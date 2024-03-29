package br.com.mcampos.ejb.cloudsystem.user.login;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.BasicSecurityDTO;
import br.com.mcampos.dto.security.LoginCredentialDTO;
import br.com.mcampos.dto.system.SendMailDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.DTOFactory;
import br.com.mcampos.ejb.cloudsystem.RandomString;
import br.com.mcampos.ejb.cloudsystem.security.accesslog.AccessLogType;
import br.com.mcampos.ejb.cloudsystem.system.EmailSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.SendMailSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.SystemParameters;
import br.com.mcampos.ejb.cloudsystem.system.SystemParametersSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.UserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.document.session.UserDocumentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.login.accesslog.AccessLog;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.PersonSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "LoginSession", mappedName = "LoginSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class LoginSessionBean extends AbstractSecurity implements LoginSessionLocal
{
	/**
	 * 
	 */
	private static final long				serialVersionUID		= 1356514789646755916L;

	private static final String				encprytPassword			= "Nj9nQ6jz6Bt3";

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager			em;

	@EJB
	private PersonSessionLocal				personSession;

	@EJB
	private UserSessionLocal				userSession;

	@EJB
	private SendMailSessionLocal			sendMail;

	@EJB
	private SystemParametersSessionLocal	sysParam;

	@EJB
	private EmailSessionLocal				emailTemplate;

	@EJB
	private UserDocumentSessionLocal		documentSession;

	private static final Integer			systemMessageTypeId		= 1;
	private static final Integer			templateEmailValidation	= 1;

	public LoginSessionBean( )
	{
	}

	/**
	 * Adiciona um novo loginSession ao sistema. Para adicionar este
	 * loginSession, deve ser observado que o mesmo depende do relacionamento
	 * com a entidade pessoa. Após incluido o loginSession com os dados mais
	 * básicos necessários, o novo usuário do sistema DEVE completar o registro.
	 * 
	 * @param dto
	 *            DTO com os dados básicos para inclusão.
	 * @exception InvalidParameterException
	 */
	@Override
	public void add( RegisterDTO dto ) throws ApplicationException
	{
	}

	/**
	 * Adiciona um novo loginSession ao sistema. Para adicionar este
	 * loginSession, deve ser observado que o mesmo depende do relacionamento
	 * com a entidade pessoa. Após incluido o loginSession com os dados mais
	 * básicos necessários, o novo usuário do sistema DEVE completar o registro.
	 * 
	 * @param dto
	 * @param person
	 * @throws ApplicationException
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void add( RegisterDTO dto, Person person ) throws ApplicationException
	{
	}

	/**
	 * Verifica se a lista de documentos inforamada é suficiente para o
	 * cadastramento do novo loginSession. Neste momento, devem existir ao menos
	 * um email e um cpf
	 * 
	 * @param list
	 *            Lista de documentos
	 * @return boolean
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private boolean verifyDocuments( List<UserDocumentDTO> list )
	{
		boolean bCPF = false, bEmail = false;

		if( list == null ) {
			return false;
		}
		for( UserDocumentDTO item : list ) {
			if( item.getDocumentType( ).getId( ).equals( UserDocumentDTO.typeEmail ) ) {
				bEmail = true;
			}
			else if( item.getDocumentType( ).getId( ).equals( UserDocumentDTO.typeCPF ) ) {
				bCPF = true;
			}
		}
		return(bEmail && bCPF);
	}

	/**
	 * Rotina responsável por obter o template de email, configurar os
	 * metacampos e enviar o email. Fica claro que este rotina serve somente
	 * para os emails vinculados as rotinas de cadastramento, validação e
	 * confirmação de loginSession.
	 * 
	 * @param login
	 *            Entity Login
	 * @param templateId
	 *            formId do template no banco de dados
	 * @param flatPassword
	 *            senha (Este senha é a senha gerada pelo sistema)
	 * @throws ApplicationException
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void sendMail( Login login, Integer templateId, String flatPassword ) throws ApplicationException
	{
		SendMailDTO emailDTO = this.createTemplate( templateId );
		emailDTO.setBody( this.translateMessageTokens( emailDTO.getBody( ), login, flatPassword ) );
		for( UserDocument item : login.getPerson( ).getDocuments( ) ) {
			if( item.getDocumentType( ).getId( ).equals( UserDocument.typeEmail ) ) {
				emailDTO.addRecipient( item.getCode( ) );
			}
		}
		try {
			this.sendMail.sendMail( emailDTO );
		}
		catch( Exception e ) {
			System.out.println( e.getMessage( ) );
		}
	}

	/**
	 * Cria um template de email baseado no template cadastrado no banco de
	 * dados
	 * 
	 * @param templateID
	 * @return
	 * @throws ApplicationException
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private SendMailDTO createTemplate( Integer templateID ) throws ApplicationException
	{
		SendMailDTO dto = this.getEmailTemplate( ).get( templateID );
		if( dto == null ) {
			this.throwRuntimeException( 11 );
		}
		return dto;
	}

	/**
	 * Traduz uma string baseda em tokens iniciados com '<<@@' e finalizado com
	 * '@@>>'
	 * 
	 * @param msg
	 *            A string a ser traduzida
	 * @param login
	 *            Entity Login
	 * @return A string traduzida
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private String translateMessageTokens( String msg, Login login, String flatPassword )
	{
		msg = msg.replaceAll( "<<@@LOGIN_NAME@@>>", login.getPerson( ).getName( ) );
		for( UserDocument item : login.getPerson( ).getDocuments( ) ) {
			if( item.getDocumentType( ).getId( ).equals( UserDocument.typeEmail ) ) {
				msg = msg.replaceAll( "<<@@EMAIL@@>>", item.getCode( ) );
				break;
			}
		}
		msg = msg.replaceAll( "<<@@TOKEN@@>>", login.getToken( ) );
		if( flatPassword != null ) {
			msg = msg.replace( "<<@@PASSWORD@@>>", flatPassword );
		}
		return msg;
	}

	/**
	 * Configura a data em que a senha irá expirar. Neste momento está
	 * configurada para 60 dias como padrão.
	 * 
	 * @param login
	 *            Entity Login
	 * @see #Login
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void setPasswordExpirationDate( Login login )
	{
		Integer days;
		SystemParameters sysParam = null;
		Calendar now;

		try {
			sysParam = this.getEntityManager( ).find( SystemParameters.class, SystemParameters.passwordValidDays );
			days = Integer.parseInt( sysParam.getValue( ) );
		}
		catch( Exception e ) {
			days = 60;
		}
		now = Calendar.getInstance( );
		now.add( Calendar.DATE, days );
		login.setPasswordExpirationDate( new Timestamp( now.getTime( ).getTime( ) + days ) );
	}

	/**
	 * Exclui um loginSession ativo no banco de dados. Um loginSession não deve
	 * ser excluído fisicamente do banco de dados. Estou pensando somente em
	 * fechar (Data fim).
	 * 
	 * @param id
	 *            Person Id a ser excluído.
	 */
	@Override
	public void delete( Integer id ) throws ApplicationException
	{
		Login login;

		/*
		 * TODO: esta rotina ainda não está validada.
		 */
		if( id == null ) {
			this.throwException( 14 );
		}
		login = this.getEntityManager( ).find( Login.class, id );
		if( login == null ) {
			return;
		}
		/*
		 * Regra: Se o login está aguardando email, apenas exclua normalmente,
		 * pois significa que este usuário nunca logou no sistema.
		 */
		if( login.getUserStatus( ).getId( ) == UserStatus.statusEmailNotValidated ) {
			this.getEntityManager( ).remove( login );
		}
	}

	/**
	 * Atualiza o status do loginSession.
	 * 
	 * @param id
	 * @param newStatus
	 */
	@Override
	public void updateLoginStatus( Integer id, Integer newStatus ) throws ApplicationException
	{
		Login login = this.getEntityManager( ).find( Login.class, id );
		if( login == null || newStatus == null ) {
			this.throwException( 14 );
		}
		login.setUserStatus( this.getEntityManager( ).find( UserStatus.class, newStatus ) );
	}

	/**
	 * Finaliza a sessão do usuário (no banco de dados). Este função tem como
	 * finalidade incluir um registro no banco de dados finalizando o
	 * loginSession.
	 * 
	 * @param dto
	 *            AuthenticationDTO
	 */
	@Override
	public void logoutUser( AuthenticationDTO dto ) throws ApplicationException
	{
		if( dto == null || dto.getUserId( ) == null ) {
			return;
		}
		Login login = this.getEntityManager( ).find( Login.class, dto.getUserId( ) );
		if( login == null ) {
			return;
		}
		this.storeAccessLog( login, dto, AccessLogType.accessLogTypeLogout );

	}

	/**
	 * Dado uma lista de documentos, obter o loginSession associado aos
	 * documentos. Uma regra: todos os documentos DEVEM pertencer a um e somente
	 * um loginSession.
	 * 
	 * @param list
	 * @return
	 * @throws ApplicationException
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private Login getLogin( List<UserDocumentDTO> list ) throws ApplicationException
	{
		Person person = (Person) this.getUserSession( ).findByDocumentList( list );
		if( person == null ) {
			this.throwException( 14 );
		}
		Login login = this.getEntityManager( ).find( Login.class, person.getId( ) );
		if( login == null ) {
			this.throwException( 14 );
		}
		return login;
	}

	/**
	 * Obtem um loginSession através de um documento.
	 * 
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private Login getLogin( UserDocumentDTO dto ) throws ApplicationException
	{
		Person person = (Person) this.getUserSession( ).getUserByDocument( dto );
		if( person == null ) {
			this.throwException( 14 );
		}
		Login login = this.getEntityManager( ).find( Login.class, person.getId( ) );
		if( login == null ) {
			this.throwException( 14 );
		}
		return login;
	}

	/**
	 * Autentica o usuário no sistema.
	 * 
	 * @param dto
	 *            Credenciais para efetivar a autenticação.
	 * @return LoginDTO dto básico autenticado.
	 * @throws ApplicationException
	 */
	@Override
	public AuthenticationDTO loginUser( LoginCredentialDTO dto ) throws ApplicationException
	{
		Login login = null;
		BasicPasswordEncryptor passwordEncryptor;
		SystemParameters sysParam = null;

		if( dto == null ) {
			this.throwException( 1 );
		}
		if( SysUtils.isEmpty( dto.getPassword( ) ) ) {
			this.throwException( 6 );
		}
		if( SysUtils.isEmpty( dto.getDocuments( ) ) ) {
			this.throwException( 3 );
		}
		if( SysUtils.isEmpty( dto.getSessionId( ) ) ) {
			this.throwException( 20 );
		}

		login = this.getLogin( dto.getDocuments( ) );
		this.getEntityManager( ).refresh( login );
		this.verifyUserStatus( login );
		passwordEncryptor = new BasicPasswordEncryptor( );
		if( passwordEncryptor.checkPassword( dto.getPassword( ), login.getPassword( ) ) == false ) {
			Integer tryCount;

			login.incrementTryCount( );
			try {
				sysParam = this.getEntityManager( ).find( SystemParameters.class, SystemParameters.maxLoginTryCount );
				tryCount = Integer.parseInt( sysParam.getValue( ) );
			}
			catch( Exception e ) {
				tryCount = 5;
			}
			if( login.getTryCount( ) > tryCount ) {
				login.setUserStatus( this.getEntityManager( ).find( UserStatus.class, UserStatus.statusMaxLoginTryCount ) );
			}
			this.throwException( 13 );
		}
		login.setTryCount( 0 );
		String authToken = this.storeAccessLog( login, dto, AccessLogType.accessLogTypeNormalLogin );
		/*
		 * If the user password has expired, we may allow login, but we'll
		 * denied every operation that depends on this login.
		 */
		Date now = new Date( );
		if( login.getPasswordExpirationDate( ).compareTo( new Timestamp( now.getTime( ) ) ) < 0 ) {
			login.setUserStatus( this.getEntityManager( ).find( UserStatus.class, UserStatus.statusExpiredPassword ) );
		}
		// throwException( 19 );
		AuthenticationDTO retDTO = new AuthenticationDTO( );
		retDTO.setUserId( login.getUserId( ) );
		retDTO.setSessionId( dto.getSessionId( ) );
		retDTO.setAuthenticationId( authToken );
		retDTO.setRemoteAddr( dto.getRemoteAddr( ) );
		retDTO.setRemoteHost( dto.getRemoteHost( ) );
		retDTO.setLocale( dto.getLocale( ) );
		return retDTO;
	}

	/**
	 * Para todo o acesso ao sistema ( Login, Logout, troca de senha, etc) um
	 * registro de log será gravado.
	 * 
	 * @param login
	 *            Entity Login.
	 * @param dto
	 *            Credenciais de loginSession.
	 * @param accessLogType
	 *            Tipo de log a ser armazenado.
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private String storeAccessLog( Login login, BasicSecurityDTO dto, Integer accessLogType ) throws ApplicationException
	{
		AccessLog log;

		if( login == null ) {
			this.throwException( 1 );
		}

		log = new AccessLog( );

		log.setLogin( login );
		log.setLoginType( this.getEntityManager( ).find( AccessLogType.class, accessLogType ) );
		log.setIp( dto != null ? dto.getRemoteAddr( ) : "127.0.0.1" );
		log.setComputer( dto != null ? dto.getRemoteHost( ) : null );
		log.setSessionId( dto != null ? dto.getSessionId( ) : null );
		if( dto != null ) {
			BasicTextEncryptor encrypt = new BasicTextEncryptor( );
			encrypt.setPassword( encprytPassword );
			log.setAuthenticationId( encrypt.encrypt( dto.getSessionId( ) ) );
			this.getEntityManager( ).persist( log );
			return log.getAuthenticationId( );
		}
		else {
			return null;
		}
	}

	/**
	 * Cria uma nova senha para o usuario logar no sistema e envia esta senha
	 * via email.
	 * 
	 * 
	 * @param document
	 *            UserDocumentDTO - identificao do usuario via documento (Email)
	 * @exception InvalidParameterException
	 */
	@Override
	public void makeNewPassword( UserDocumentDTO document ) throws ApplicationException
	{
		Login login;
		BasicPasswordEncryptor passwordEncryptor;
		String password;

		if( document == null ) {
			this.throwException( 1 );
		}

		login = this.getLogin( document );
		if( login == null ) {
			this.throwException( 14 );
		}
		this.verifyUserStatus( login );
		passwordEncryptor = new BasicPasswordEncryptor( );
		password = RandomString.randomstring( );
		this.storeOldPassword( login );
		login.setPassword( passwordEncryptor.encryptPassword( password ) );
		this.storeOldPassword( login );
		login.setTryCount( 0 );
		this.sendMail( login, 3, password );
	}

	/**
	 * Quando uma senha é alterada, a senha antiga é armazenada no banco de
	 * dados e o sistema não permite que esta senha seja usada novamente pelo
	 * mesmo loginSession.
	 * 
	 * 
	 * @param login
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void storeOldPassword( Login login ) throws ApplicationException
	{
	}

	/**
	 * Valida o status do usuário de acordo com a operação.
	 * 
	 * @param login
	 * @throws ApplicationException
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void verifyUserStatus( Login login ) throws ApplicationException
	{
		if( login.getUserStatus( ).getAllowLogin( ) == true ) {
			return;
		}

		switch( (login.getUserStatus( ).getId( )) ) {
		case UserStatus.statusMaxLoginTryCount:
			this.throwException( 15 );
		case UserStatus.statusInativo:
			this.throwException( 16 );
		case UserStatus.statusEmailNotValidated:
			this.throwException( 17 );
		default:
			this.throwException( 18 );
		}
	}

	/**
	 * Altera a senha do usuário. Deve-se imaginar que o usuario não está logado
	 * no sistema para alterar a sua senha.
	 * 
	 * @param document
	 *            UserDocumentDTO - identificado do usuario via documento
	 *            (Email)
	 * @param oldPassword
	 *            - A senha antiga a ser alterada
	 * @param newPassword
	 *            - A nova senha
	 * @exception InvalidParameterException
	 */
	@Override
	public void changePassword( UserDocumentDTO document, String oldPassword, String newPassword ) throws ApplicationException
	{
		Login login;
		BasicPasswordEncryptor passwordEncryptor;

		login = this.getLogin( document );
		if( login == null ) {
			throw new EJBException( "Usuário o senha inválida." );
		}
		this.verifyUserStatus( login );
		passwordEncryptor = new BasicPasswordEncryptor( );
		if( passwordEncryptor.checkPassword( oldPassword, login.getPassword( ) ) == false ) {
			throw new EJBException( "A senha atual está inválida." );
		}
		if( this.isPasswordUsed( login, newPassword ) ) {
			throw new EJBException( "A nova senha já foi usada antes. Não é permitido o uso de senhas antigas." );
		}
		this.storeOldPassword( login );
		login.setPassword( passwordEncryptor.encryptPassword( newPassword ) );
		this.storeOldPassword( login );
		login.setTryCount( 0 );
		this.setPasswordExpirationDate( login );
		login.setUserStatus( this.getEntityManager( ).find( UserStatus.class, UserStatus.statusOk ) );
		// sendMail( login, 4, null );
	}

	/**
	 * O sistema não permite que uma senha seja usada mais de uma vez. Assim,
	 * esta função verifica a existência de uma senha usada anteriormente por um
	 * mesmo usuário.
	 * 
	 * @param login
	 *            Entity Login
	 * @param newPassword
	 *            Nova senha a ser verificada
	 * @return Boolean
	 */
	@SuppressWarnings( "unchecked" )
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private Boolean isPasswordUsed( Login login, String newPassword )
	{
		return false;
	}

	/**
	 * Quando o usuário erra a senha ao acessar o site, é incrementado um
	 * contador de erros. Quando este valor ultrapassa o máximo de número de
	 * tentativas, o usuário é automaticamente bloqueado pelo sistema.
	 * 
	 * @param login
	 *            Entity Login
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void incrementTryCount( Login login )
	{
		this.getEntityManager( ).merge( login );
		login.incrementTryCount( );
		this.getEntityManager( ).flush( );
	}

	/**
	 * Valida o email informado no ato do cadastro. Quando o usuario se cadastra
	 * no sistema, deve obrigatóriamente informar um email válido e único no
	 * sistema. Este será a 'identidade' do usuário no sistema, e, portanto,
	 * este email deve ser validado.
	 * 
	 * 
	 * @param token
	 *            Código gerado pelo sistema e enviado ao email do novo
	 *            cadastro. Este token deve ser único no sistema.
	 * @param password
	 *            Senha de validação. Este é a senha informada pelo usuário no
	 *            ato do cadstro
	 * @exception InvalidParameterException
	 */
	@Override
	public void validateEmail( String token, String password ) throws ApplicationException
	{
		BasicPasswordEncryptor passwordEncryptor;
		Login login;

		if( SysUtils.isEmpty( token ) ) {
			this.throwException( 8 );
		}
		if( SysUtils.isEmpty( password ) ) {
			this.throwException( 6 );
		}

		login = this.findLoginByToken( token );
		if( login.getUserStatus( ).getId( ) != UserStatus.statusEmailNotValidated ) {
			this.throwException( 12 );
		}
		passwordEncryptor = new BasicPasswordEncryptor( );
		if( passwordEncryptor.checkPassword( password, login.getPassword( ) ) == false ) {
			this.incrementTryCount( login );
			this.throwException( 13 );
		}
		login.setTryCount( 0 );
		login.setUserStatus( this.getEntityManager( ).find( UserStatus.class, UserStatus.statusFullfillRecord ) );
		login.setToken( null );

		this.sendMail( login, 2, null );
	}

	/**
	 * Quando um novo usuário se cadastra no sistema, é enviado um email de
	 * confirmação com um código de acesso (token). Este código é usado para
	 * validar o email dentro do sistema. Esta função localiza o novo
	 * loginSession através deste token - que por sua vez deve ser único no
	 * sistema.
	 * 
	 * @param token
	 *            - Um string gerado aleatoreamente pelo sistema.
	 * @return Entity Login
	 * @throws ApplicationException
	 */
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private Login findLoginByToken( String token ) throws ApplicationException
	{
		Login login = null;

		try {
			login =
					(Login) this.getEntityManager( ).createNamedQuery( "Login.findToken" ).setParameter( "token", token ).getSingleResult( );
		}
		catch( NoResultException e ) {
			e = null;
			this.throwException( 11 );
		}
		return login;
	}

	@Override
	public Long getRecordCount( )
	{
		Long recordCount;

		recordCount = (Long) this.getEntityManager( ).createNativeQuery( "SELECT COUNT(*) FROM LOGIN" ).getSingleResult( );
		return recordCount;
	}

	/**
	 * <penPageSequence>select o from Users o</penPageSequence>
	 */
	@SuppressWarnings( "unchecked" )
	public List<ListLoginDTO> getLoginByRange( int firstResult, int maxResults )
	{
		Query query = this.getEntityManager( ).createNamedQuery( "Login.findAll" );
		if( firstResult > 0 ) {
			query = query.setFirstResult( firstResult );
		}
		if( maxResults > 0 ) {
			query = query.setMaxResults( maxResults );
		}
		return copy( query.getResultList( ) );
	}

	public static List<ListLoginDTO> copy( List<Login> list )
	{
		List<ListLoginDTO> dtos = null;

		if( list == null ) {
			return dtos;
		}
		dtos = new ArrayList<ListLoginDTO>( list.size( ) );
		for( Login item : list ) {
			dtos.add( DTOFactory.copy( item ) );
		}
		return dtos;

	}

	/**
	 * Reenvia um email de confirmação de cadastro de loginSession.
	 * 
	 * 
	 * @param dto
	 *            UserDocumentDTO - identificao do usuario via documento (Email)
	 * @exception InvalidParameterException
	 */
	@Override
	public void sendValidationEmail( UserDocumentDTO dto ) throws ApplicationException
	{
		Login login;

		login = this.getLogin( dto );
		login.getUserStatus( );
		if( login.getUserStatus( ).getId( ) != UserStatus.statusEmailNotValidated ) {
			this.throwException( 12 );
		}
		this.sendMail( login, 1, null );
	}

	protected PersonSessionLocal getPersonSession( )
	{
		return this.personSession;
	}

	protected UserSessionLocal getUserSession( )
	{
		return this.userSession;
	}

	protected SystemParametersSessionLocal getSysParam( )
	{
		return this.sysParam;
	}

	protected EmailSessionLocal getEmailTemplate( )
	{
		return this.emailTemplate;
	}

	/**
	 * Obtem o status do loginSession do usuário corrente (autenticado).
	 * 
	 * @param currentUser
	 *            AuthenticationDTO do usuário autenticado
	 * @return Id do status do usuário
	 */
	@Override
	public Integer getStatus( AuthenticationDTO currentUser ) throws ApplicationException
	{
		this.authenticate( currentUser );

		Login login = this.getEntityManager( ).find( Login.class, currentUser.getUserId( ) );
		return login.getUserStatus( ).getId( );
	}

	/**
	 * Altera o status do usuário no banco de dados.
	 * 
	 * @param currentUser
	 *            Usuário autenticado.
	 * @param newStatus
	 *            Novo status a ser alterado no banco de dados.
	 */
	@Override
	public void setStatus( AuthenticationDTO currentUser, Integer newStatus ) throws ApplicationException
	{
		this.authenticate( currentUser );
		if( SysUtils.isZero( newStatus ) ) {
			this.throwCommomRuntimeException( 3 );
		}
		Login login = this.getEntityManager( ).find( Login.class, currentUser.getUserId( ) );
		login.setUserStatus( this.getEntityManager( ).find( UserStatus.class, newStatus ) );
	}

	@Override
	public Integer getMessageTypeId( )
	{
		return systemMessageTypeId;
	}

	@Override
	public EntityManager getEntityManager( )
	{
		return this.em;
	}

	@Override
	public Login get( Integer id ) throws ApplicationException
	{
		return this.getEntityManager( ).find( Login.class, id );
	}

	@Override
	public Login get( Person person ) throws ApplicationException
	{
		return this.getEntityManager( ).find( Login.class, person.getId( ) );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<Login> getAll( ) throws ApplicationException
	{
		try {
			Query query = this.getEntityManager( ).createNamedQuery( Login.getAll );
			return query.getResultList( );
		}
		catch( Exception e ) {
			return Collections.emptyList( );
		}
	}
}
