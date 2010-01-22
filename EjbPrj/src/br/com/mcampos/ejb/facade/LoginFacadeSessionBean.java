package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;

import br.com.mcampos.ejb.session.user.LoginSessionLocal;

import br.com.mcampos.sysutils.SysUtils;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

@Stateless( name = "LoginFacadeSession", mappedName = "CloudSystems-EjbPrj-LoginFacadeSession" )
public class LoginFacadeSessionBean implements LoginFacadeSession
{
    @EJB
    SystemMessagesSessionLocal systemMessage;
    @EJB
    LoginSessionLocal login;

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
    public void add( RegisterDTO dto )
    {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
        login.add( dto );
    }


    /**
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception EJBException
     */
    public void makeNewPassword( UserDocumentDTO dto )
    {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
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
    public void changePassword( UserDocumentDTO dto, String oldPassword, String newPassword )
    {
        if ( dto == null || SysUtils.isEmpty( oldPassword ) || SysUtils.isEmpty( newPassword ) )
            systemMessage.throwMessage( 26 );

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
    public void validateEmail( String token, String password )
    {
        if ( SysUtils.isEmpty( token ) || SysUtils.isEmpty( password ) )
            systemMessage.throwMessage( 26 );
        getLogin().validateEmail( token, password );
    }

    /**
     * Reenvia um email de confirmação de cadastro de login.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception EJBException
     */
    public void sendValidationEmail( UserDocumentDTO dto )
    {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
        getLogin().sendValidationEmail( dto );
    }
}
