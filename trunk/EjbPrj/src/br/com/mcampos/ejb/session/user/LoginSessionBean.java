package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.system.SendMailDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.core.util.RandomString;
import br.com.mcampos.ejb.entity.login.AccessLog;
import br.com.mcampos.ejb.entity.login.AccessLogType;
import br.com.mcampos.ejb.entity.login.LastUsedPassword;
import br.com.mcampos.ejb.entity.login.LastUsedPasswordPK;
import br.com.mcampos.ejb.entity.system.SystemParameters;
import br.com.mcampos.ejb.entity.login.Login;


import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.UserDocument;
import br.com.mcampos.ejb.entity.user.Users;
import br.com.mcampos.ejb.entity.user.attributes.UserStatus;

import br.com.mcampos.ejb.session.system.SendMailSessionLocal;
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

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;


import org.jasypt.util.password.BasicPasswordEncryptor;

@Stateless( name = "LoginSession", mappedName = "CloudSystems-EjbPrj-LoginSession" )
@Local
public class LoginSessionBean implements LoginSessionLocal
{
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

    private static final Integer systemMessageTypeId = 1;


    public LoginSessionBean ()
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
    public void add ( RegisterDTO dto ) throws ApplicationException
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

    protected boolean verifyDocuments ( List<UserDocumentDTO> list )
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

    protected void sendMail ( RegisterDTO dto, String token ) throws ApplicationException
    {
        String messageBody;
        
        if ( dto == null || dto.getDocuments() == null )
            throwRuntimeException( 1 );
        if ( SysUtils.isEmpty( token  ) )
            throwRuntimeException( 11 );
        messageBody = getSysParam().getValue( "TemplateEmailConfirmation" );
        if ( messageBody == null ) 
            getSystemMessage().throwRuntimeException( systemMessageTypeId, 5 );
        messageBody = translateMessageTokens( messageBody, dto, token );
        
        
        SendMailDTO emailDTO = new SendMailDTO();
        for ( UserDocumentDTO item : dto.getDocuments() ) {
            if ( item.getDocumentType().getId().equals( UserDocumentDTO.typeEmail ) ) 
                emailDTO.addRecipient( item.getCode() );
        }
        emailDTO.setSubject( "Validação de Email para uso no sistema CloudSystems" );
        emailDTO.setBody( messageBody );
        sendMail.sendMail( emailDTO );
    }

    protected String translateMessageTokens ( String msg, RegisterDTO dto, String token )
    {
        msg = msg.replaceAll( "<<@@LOGIN_NAME@@>>", dto.getName() );
        for ( UserDocumentDTO item : dto.getDocuments() ) {
            if ( item.getDocumentType().getId().equals( UserDocumentDTO.typeEmail ) ) {
                msg = msg.replaceAll( "<<@@EMAIL@@>>", item.getCode() );
                break;
            }
        }
        msg = msg.replaceAll( "<<@@TOKEN@@>>", token );
        return msg;
    }

    protected void add ( RegisterDTO dto, Person person ) throws ApplicationException
    {
        String encryptedPassword;
        BasicPasswordEncryptor passwordEncryptor;

        Login login = new Login( null, dto.getPassword(), new UserStatus( UserStatus.statusEmailNotValidated ) );
        login.setPerson( person );
        passwordEncryptor = new BasicPasswordEncryptor();
        encryptedPassword = passwordEncryptor.encryptPassword( login.getPassword() );
        setPasswordExpirationDate( login );
        login.setPassword( encryptedPassword );
        login.setToken( RandomString.randomstring() );
        if ( SysUtils.isEmpty( login.getToken() ) )
            throwRuntimeException( 11 );
        em.persist( login );
        storeOldPassword( login );
        storeAccessLog( login, dto, 2 );
        sendMail( dto, login.getToken() );
    }

