package br.com.mcampos.util.business;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.LoginCredentialDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.user.login.LoginFacadeSession;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

public class LoginLocator extends BusinessDelegate
{
    public LoginLocator()
    {
        super();
    }


    protected LoginFacadeSession getFacade()
    {
        Object obj;

        obj = getEJBSession( LoginFacadeSession.class );
        if ( obj == null )
            throw new InvalidParameterException( "Ejb Session is null" );
        return ( LoginFacadeSession )obj;
    }


    /**
     * Cria - ou adiciona - um novo login no sistema.
     *
     *
     * @param dto RegisterDTO dto com os dados necessários para criação do login.
     * Os dados são os mínimos necessários, depois o novo usuário deverá completar o seu registro.
     * @exception InvalidParameterException
     */
    public void create( RegisterDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            throw new InvalidParameterException( "Nenhum parametro pode ser nulo ou vazio" );
        getFacade().add( dto );
    }

    /**
     * Altera a senha do usuário. Deve-se imaginar que o usuario não está logado no sistema
     * para alterar a sua senha.
     *
     * @param dto UserDocumentDTO - identificado do usuario via documento (Email)
     * @param oldPassword - A senha antiga a ser alterada
     * @param newPassword - A nova senha
     * @exception InvalidParameterException
     */
    public void changePassword( UserDocumentDTO dto, String oldPassword, String newPassword ) throws ApplicationException
    {
        if ( dto == null || oldPassword == null || newPassword == null )
            throw new InvalidParameterException( "Nenhum parametro pode ser nulo" );
        if ( oldPassword.equals( newPassword ) )
            throw new InvalidParameterException( "As senhas não podem ser iguais" );

        getFacade().changePassword( dto, oldPassword, newPassword );
    }


    /**
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    public void makeNewPasssword( UserDocumentDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            throw new InvalidParameterException( "Nenhum parametro pode ser nulo ou vazio" );

        getFacade().makeNewPassword( dto );
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
        if ( SysUtils.isEmpty( token ) || SysUtils.isEmpty( password ) )
            throw new InvalidParameterException( "Nenhum parametro pode ser nulo ou vazio" );

        getFacade().validateEmail( token, password );
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
        if ( dto == null )
            throw new InvalidParameterException( "Nenhum parametro pode ser nulo ou vazio" );

        getFacade().sendValidationEmail( dto );
    }


    public AuthenticationDTO loginUser( LoginCredentialDTO dto ) throws ApplicationException
    {
        return getFacade().loginUser( dto );
    }

    public void logoutUser( AuthenticationDTO dto ) throws ApplicationException
    {
        getFacade().logoutUser( dto );
    }

    /**
     * Obtem o status do login do usuário corrente (autenticado).
     *
     * @param currentUser AuthenticationDTO do usuário autenticado
     * @return Id do status do usuário
     */
    public Integer getStatus( AuthenticationDTO currentUser ) throws ApplicationException
    {
        return getFacade().getStatus( currentUser );
    }


    /**
     * Altera o status do usuário no banco de dados.
     *
     * @param currentUser Usuário autenticado.
     * @param newStatus Novo status a ser alterado no banco de dados.
     */
    public void setStatus( AuthenticationDTO currentUser, Integer newStatus ) throws ApplicationException
    {
        getFacade().setStatus( currentUser, newStatus );
    }
}
