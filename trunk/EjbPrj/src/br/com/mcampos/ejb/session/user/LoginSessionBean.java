package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.BasicSecurityDTO;
import br.com.mcampos.dto.system.SendMailDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.dto.security.LoginCredentialDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.core.util.RandomString;
import br.com.mcampos.ejb.entity.login.AccessLog;
import br.com.mcampos.ejb.entity.login.AccessLogType;
import br.com.mcampos.ejb.entity.login.LastUsedPassword;
import br.com.mcampos.ejb.entity.login.LastUsedPasswordPK;
import br.com.mcampos.ejb.entity.system.SystemParameters;
import br.com.mcampos.ejb.entity.login.Login;


import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.UserDocument;
import br.com.mcampos.ejb.entity.user.attributes.UserStatus;

import br.com.mcampos.ejb.session.system.EmailSessionLocal;
import br.com.mcampos.ejb.session.system.SendMailSessionLocal;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionBean;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.session.system.SystemParametersSessionLocal;
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
import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;


import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;


@Stateless( name = "LoginSession", mappedName = "CloudSystems-EjbPrj-LoginSession" )
@Local
public class LoginSessionBean implements LoginSessionLocal
{
    private static final String encprytPassword = "Nj9nQ6jz6Bt3";

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;
    @EJB
    CollaboratorSessionLocal collaborator;

    @EJB
    PersonSessionLocal personSession;

    @EJB
    UserSessionLocal userSession;

    @EJB
    SystemMessagesSessionLocal systemMessage;

    @EJB
    SendMailSessionLocal sendMail;

    @EJB
    SystemParametersSessionLocal sysParam;

    @EJB
    EmailSessionLocal emailTemplate;

    private static final Integer systemMessageTypeId = 1;
    private static final Integer templateEmailValidation = 1;


    public LoginSessionBean()
    {
    }

