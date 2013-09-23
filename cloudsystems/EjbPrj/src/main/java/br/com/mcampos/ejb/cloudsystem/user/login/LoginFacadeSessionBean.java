package br.com.mcampos.ejb.cloudsystem.user.login;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.LoginCredentialDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.media.entity.UserMedia;
import br.com.mcampos.ejb.cloudsystem.user.media.session.UserMediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;
import br.com.mcampos.ejb.cloudsystem.user.media.type.session.UserMediaTypeSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "LoginFacadeSession", mappedName = "LoginFacadeSession" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class LoginFacadeSessionBean implements LoginFacadeSession
{
    @EJB
    SystemMessagesSessionLocal systemMessage;

    @EJB
    LoginSessionLocal loginSession;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private ClientSessionLocal clientSession;

    @EJB
    private UserMediaSessionLocal userMediaSession;

    @EJB
    private UserMediaTypeSessionLocal userMediaTypeSession;

    private static final Integer systemMessageTypeId = 1;

    public LoginFacadeSessionBean()
    {
    }


    protected LoginSessionLocal getLoginSession()
    {
        return loginSession;
    }


    /**
     * Adiciona um novo loginSession ao sistema. Para adicionar este loginSession, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     *
     * @param dto
     * @exception EJBException
     */
    public void add( RegisterDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 1 );
        loginSession.add( dto );
    }


    /**
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception EJBException
     */
    public void makeNewPassword( UserDocumentDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 1 );
        getLoginSession().makeNewPassword( dto );
    }

    /**
     * Altera a senha do usuário. Deve-se imaginar que o usuario não está logado no sistema
     * para alterar a sua senha.
     *
     * @param dto UserDocumentDTO - identificado do usuario via documento (Email)
     * @param oldPassword - A senha antiga a ser alterada
     * @param newPassword - A nova senha
     * @exception EJBException
     */
    public void changePassword( UserDocumentDTO dto, String oldPassword, String newPassword ) throws ApplicationException
    {
        if ( dto == null || SysUtils.isEmpty( oldPassword ) || SysUtils.isEmpty( newPassword ) )
            systemMessage.throwException( systemMessageTypeId, 1 );
        if ( SysUtils.isEmpty( oldPassword ) )
            systemMessage.throwException( systemMessageTypeId, 6 );
        if ( SysUtils.isEmpty( newPassword ) )
            systemMessage.throwException( systemMessageTypeId, 7 );
        getLoginSession().changePassword( dto, oldPassword, newPassword );
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
     * @exception EJBException
     */
    public void validateEmail( String token, String password ) throws ApplicationException
    {
        if ( SysUtils.isEmpty( password ) )
            systemMessage.throwException( systemMessageTypeId, 6 );
        if ( SysUtils.isEmpty( token ) )
            systemMessage.throwException( systemMessageTypeId, 7 );
        getLoginSession().validateEmail( token, password );
    }

    /**
     * Reenvia um email de confirmação de cadastro de loginSession.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception EJBException
     */
    public void sendValidationEmail( UserDocumentDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 1 );
        getLoginSession().sendValidationEmail( dto );
    }


    /**
     * Executa o loginSession no aplicativo se possível.
     *
     * @param dto Credenciais para realização do loginSession
     * @return LoginDTO dados do usuário autenticado
     * @throws ApplicationException
     */
    public AuthenticationDTO loginUser( LoginCredentialDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 1 );
        if ( SysUtils.isEmpty( dto.getPassword() ) )
            systemMessage.throwException( systemMessageTypeId, 6 );
        if ( SysUtils.isEmpty( dto.getDocuments() ) )
            systemMessage.throwException( systemMessageTypeId, 3 );
        return getLoginSession().loginUser( dto );
    }


    /**
     * Finaliza a sessão (não confundir com a sessão do browser) do usuário.
     *
     * @param dto LoginDTO
     * @throws ApplicationException
     */
    public void logoutUser( AuthenticationDTO dto ) throws ApplicationException
    {
        getLoginSession().logoutUser( dto );
    }


    /**
     * Obtem o status do loginSession do usuário corrente (autenticado).
     *
     * @param currentUser AuthenticationDTO do usuário autenticado
     * @return Id do status do usuário
     */
    public Integer getStatus( AuthenticationDTO currentUser ) throws ApplicationException
    {
        return getLoginSession().getStatus( currentUser );
    }


    /**
     * Altera o status do usuário no banco de dados.
     *
     * @param currentUser Usuário autenticado.
     * @param newStatus Novo status a ser alterado no banco de dados.
     */
    public void setStatus( AuthenticationDTO currentUser, Integer newStatus ) throws ApplicationException
    {
        getLoginSession().setStatus( currentUser, newStatus );
    }

    public MediaDTO[] getLogo( AuthenticationDTO currentUser ) throws ApplicationException
    {
        if ( currentUser == null || currentUser.getCurrentCompany() == null || currentUser.getUserId() == null )
            return null;
        MediaDTO[] medias = new MediaDTO[ 2 ];
        Company myCompany = companySession.get( currentUser.getCurrentCompany() );
        if ( myCompany != null ) {
            medias[ 0 ] = getLogo( myCompany );
        }
        Client sponsor = clientSession.getSponsor( myCompany );
        if ( sponsor != null ) {
            medias[ 1 ] = getLogo( sponsor.getCompany() );
        }
        return medias;
    }

    protected Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        Collaborator coll = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        return coll.getCompany();
    }

    private MediaDTO getLogo( Company company ) throws ApplicationException
    {
        UserMedia u = userMediaSession.get( company, getLogoMediaType() );
        MediaDTO m = ( u != null && u.getMedia() != null ) ? u.getMedia().toDTO() : null;
        if ( m != null ) /*Lazy Load Object*/
            m.setObject( u.getMedia().getObject() );
        return m;
    }

    private UserMediaType getLogoMediaType() throws ApplicationException
    {
        return userMediaTypeSession.get( UserMediaType.typeLogo );
    }

    public List<ListLoginDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException
    {
        List<Login> logins = loginSession.getAll();
        return LoginSessionBean.copy( logins );
    }
}
