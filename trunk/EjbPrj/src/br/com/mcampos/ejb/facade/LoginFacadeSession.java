package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;

import br.com.mcampos.dto.user.UserDocumentDTO;

import br.com.mcampos.dto.user.login.LoginDTO;

import javax.ejb.Remote;

@Remote
public interface LoginFacadeSession
{
    Boolean add(RegisterDTO dto);
    
    /**
     * Altera a senha do usuário. Deve-se imaginar que o usuario não está logado no sistema
     * para alterar a sua senha.
     *
     * @param document UserDocumentDTO - identificado do usuario via documento (Email)
     * @param oldPassword - A senha antiga a ser alterada
     * @param newPassword - A nova senha
     * @exception InvalidParameterException
     */
    void changePassword ( UserDocumentDTO document, String oldPassword, String newPassword );
    
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
    void validateEmail ( String token, String password );
    
    /**
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     * 
     * 
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    void makeNewPassword ( UserDocumentDTO dto );
    
    
    /**
     * Reenvia um email de confirmação de cadastro de login.
     * 
     * 
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    void sendValidationEmail ( UserDocumentDTO dto );
}