    protected void setPasswordExpirationDate ( Login login )
    {
        Integer days;
        SystemParameters sysParam = null;
        Exception stackE = null;
        Calendar now;

        try {
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
        catch ( Exception e ) {
            stackE = e;
        }
        finally {
            if ( sysParam == null )
                throw new EJBException( "Erro na tabela de parametros do sistema", stackE );
        }
    }

    public void update ( LoginDTO dto )
    {
        Login login = DTOFactory.copy( dto );
        Users old;


        /*
         * In theory, this is an update.
         */
        login.getPerson().setUpdateDate( new Timestamp( Calendar.getInstance().getTime().getTime() ) );
        try {
            old = em.find( Users.class, login.getPerson().getId() );
        }
        catch ( Exception e ) {
            throw new EJBException( "Atualização de login sem a devida chave primária! " );
        }
        login.getPerson().setInsertDate( old.getInsertDate() );
        em.merge( login );
    }

    public void delete ( Integer id )
    {
        Login login;


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


    public void delete ( Integer[] logins )
    {
        if ( logins == null || logins.length == 0 )
            throw new IllegalArgumentException( "Logins cannot be null or empty" );

        for ( Integer id : logins ) {
            delete( id );
        }
    }

    public void updateLoginStatus ( Integer id, Integer newStatus )
    {
        Login login = em.find( Login.class, id );
        if ( login == null || newStatus == null )
            throw new EJBException( "Não existe login" );
        login.setUserStatus( em.find( UserStatus.class, newStatus ) );
    }


    public void logoutUser ( LoginDTO dto )
    {
        if ( dto == null || dto.getUserId() == null )
            return;
        Login login = em.find( Login.class, dto.getUserId() );
        if ( login == null )
            return;
        storeAccessLog( login, null, AccessLogType.accessLogTypeLogout );

    }

    protected Login getLoginByDocument ( UserDocumentDTO document )
    {
        Login login;
        Query query;
        UserDocument userDocument;

        try {

            query = em.createNamedQuery( "UserDocument.findByDocument" );
            query.setParameter( "document", document.getCode() );
            query.setParameter( "docType", document.getDocumentType().getId() );
            userDocument = ( UserDocument )query.getSingleResult();
            login = em.find( Login.class, userDocument.getUserId() );
        }
        catch ( Exception e ) {
            throw new EJBException( "Usuário inválido, não foi possível realizar o login.", e );
        }
        return login;
    }


    public LoginDTO loginUser ( LoginCredentialDTO dto )
    {
        Login login = null;
        BasicPasswordEncryptor passwordEncryptor;
        SystemParameters sysParam = null;

        for ( UserDocumentDTO doc : dto.getDocuments() ) {
            login = getLoginByDocument( doc );
            if ( login != null )
                break;
        }
        if ( login == null )
            throw new EJBException( "Usuário o senha inválida." );
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
            return null;
        }
        login.setTryCount( 0 );
        storeAccessLog( login, dto, AccessLogType.accessLogTypeNormalLogin );
        /*
         * If the user password has expired, we may allow login,
         * but we'll denied every operation that depends on this login.
         */
        Date now = new Date();
        if ( login.getPasswordExpirationDate().compareTo( new Timestamp( now.getTime() ) ) < 0 )
            login.setUserStatus( em.find( UserStatus.class, UserStatus.statusExpiredPassword ) );
        LoginDTO loginDTO = DTOFactory.copy( login, false );
        List<Collaborator> business = collaborator.getCompanies( login.getUserId() );
        if ( SysUtils.isEmpty( business ) == false ) {
            for ( Collaborator col : business ) {
                UserDTO userDTO = DTOFactory.copy( col.getCompany() );
                loginDTO.addBusinessEntity( userDTO );
            }
        }
        return loginDTO;
    }

    protected void storeAccessLog ( Login login, LoginCredentialDTO dto, Integer accessLogType )
    {
        AccessLog log;

        log = new AccessLog();

        log.setLogin( login );
        log.setLoginType( em.find( AccessLogType.class, accessLogType ) );
        log.setIp( dto != null ? dto.getRemoteAddr() : "127.1.1.1" );
        log.setComputer( dto != null ? dto.getRemoteHost() : null );

        em.persist( log );
    }

    /**
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     *
     *
     * @param document UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    public void makeNewPassword ( UserDocumentDTO document )
    {
        Login login;
        BasicPasswordEncryptor passwordEncryptor;
        String password;

        login = getLoginByDocument( document );
        if ( login == null )
            throw new EJBException( "Usuário o senha inválida." );
        verifyUserStatus( login );
        passwordEncryptor = new BasicPasswordEncryptor();
        password = RandomString.randomstring();
        storeOldPassword( login );
        login.setPassword( passwordEncryptor.encryptPassword( password ) );
        storeOldPassword( login );
        login.setTryCount( 0 );
        /*
         * TODO: SEND EMAIL
         */
    }