    /**
     * Adiciona um novo login ao sistema. Para adicionar este login, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     * Após incluido o login com os dados mais básicos necessários, o novo usuário do
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
            person = getPersonSession().add( DTOFactory.copy( dto ) );
        }
        if ( person.getLogin() != null )
            throwRuntimeException( 5 );
        add( dto, person );
    }

    /**
     * Adiciona um novo login ao sistema. Para adicionar este login, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     * Após incluido o login com os dados mais básicos necessários, o novo usuário do
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

        Login login = new Login( null, dto.getPassword(), new UserStatus( UserStatus.statusEmailNotValidated ) );
        /*
         * Nunca esquecer de vincular os objetos. Quando um objeto é instanciado
         * ele mantem suas características. Neste exemplo: foi instanciado uma
         * pessoa, que neste momento não possuia login. Ainda que o login seja
         * inserido no banco de dados, fica a situação inicial - sem login.
         */
        login.setPerson( person );
        passwordEncryptor = new BasicPasswordEncryptor();
        encryptedPassword = passwordEncryptor.encryptPassword( login.getPassword() );
        setPasswordExpirationDate( login );
        login.setPassword( encryptedPassword );
        login.setToken( RandomString.randomstring() );
        if ( SysUtils.isEmpty( login.getToken() ) )
            throwRuntimeException( 8 );
        em.persist( login );
        storeOldPassword( login );
        storeAccessLog( login, dto, 2 );
        sendMail( login, templateEmailValidation, null );
    }


    /**
     * Verifica se a lista de documentos inforamada é suficiente para o cadastramento
     * do novo login. Neste momento, devem existir ao menos um email e um cpf
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
     * vinculados as rotinas de cadastramento, validação e confirmação de login.
     *
     * @param login Entity Login
     * @param templateId id do template no banco de dados
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
            sysParam = em.find( SystemParameters.class, SystemParameters.passwordValidDays );
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
     * Exclui um login ativo no banco de dados. Um login não deve ser excluído
     * fisicamente do banco de dados. Estou pensando somente em fechar (Data fim).
     *
     * @param id Person Id a ser excluído.
     */
    public void delete( Integer id )
    {
        Login login;

        /*
         * TODO: esta rotina ainda não está validada.
         */
        if ( id == null )
            throw new IllegalArgumentException( "Login ID cannot be null " );
        login = em.find( Login.class, id );
        if ( login == null )
            return;
        /*
         * Regra: Se o login está aguardando email, apenas exclua normalmente,
         * pois significa que este usuário nunca logou no sistema.
         */
        if ( login.getUserStatus().getId() == UserStatus.statusEmailNotValidated )
            em.remove( login );
    }


    /**
     * Exclui uma lista de logins.
     *
     * @param logins
     */
    public void delete( Integer[] logins )
    {
        if ( logins == null || logins.length == 0 )
            throw new IllegalArgumentException( "Logins cannot be null or empty" );

        for ( Integer id : logins ) {
            delete( id );
        }
    }

    /**
     * Atualiza o status do login.
     *
     * @param id
     * @param newStatus
     */
    public void updateLoginStatus( Integer id, Integer newStatus )
    {
        Login login = em.find( Login.class, id );
        if ( login == null || newStatus == null )
            throw new EJBException( "Não existe login" );
        login.setUserStatus( em.find( UserStatus.class, newStatus ) );
    }


    /**
     * Finaliza a sessão do usuário (no banco de dados). Este função tem como
     * finalidade incluir um registro no banco de dados finalizando o login.
     *
     * @param dto AuthenticationDTO
     */
    public void logoutUser( AuthenticationDTO dto ) throws ApplicationException
    {
        if ( dto == null || dto.getUserId() == null )
            return;
        Login login = em.find( Login.class, dto.getUserId() );
        if ( login == null )
            return;
        storeAccessLog( login, dto, AccessLogType.accessLogTypeLogout );

    }

    /**
     * Dado uma lista de documentos, obter o login associado aos documentos.
     * Uma regra: todos os documentos DEVEM pertencer a um e somente um login.
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
        Login login = person.getLogin();
        if ( login == null )
            throwException( 14 );
        return login;
    }

    /**
     * Obtem um login através de um documento.
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
        if ( person.getLogin() == null )
            throwException( 14 );
        return person.getLogin();
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
        em.refresh( login );
        verifyUserStatus( login );
        passwordEncryptor = new BasicPasswordEncryptor();
        if ( passwordEncryptor.checkPassword( dto.getPassword(), login.getPassword() ) == false ) {
            Integer tryCount;

            login.incrementTryCount();
            try {
                sysParam = em.find( SystemParameters.class, SystemParameters.maxLoginTryCount );
                tryCount = Integer.parseInt( sysParam.getValue() );
            }
            catch ( Exception e ) {
                tryCount = 5;
            }
            if ( login.getTryCount() > tryCount ) {
                login.setUserStatus( em.find( UserStatus.class, UserStatus.statusMaxLoginTryCount ) );
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
            login.setUserStatus( em.find( UserStatus.class, UserStatus.statusExpiredPassword ) );
            throwException( 19 );
        }
        AuthenticationDTO retDTO = new AuthenticationDTO();
        retDTO.setUserId( login.getUserId() );
        retDTO.setSessionId( dto.getSessionId() );
        retDTO.setAuthenticationId( authToken );
        retDTO.setRemoteAddr( dto.getRemoteAddr() );
        retDTO.setRemoteHost( dto.getRemoteHost() );
        return retDTO;
    }

    /**
     * Para todo o acesso ao sistema ( Login, Logout, troca de senha, etc)
     * um registro de log será gravado.
     *
     * @param login Entity Login.
     * @param dto Credenciais de login.
     * @param accessLogType Tipo de log a ser armazenado.
     */
    protected String storeAccessLog( Login login, BasicSecurityDTO dto, Integer accessLogType ) throws ApplicationException
    {
        AccessLog log;

        if ( login == null )
            throwException( 1 );

        log = new AccessLog();

        log.setLogin( login );
        log.setLoginType( em.find( AccessLogType.class, accessLogType ) );
        log.setIp( dto != null ? dto.getRemoteAddr() : "127.0.0.1" );
        log.setComputer( dto != null ? dto.getRemoteHost() : null );
        log.setSessionId( dto.getSessionId() );
        if ( dto != null ) {
            BasicTextEncryptor encrypt = new BasicTextEncryptor();
            encrypt.setPassword( "Nj9nQ6jz6Bt3" );
            log.setAuthenticationId( encrypt.encrypt( dto.getSessionId() ) );
        }
        else
            return null;
        em.persist( log );
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
     * e o sistema não permite que esta senha seja usada novamente pelo mesmo login.
     *
     *
     * @param login
     */
    protected void storeOldPassword( Login login )
    {
        LastUsedPassword lastUsedPassword;
        Timestamp now;

        try {
            now = new Timestamp( Calendar.getInstance().getTime().getTime() );
            lastUsedPassword = em.find( LastUsedPassword.class, new LastUsedPasswordPK( login.getPassword(), login.getUserId() ) );
            if ( lastUsedPassword == null ) {
                lastUsedPassword = new LastUsedPassword( now, login.getPassword(), null, login.getUserId() );
                lastUsedPassword.setLogin( login );
                em.persist( lastUsedPassword );
            }
            else {
                lastUsedPassword.setToDate( now );
            }
        }
        catch ( Exception e ) {
            throw new EJBException( "Erro ao armazenar a senha usada.", e );
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
        case UserStatus.statusMaxLoginTryCount: throwException( 15 );
        case UserStatus.statusInativo: throwException( 16 );
        case UserStatus.statusEmailNotValidated: throwException( 17 );
        default: throwException( 18 );
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
        login.setUserStatus( em.find( UserStatus.class, UserStatus.statusOk ) );
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

            retList = em.createNamedQuery( "LastUsedPassword.findAll" ).setParameter( "id", login.getUserId() ).getResultList();
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
        em.merge( login );
        login.incrementTryCount();
        em.flush();
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
        login.setUserStatus( em.find( UserStatus.class, UserStatus.statusFullfillRecord ) );
        login.setToken( null );

        sendMail( login, 2, null );
    }

    /**
     * Quando um novo usuário se cadastra no sistema, é enviado um email de confirmação
     * com um código de acesso (token). Este código é usado para validar o email dentro
     * do sistema. Esta função localiza o novo login através deste token - que por sua
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
            login = ( Login )em.createNamedQuery( "Login.findToken" ).setParameter( "token", token ).getSingleResult();
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

        recordCount = ( Long )em.createNativeQuery( "SELECT COUNT(*) FROM LOGIN" ).getSingleResult();
        return recordCount;
    }


    /** <code>select o from Users o</code> */
    @SuppressWarnings( "unckecked" )
    public List<ListLoginDTO> getLoginByRange( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery( "Login.findAll" );
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
            em.refresh( item.getUserStatus() );
            dtos.add( DTOFactory.copy( item ) );
        }
        return dtos;

    }


    /**
     * Reenvia um email de confirmação de cadastro de login.
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

    protected SystemMessagesSessionLocal getSystemMessage()
    {
        return systemMessage;
    }

    protected SystemParametersSessionLocal getSysParam()
    {
        return sysParam;
    }

    /**
     * Lança uma exceção que NÃO causa rollback da transação, ou seja,
     * NÃO É derivada de runtime-excpetion
     *
     * @param id - id da mensagem a ser exibida. Cadastrada na tabela SystemMessages
     * @throws ApplicationException
     */
    protected void throwException( int id ) throws ApplicationException
    {
        getSystemMessage().throwException( systemMessageTypeId, id );
    }


    /**
     * Lança uma exceção que PROVOCA rollback da transação, ou seja,
     * E derivada de runtime-excpetion
     *
     * @param id - id da mensagem a ser exibida. Cadastrada na tabela SystemMessages
     * @throws ApplicationException
     */
    protected void throwRuntimeException( int id ) throws ApplicationException
    {
        getSystemMessage().throwRuntimeException( systemMessageTypeId, id );
    }

    protected EmailSessionLocal getEmailTemplate()
    {
        return emailTemplate;
    }


    /**
     * Obtem o status do login do usuário corrente (autenticado).
     *
     * @param currentUser AuthenticationDTO do usuário autenticado
     * @return Id do status do usuário
     */
    public Integer getStatus( AuthenticationDTO currentUser )
    {
        authenticate( currentUser );
        Integer status = 0;

        Login login = em.find( Login.class, currentUser.getUserId() );

        return login.getUserStatus().getId();
    }


    /**
     * Altera o status do usuário no banco de dados.
     *
     * @param currentUser Usuário autenticado.
     * @param newStatus Novo status a ser alterado no banco de dados.
     */
    public void setStatus( AuthenticationDTO currentUser, Integer newStatus )
    {
        authenticate( currentUser );
        if ( SysUtils.isZero( newStatus ) )
            systemMessage.throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 3 );

        Login login = em.find( Login.class, currentUser.getUserId() );

        login.setUserStatus( em.find( UserStatus.class, newStatus ) );
    }


    /**
     * Autentica o usuário. Esta será a função mais usada de todas.
     * Para QUALQUER operacao, esta função deverá ser chamada antes. Entre os
     * testes a serem executados podemos listar:
     * 1) Existe um login no banco de dados com o UserId passado?
     * 2) Existe algum token no banco de dados igual ao token passado?
     *    O log existe? O log localizado é igual ao usuário corrente?
     * 3) O Token passado é valido?
     *
     * @param currentUser
     * @return boolean true para usuário autenticado ou false
     */
    public void authenticate( AuthenticationDTO currentUser )
    {
        if ( currentUser == null || SysUtils.isZero( currentUser.getUserId() ) )
            systemMessage.throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 3 );


        AccessLog log = null;
        /*
         * Existe um login no banco de dados com o UserId passado?
         */
        Login login = em.find( Login.class, currentUser.getUserId() );
        if ( login == null )
            systemMessage.throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 2 );

        /*
         * Existe algum token no banco de dados igual ao token passado?
         * O log existe? O log localizado é igual ao usuário corrente?
         */
        if ( SysUtils.isEmpty( currentUser.getAuthenticationId() ) )
            systemMessage.throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 3 );
        try {
            log = ( AccessLog )em.createNamedQuery( "AccessLog.findToken" ).setParameter( "token", currentUser.getAuthenticationId() ).setParameter( "userId", currentUser.getUserId() ).getSingleResult();
        }
        catch ( NoResultException e ) {
            /*
             * O log não existe. Não existe este usuário com o token corrente...
             */
            systemMessage.throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 2 );
        }

        /*
         * O Token passado é valido?
         */
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword( "Nj9nQ6jz6Bt3" );
        String descryptedId = encryptor.decrypt( log.getAuthenticationId() );
        if ( descryptedId.equals( currentUser.getSessionId() ) == false )
            systemMessage.throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 2 );
        if ( log.getSessionId().equals( currentUser.getSessionId() ) == false )
            systemMessage.throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 2 );
    }
}
