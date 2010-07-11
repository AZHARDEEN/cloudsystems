package br.com.mcampos.ejb.cloudsystem.user.login;


import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.BasicSecurityDTO;
import br.com.mcampos.dto.security.LoginCredentialDTO;
import br.com.mcampos.dto.system.SendMailDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.ejb.cloudsystem.security.accesslog.AccessLogType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;
import br.com.mcampos.ejb.cloudsystem.user.document.UserDocumentUtil;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.document.session.UserDocumentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.login.accesslog.AccessLog;
import br.com.mcampos.ejb.cloudsystem.user.login.lastusedpassword.LastUsedPassword;
import br.com.mcampos.ejb.cloudsystem.user.login.lastusedpassword.LastUsedPasswordPK;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.PersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.core.util.RandomString;
import br.com.mcampos.ejb.entity.system.SystemParameters;
import br.com.mcampos.ejb.session.system.EmailSessionLocal;
import br.com.mcampos.ejb.session.system.SendMailSessionLocal;
import br.com.mcampos.ejb.session.system.SystemParametersSessionLocal;
import br.com.mcampos.ejb.session.user.UserSessionLocal;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
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


@Stateless( name = "LoginSession", mappedName = "CloudSystems-EjbPrj-LoginSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class LoginSessionBean extends AbstractSecurity implements LoginSessionLocal
{
    private static final String encprytPassword = "Nj9nQ6jz6Bt3";

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private PersonSessionLocal personSession;

    @EJB
    private UserSessionLocal userSession;

    @EJB
    private SendMailSessionLocal sendMail;

    @EJB
    private SystemParametersSessionLocal sysParam;

    @EJB
    private EmailSessionLocal emailTemplate;

    @EJB
    private UserDocumentSessionLocal documentSession;

    private static final Integer systemMessageTypeId = 1;
    private static final Integer templateEmailValidation = 1;


    public LoginSessionBean()
    {
    }

    /**
     * Adiciona um novo loginSession ao sistema. Para adicionar este loginSession, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     * Após incluido o loginSession com os dados mais básicos necessários, o novo usuário do
     * sistema DEVE completar o registro.
     *
     * @param dto DTO com os dados básicos para inclusão.
     * @exception InvalidParameterException
     */
    public void add( RegisterDTO dto ) throws ApplicationException
    {
        /*
         * Verificar os parametros novamente. Pois nao podemos confiar no controller.
         */
        Person person;

        /*
         * Validação dos parâmetros recebidos
         */
        if ( dto == null )
            throwRuntimeException( 1 );
        if ( SysUtils.isEmpty( dto.getName() ) )
            throwRuntimeException( 2 );
        if ( dto.getDocuments().size() < 1 )
            throwRuntimeException( 3 );
        if ( SysUtils.isEmpty( dto.getPassword() ) )
            throwRuntimeException( 4 );
        if ( verifyDocuments( dto.getDocuments() ) == false )
            throwRuntimeException( 10 );

        person = ( Person )getUserSession().findByDocumentList( dto.getDocuments() );
        if ( person == null ) {
            person = getPersonSession().add( PersonUtil.copy( dto ) );
            documentSession.refresh( person, UserDocumentUtil.toEntityList( person, dto.getDocuments() ) );
        }
        if ( get( person ) != null )
            throwRuntimeException( 5 );
        add( dto, person );
    }

    /**
     * Adiciona um novo loginSession ao sistema. Para adicionar este loginSession, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     * Após incluido o loginSession com os dados mais básicos necessários, o novo usuário do
     * sistema DEVE completar o registro.
     *
     * @param dto
     * @param person
     * @throws ApplicationException
     */
    protected void add( RegisterDTO dto, Person person ) throws ApplicationException
    {
        String encryptedPassword;
        BasicPasswordEncryptor passwordEncryptor;

        Login login = new Login( null, dto.getPassword(), em.find( UserStatus.class, UserStatus.statusEmailNotValidated ) );
        /*
         * Nunca esquecer de vincular os objetos. Quando um objeto é instanciado
         * ele mantem suas características. Neste exemplo: foi instanciado uma
         * pessoa, que neste momento não possuia login. Ainda que o login seja
         * inserido no banco de dados, fica a situação inicial - sem login.
         */
        login.setPerson( person );
        login.setUserId( person.getId() );
        passwordEncryptor = new BasicPasswordEncryptor();
        encryptedPassword = passwordEncryptor.encryptPassword( login.getPassword() );
        setPasswordExpirationDate( login );
        login.setPassword( encryptedPassword );
        login.setToken( RandomString.randomstring() );
        if ( SysUtils.isEmpty( login.getToken() ) )
            throwRuntimeException( 8 );
        /*
         * TODO - Vincular o novo login a empresa master (Neste caso MCampos Consultoria!!!!);
         * como usuario (empregado). Atribuir uma role de usuario do sistema.
         */
        getEntityManager().persist( login );
        getEntityManager().flush();
        storeOldPassword( login );
        storeAccessLog( login, dto, 2 );
        sendMail( login, templateEmailValidation, null );
    }


    /**
     * Verifica se a lista de documentos inforamada é suficiente para o cadastramento
     * do novo loginSession. Neste momento, devem existir ao menos um email e um cpf
     *
     * @param list Lista de documentos
     * @return boolean
     */
    protected boolean verifyDocuments( List<UserDocumentDTO> list )
    {
        boolean bCPF = false, bEmail = false;

        if ( list == null )
            return false;
        for ( UserDocumentDTO item : list ) {
            if ( item.getDocumentType().getId().equals( UserDocumentDTO.typeEmail ) ) {
                bEmail = true;
            }
            else if ( item.getDocumentType().getId().equals( UserDocumentDTO.typeCPF ) ) {
                bCPF = true;
            }
        }
        return ( bEmail && bCPF );
    }


    /**
     * Rotina responsável por obter o template de email, configurar os metacampos
     * e enviar o email. Fica claro que este rotina serve somente para os emails
     * vinculados as rotinas de cadastramento, validação e confirmação de loginSession.
     *
     * @param login Entity Login
     * @param templateId formId do template no banco de dados
     * @param flatPassword senha (Este senha é a senha gerada pelo sistema)
     * @throws ApplicationException
     */
    protected void sendMail( Login login, Integer templateId, String flatPassword ) throws ApplicationException
    {
        SendMailDTO emailDTO = createTemplate( templateId );
        emailDTO.setBody( translateMessageTokens( emailDTO.getBody(), login, flatPassword ) );
        for ( UserDocument item : login.getPerson().getDocuments() ) {
            if ( item.getDocumentType().getId().equals( UserDocument.typeEmail ) )
                emailDTO.addRecipient( item.getCode() );
        }
        sendMail.sendMail( emailDTO );
    }


    /**
     * Cria um template de email baseado no template cadastrado no banco de dados
     *
     * @param templateID
     * @return
     * @throws ApplicationException
     */
    protected SendMailDTO createTemplate( Integer templateID ) throws ApplicationException
    {
        SendMailDTO dto = getEmailTemplate().get( templateID );
        if ( dto == null )
            throwRuntimeException( 11 );
        return dto;
    }

    /**
     * Traduz uma string baseda em tokens iniciados com '<<@@' e finalizado com '@@>>'
     *
     * @param msg A string a ser traduzida
     * @param login Entity Login
     * @return A string traduzida
     */
    protected String translateMessageTokens( String msg, Login login, String flatPassword )
    {
        msg = msg.replaceAll( "<<@@LOGIN_NAME@@>>", login.getPerson().getName() );
        for ( UserDocument item : login.getPerson().getDocuments() ) {
            if ( item.getDocumentType().getId().equals( UserDocument.typeEmail ) ) {
                msg = msg.replaceAll( "<<@@EMAIL@@>>", item.getCode() );
                break;
            }
        }
        msg = msg.replaceAll( "<<@@TOKEN@@>>", login.getToken() );
        if ( flatPassword != null )
            msg = msg.replace( "<<@@PASSWORD@@>>", flatPassword );
        return msg;
    }

    /**
     * Configura a data em que a senha irá expirar. Neste momento está configurada
     * para 60 dias como padrão.
     * @param login Entity Login
     * @see #Login
     */
    protected void setPasswordExpirationDate( Login login )
    {
        Integer days;
        SystemParameters sysParam = null;
        Calendar now;

        try {
            sysParam = getEntityManager().find( SystemParameters.class, SystemParameters.passwordValidDays );
            days = Integer.parseInt( sysParam.getValue() );
        }
        catch ( Exception e ) {
            days = 60;
        }
        now = Calendar.getInstance();
        now.add( Calendar.DATE, days );
        login.setPasswordExpirationDate( new Timestamp( now.getTime().getTime() + days ) );
    }


    /**
     * Exclui um loginSession ativo no banco de dados. Um loginSession não deve ser excluído
     * fisicamente do banco de dados. Estou pensando somente em fechar (Data fim).
     *
     * @param id Person Id a ser excluído.
     */
    public void delete( Integer id ) throws ApplicationException
    {
        Login login;

        /*
         * TODO: esta rotina ainda não está validada.
         */
        if ( id == null )
            throwException( 14 );
        login = getEntityManager().find( Login.class, id );
        if ( login == null )
            return;
        /*
         * Regra: Se o login está aguardando email, apenas exclua normalmente,
         * pois significa que este usuário nunca logou no sistema.
         */
        if ( login.getUserStatus().getId() == UserStatus.statusEmailNotValidated )
            getEntityManager().remove( login );
    }


    /**
     * Atualiza o status do loginSession.
     *
     * @param id
     * @param newStatus
     */
    public void updateLoginStatus( Integer id, Integer newStatus ) throws ApplicationException
    {
        Login login = getEntityManager().find( Login.class, id );
        if ( login == null || newStatus == null )
            throwException( 14 );
        login.setUserStatus( getEntityManager().find( UserStatus.class, newStatus ) );
    }


    /**
     * Finaliza a sessão do usuário (no banco de dados). Este função tem como
     * finalidade incluir um registro no banco de dados finalizando o loginSession.
     *
     * @param dto AuthenticationDTO
     */
    public void logoutUser( AuthenticationDTO dto ) throws ApplicationException
    {
        if ( dto == null || dto.getUserId() == null )
            return;
        Login login = getEntityManager().find( Login.class, dto.getUserId() );
        if ( login == null )
            return;
        storeAccessLog( login, dto, AccessLogType.accessLogTypeLogout );

    }

    /**
     * Dado uma lista de documentos, obter o loginSession associado aos documentos.
     * Uma regra: todos os documentos DEVEM pertencer a um e somente um loginSession.
     *
     * @param list
     * @return
     * @throws ApplicationException
     */
    protected Login getLogin( List<UserDocumentDTO> list ) throws ApplicationException
    {
        Person person = ( Person )getUserSession().findByDocumentList( list );
        if ( person == null )
            throwException( 14 );
        Login login = get( person );
        if ( login == null )
            throwException( 14 );
        return login;
    }

    /**
     * Obtem um loginSession através de um documento.
     *
     * @param dto
     * @return
     * @throws ApplicationException
     */
    protected Login getLogin( UserDocumentDTO dto ) throws ApplicationException
    {
        Person person = ( Person )getUserSession().getUserByDocument( dto );
        if ( person == null )
            throwException( 14 );
        Login login = get( person );
        if ( login == null )
            throwException( 14 );
        return login;
    }


    /**
     * Autentica o usuário no sistema.
     *
     * @param dto Credenciais para efetivar a autenticação.
     * @return LoginDTO dto básico autenticado.
     * @throws ApplicationException
     */
    public AuthenticationDTO loginUser( LoginCredentialDTO dto ) throws ApplicationException
    {
        Login login = null;
        BasicPasswordEncryptor passwordEncryptor;
        SystemParameters sysParam = null;

        if ( dto == null )
            throwException( 1 );
        if ( SysUtils.isEmpty( dto.getPassword() ) )
            throwException( 6 );
        if ( SysUtils.isEmpty( dto.getDocuments() ) )
            throwException( 3 );
        if ( SysUtils.isEmpty( dto.getSessionId() ) )
            throwException( 20 );

        login = getLogin( dto.getDocuments() );
        getEntityManager().refresh( login );
        verifyUserStatus( login );
        passwordEncryptor = new BasicPasswordEncryptor();
        if ( passwordEncryptor.checkPassword( dto.getPassword(), login.getPassword() ) == false ) {
            Integer tryCount;

            login.incrementTryCount();
            try {
                sysParam = getEntityManager().find( SystemParameters.class, SystemParameters.maxLoginTryCount );
                tryCount = Integer.parseInt( sysParam.getValue() );
            }
            catch ( Exception e ) {
                tryCount = 5;
            }
            if ( login.getTryCount() > tryCount ) {
                login.setUserStatus( getEntityManager().find( UserStatus.class, UserStatus.statusMaxLoginTryCount ) );
            }
            throwException( 13 );
        }
        login.setTryCount( 0 );
        String authToken = storeAccessLog( login, dto, AccessLogType.accessLogTypeNormalLogin );
        /*
         * If the user password has expired, we may allow login,
         * but we'll denied every operation that depends on this login.
         */
        Date now = new Date();
        if ( login.getPasswordExpirationDate().compareTo( new Timestamp( now.getTime() ) ) < 0 ) {
            login.setUserStatus( getEntityManager().find( UserStatus.class, UserStatus.statusExpiredPassword ) );
            //throwException( 19 );
        }
        AuthenticationDTO retDTO = new AuthenticationDTO();
        retDTO.setUserId( login.getUserId() );
        retDTO.setSessionId( dto.getSessionId() );
        retDTO.setAuthenticationId( authToken );
        retDTO.setRemoteAddr( dto.getRemoteAddr() );
        retDTO.setRemoteHost( dto.getRemoteHost() );
        retDTO.setLocale( dto.getLocale() );
        return retDTO;
    }

    /**
     * Para todo o acesso ao sistema ( Login, Logout, troca de senha, etc)
     * um registro de log será gravado.
     *
     * @param login Entity Login.
     * @param dto Credenciais de loginSession.
     * @param accessLogType Tipo de log a ser armazenado.
     */
    protected String storeAccessLog( Login login, BasicSecurityDTO dto, Integer accessLogType ) throws ApplicationException
    {
        AccessLog log;

        if ( login == null )
            throwException( 1 );

        log = new AccessLog();

        log.setLogin( login );
        log.setLoginType( getEntityManager().find( AccessLogType.class, accessLogType ) );
        log.setIp( dto != null ? dto.getRemoteAddr() : "127.0.0.1" );
        log.setComputer( dto != null ? dto.getRemoteHost() : null );
        log.setSessionId( dto.getSessionId() );
        if ( dto != null ) {
            BasicTextEncryptor encrypt = new BasicTextEncryptor();
            encrypt.setPassword( encprytPassword );
            log.setAuthenticationId( encrypt.encrypt( dto.getSessionId() ) );
        }
        else
            return null;
        getEntityManager().persist( log );
        return log.getAuthenticationId();
    }

    /**
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     *
     *
     * @param document UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    public void makeNewPassword( UserDocumentDTO document ) throws ApplicationException
    {
        Login login;
        BasicPasswordEncryptor passwordEncryptor;
        String password;


        if ( document == null )
            throwException( 1 );

        login = getLogin( document );
        if ( login == null )
            throwException( 14 );
        verifyUserStatus( login );
        passwordEncryptor = new BasicPasswordEncryptor();
        password = RandomString.randomstring();
        storeOldPassword( login );
        login.setPassword( passwordEncryptor.encryptPassword( password ) );
        storeOldPassword( login );
        login.setTryCount( 0 );
        sendMail( login, 3, password );
    }


    /**
     * Quando uma senha é alterada, a senha antiga é armazenada no banco de dados
     * e o sistema não permite que esta senha seja usada novamente pelo mesmo loginSession.
     *
     *
     * @param login
     */
    protected void storeOldPassword( Login login ) throws ApplicationException
    {
        LastUsedPassword lastUsedPassword;
        Timestamp now;

        try {
            now = new Timestamp( Calendar.getInstance().getTime().getTime() );
            lastUsedPassword =
                    getEntityManager().find( LastUsedPassword.class, new LastUsedPasswordPK( login.getPassword(), login.getUserId() ) );
            if ( lastUsedPassword == null ) {
                lastUsedPassword = new LastUsedPassword( now, login.getPassword(), null, login.getUserId() );
                lastUsedPassword.setLogin( login );
                getEntityManager().persist( lastUsedPassword );
            }
            else {
                lastUsedPassword.setToDate( now );
            }
        }
        catch ( Exception e ) {
            throwRuntimeException( 21 );
        }
    }


    /**
     * Valida o status do usuário de acordo com a operação.
     *
     * @param login
     * @throws ApplicationException
     */
    protected void verifyUserStatus( Login login ) throws ApplicationException
    {
        if ( login.getUserStatus().getAllowLogin() == true )
            return;

        switch ( ( int )( login.getUserStatus().getId() ) ) {
        case UserStatus.statusMaxLoginTryCount:
            throwException( 15 );
        case UserStatus.statusInativo:
            throwException( 16 );
        case UserStatus.statusEmailNotValidated:
            throwException( 17 );
        default:
            throwException( 18 );
        }
    }


    /**
     * Altera a senha do usuário. Deve-se imaginar que o usuario não está logado no sistema
     * para alterar a sua senha.
     *
     * @param document UserDocumentDTO - identificado do usuario via documento (Email)
     * @param oldPassword - A senha antiga a ser alterada
     * @param newPassword - A nova senha
     * @exception InvalidParameterException
     */
    public void changePassword( UserDocumentDTO document, String oldPassword, String newPassword ) throws ApplicationException
    {
        Login login;
        BasicPasswordEncryptor passwordEncryptor;

        login = getLogin( document );
        if ( login == null )
            throw new EJBException( "Usuário o senha inválida." );
        verifyUserStatus( login );
        passwordEncryptor = new BasicPasswordEncryptor();
        if ( passwordEncryptor.checkPassword( oldPassword, login.getPassword() ) == false ) {
            throw new EJBException( "A senha atual está inválida." );
        }
        if ( isPasswordUsed( login, newPassword ) )
            throw new EJBException( "A nova senha já foi usada antes. Não é permitido o uso de senhas antigas." );
        storeOldPassword( login );
        login.setPassword( passwordEncryptor.encryptPassword( newPassword ) );
        storeOldPassword( login );
        login.setTryCount( 0 );
        setPasswordExpirationDate( login );
        login.setUserStatus( getEntityManager().find( UserStatus.class, UserStatus.statusOk ) );
        sendMail( login, 4, null );
    }


    /**
     * O sistema não permite que uma senha seja usada mais de uma vez. Assim,
     * esta função verifica a existência de uma senha usada anteriormente por
     * um mesmo usuário.
     *
     * @param login Entity Login
     * @param newPassword Nova senha a ser verificada
     * @return Boolean
     */
    protected Boolean isPasswordUsed( Login login, String newPassword )
    {
        List<LastUsedPassword> list;
        BasicPasswordEncryptor passwordEncryptor;

        try {
            List retList;

            retList =
                    getEntityManager().createNamedQuery( "LastUsedPassword.findAll" ).setParameter( "id", login.getUserId() ).getResultList();
            list = ( List<LastUsedPassword> )retList;
            passwordEncryptor = new BasicPasswordEncryptor();
            for ( LastUsedPassword password : list ) {
                if ( passwordEncryptor.checkPassword( newPassword, password.getPassword() ) )
                    return true;
            }
        }
        catch ( NoResultException e ) {
            return false;
        }
        return false;
    }

    /**
     * Quando o usuário erra a senha ao acessar o site, é incrementado um contador
     * de erros. Quando este valor ultrapassa o máximo de número de tentativas,
     * o usuário é automaticamente bloqueado pelo sistema.
     *
     * @param login Entity Login
     */
    protected void incrementTryCount( Login login )
    {
        getEntityManager().merge( login );
        login.incrementTryCount();
        getEntityManager().flush();
    }

    /**
     * Valida o email informado no ato do cadastro. Quando o usuario se cadastra no sistema,
     * deve obrigatóriamente informar um email válido e único no sistema. Este será a 'identidade'
     * do usuário no sistema, e, portanto, este email deve ser validado.
     *
     *
     * @param token Código gerado pelo sistema e enviado ao email do novo cadastro.
     *              Este token deve ser único no sistema.
     * @param password Senha de validação. Este é a senha informada pelo usuário no ato do cadstro
     * @exception InvalidParameterException
     */
    public void validateEmail( String token, String password ) throws ApplicationException
    {
        BasicPasswordEncryptor passwordEncryptor;
        Login login;

        if ( SysUtils.isEmpty( token ) )
            throwException( 8 );
        if ( SysUtils.isEmpty( password ) )
            throwException( 6 );


        login = findLoginByToken( token );
        if ( login.getUserStatus().getId() != UserStatus.statusEmailNotValidated )
            throwException( 12 );
        passwordEncryptor = new BasicPasswordEncryptor();
        if ( passwordEncryptor.checkPassword( password, login.getPassword() ) == false ) {
            incrementTryCount( login );
            throwException( 13 );
        }
        login.setTryCount( 0 );
        login.setUserStatus( getEntityManager().find( UserStatus.class, UserStatus.statusFullfillRecord ) );
        login.setToken( null );

        sendMail( login, 2, null );
    }

    /**
     * Quando um novo usuário se cadastra no sistema, é enviado um email de confirmação
     * com um código de acesso (token). Este código é usado para validar o email dentro
     * do sistema. Esta função localiza o novo loginSession através deste token - que por sua
     * vez deve ser único no sistema.
     *
     * @param token - Um string gerado aleatoreamente pelo sistema.
     * @return Entity Login
     * @throws ApplicationException
     */
    protected Login findLoginByToken( String token ) throws ApplicationException
    {
        Login login = null;

        try {
            login =
                    ( Login )getEntityManager().createNamedQuery( "Login.findToken" ).setParameter( "token", token ).getSingleResult();
        }
        catch ( NoResultException e ) {
            e = null;
            throwException( 11 );
        }
        return login;
    }


    public Long getRecordCount()
    {
        Long recordCount;

        recordCount = ( Long )getEntityManager().createNativeQuery( "SELECT COUNT(*) FROM LOGIN" ).getSingleResult();
        return recordCount;
    }


    /**<penPageSequence>select o from Users o</penPageSequence>
     */
    @SuppressWarnings( "unckecked" )
    public List<ListLoginDTO> getLoginByRange( int firstResult, int maxResults )
    {
        Query query = getEntityManager().createNamedQuery( "Login.findAll" );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        return copy( query.getResultList() );
    }


    protected List<ListLoginDTO> copy( List<Login> list )
    {
        List<ListLoginDTO> dtos = null;

        if ( list == null )
            return dtos;
        dtos = new ArrayList<ListLoginDTO>( list.size() );
        for ( Login item : list ) {
            getEntityManager().refresh( item.getUserStatus() );
            dtos.add( DTOFactory.copy( item ) );
        }
        return dtos;

    }


    /**
     * Reenvia um email de confirmação de cadastro de loginSession.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    public void sendValidationEmail( UserDocumentDTO dto ) throws ApplicationException
    {
        Login login;

        login = getLogin( dto );
        login.getUserStatus();
        if ( login.getUserStatus().getId() != UserStatus.statusEmailNotValidated )
            throwException( 12 );
        sendMail( login, 1, null );
    }

    protected PersonSessionLocal getPersonSession()
    {
        return personSession;
    }

    protected UserSessionLocal getUserSession()
    {
        return userSession;
    }

    protected SystemParametersSessionLocal getSysParam()
    {
        return sysParam;
    }

    protected EmailSessionLocal getEmailTemplate()
    {
        return emailTemplate;
    }


    /**
     * Obtem o status do loginSession do usuário corrente (autenticado).
     *
     * @param currentUser AuthenticationDTO do usuário autenticado
     * @return Id do status do usuário
     */
    public Integer getStatus( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        Integer status = 0;

        Login login = getEntityManager().find( Login.class, currentUser.getUserId() );
        return login.getUserStatus().getId();
    }


    /**
     * Altera o status do usuário no banco de dados.
     *
     * @param currentUser Usuário autenticado.
     * @param newStatus Novo status a ser alterado no banco de dados.
     */
    public void setStatus( AuthenticationDTO currentUser, Integer newStatus ) throws ApplicationException
    {
        authenticate( currentUser );
        if ( SysUtils.isZero( newStatus ) )
            throwCommomRuntimeException( 3 );
        Login login = getEntityManager().find( Login.class, currentUser.getUserId() );
        login.setUserStatus( getEntityManager().find( UserStatus.class, newStatus ) );
    }

    public Integer getMessageTypeId()
    {
        return systemMessageTypeId;
    }

    public EntityManager getEntityManager()
    {
        return em;
    }

    public Login get( Integer id ) throws ApplicationException
    {
        return getEntityManager().find( Login.class, id );
    }

    public Login get( Person person ) throws ApplicationException
    {
        return getEntityManager().find( Login.class, person.getId() );
    }
}
