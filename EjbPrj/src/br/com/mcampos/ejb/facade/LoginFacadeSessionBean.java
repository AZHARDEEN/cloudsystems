package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.security.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;

import br.com.mcampos.ejb.session.user.LoginSessionLocal;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless( name = "LoginFacadeSession", mappedName = "CloudSystems-EjbPrj-LoginFacadeSession" )
@Remote
public class LoginFacadeSessionBean implements LoginFacadeSession
{
    @EJB
    SystemMessagesSessionLocal systemMessage;
    @EJB
    LoginSessionLocal login;

    private static final Integer systemMessageTypeId = 1;

    public LoginFacadeSessionBean()
    {
    }


    protected LoginSessionLocal getLogin()
    {
        return login;
    }


    /**
     * Adiciona um novo login ao sistema. Para adicionar este login, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     *
     * @param dto
     * @exception EJBException
     */
    public void add( RegisterDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 1 );
        login.add( dto );
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
        getLogin().makeNewPassword( dto );
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
        getLogin().changePassword( dto, oldPassword, newPassword );
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
        getLogin().validateEmail( token, password );
    }

    /**
     * Reenvia um email de confirmação de cadastro de login.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception EJBException
     */
    public void sendValidationEmail( UserDocumentDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 1 );
        getLogin().sendValidationEmail( dto );
    }


    /**
     * Executa o login no aplicativo se possível.
     *
     * @param dto Credenciais para realização do login
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
        return getLogin().loginUser( dto );
    }


    /**
     * Finaliza a sessão (não confundir com a sessão do browser) do usuário.
     *
     * @param dto LoginDTO
     * @throws ApplicationException
     */
    public void logoutUser( AuthenticationDTO dto ) throws ApplicationException
    {
        getLogin().logoutUser( dto );
    }


    /**
     * Obtem o status do login do usuário corrente (autenticado).
     *
     * @param currentUser AuthenticationDTO do usuário autenticado
     * @return Id do status do usuário
     */
    public Integer getStatus( AuthenticationDTO currentUser )
    {
        return getLogin().getStatus( currentUser );
    }


    /**
     * Altera o status do usuário no banco de dados.
     *
     * @param currentUser Usuário autenticado.
     * @param newStatus Novo status a ser alterado no banco de dados.
     */
    public void setStatus( AuthenticationDTO currentUser, Integer newStatus )
    {
        getLogin().setStatus( currentUser, newStatus );
    }
}