    protected void storeOldPassword ( Login login )
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


    protected void verifyUserStatus ( Login login )
    {
        if ( login.getUserStatus().getAllowLogin() == true )
            return;

        switch ( ( int )( login.getUserStatus().getId() ) ) {
        case UserStatus.statusMaxLoginTryCount:
            throw new EJBException( "Usuário bloqueado por excesso de tentativas de login." );
        case UserStatus.statusInativo:
            throw new EJBException( "Usuário está inativo." );
        case UserStatus.statusEmailNotValidated:
            throw new EJBException( "O email ainda não foi validado. Valide o email antes." );
        default:
            throw new EJBException( "Usuário não pode usar o sistema." );
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
    public void changePassword ( UserDocumentDTO document, String oldPassword, String newPassword )
    {
        Login login;
        BasicPasswordEncryptor passwordEncryptor;

        login = getLoginByDocument( document );
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
        /*
         * TODO: SEND EMAIL
         */
    }


    protected Boolean isPasswordUsed ( Login login, String newPassword )
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

    @TransactionAttribute( value = TransactionAttributeType.REQUIRES_NEW )
    protected void incrementTryCount ( Login login )
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
    public void validateEmail ( String token, String password )
    {
        Login login;
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        try {
            login = ( Login )em.createNamedQuery( "Login.findToken" ).setParameter( "token", token ).getSingleResult();
        }
        catch ( Exception e ) {
            throw new EJBException( "Token de validação não identificado.", e );
        }
        if ( login == null )
            throw new EJBException( "Token de segurança não identificado." );
        if ( login.getUserStatus().getId() != UserStatus.statusEmailNotValidated )
            throw new EJBException( "Usuário não pode validar o email." );
        if ( passwordEncryptor.checkPassword( password, login.getPassword() ) == false ) {
            incrementTryCount( login );
            throw new EJBException( "A senha atual está inválida." );
        }
        login.setTryCount( 0 );
        login.setUserStatus( em.find( UserStatus.class, UserStatus.statusFullfillRecord ) );
        login.setToken( null );
    }


    public Long getRecordCount ()
    {
        Long recordCount;

        recordCount = ( Long )em.createNativeQuery( "SELECT COUNT(*) FROM LOGIN" ).getSingleResult();
        return recordCount;
    }


    /** <code>select o from Users o</code> */
    @SuppressWarnings( "unckecked" )
    public List<ListLoginDTO> getLoginByRange ( int firstResult, int maxResults )
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


    protected List<ListLoginDTO> copy ( List<Login> list )
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
    public void sendValidationEmail ( UserDocumentDTO dto )
    {

    }

    protected PersonSessionLocal getPersonSession ()
    {
        return personSession;
    }

    protected UserSessionLocal getUserSession ()
    {
        return userSession;
    }

    protected SystemMessagesSessionLocal getSystemMessage ()
    {
        return systemMessage;
    }

    protected SystemParametersSessionLocal getSysParam ()
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
    protected void throwException ( int id ) throws ApplicationException
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
    protected void throwRuntimeException ( int id ) throws ApplicationException
    {
        getSystemMessage().throwRuntimeException( systemMessageTypeId, id );
    }
}
