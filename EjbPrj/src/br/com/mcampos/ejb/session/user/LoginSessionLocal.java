package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LoginSessionLocal
{
    /**
     * Adiciona um novo login ao sistema. Para adicionar este login, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     * Após incluido o login com os dados mais básicos necessários, o novo usuário do 
     * sistema DEVE completar o registro.
     *
     * @param dto DTO com os dados básicos para inclusão.
     * @exception InvalidParameterException
     */
    void add( RegisterDTO dto );
    void update( LoginDTO login );
    void delete( Integer id );
    LoginDTO loginUser ( LoginCredentialDTO dto );
    public void logoutUser ( LoginDTO dto );
    Long getRecordCount ();
    void delete( Integer[] logins );
    void updateLoginStatus ( Integer id, Integer newStatus );


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
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     * 
     * 
     * @param document UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    void makeNewPassword ( UserDocumentDTO document );
    
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
     * Reenvia um email de confirmação de cadastro de login.
     * 
     * 
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception InvalidParameterException
     */
    void sendValidationEmail ( UserDocumentDTO dto );
    
}
