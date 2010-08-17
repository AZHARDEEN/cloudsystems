package br.com.mcampos.ejb.cloudsystem.user.login;


import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.LoginCredentialDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;


@Remote
public interface LoginFacadeSession
{
    /**
     * Adiciona um novo loginSession ao sistema. Para adicionar este loginSession, deve ser
     * observado que o mesmo depende do relacionamento com a entidade pessoa.
     *
     * @param dto
     * @exception EJBException
     */
    void add( RegisterDTO dto ) throws ApplicationException;

    /**
     * Altera a senha do usuário. Deve-se imaginar que o usuario não está logado no sistema
     * para alterar a sua senha.
     *
     * @param document UserDocumentDTO - identificado do usuario via documento (Email)
     * @param oldPassword - A senha antiga a ser alterada
     * @param newPassword - A nova senha
     * @exception EJBException
     */
    void changePassword( UserDocumentDTO document, String oldPassword, String newPassword ) throws ApplicationException;

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
    void validateEmail( String token, String password ) throws ApplicationException;

    /**
     * Cria uma nova senha para o usuario logar no sistema e envia esta senha via email.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception EJBException
     */
    void makeNewPassword( UserDocumentDTO dto ) throws ApplicationException;


    /**
     * Reenvia um email de confirmação de cadastro de loginSession.
     *
     *
     * @param dto UserDocumentDTO - identificao do usuario via documento (Email)
     * @exception EJBException
     */
    void sendValidationEmail( UserDocumentDTO dto ) throws ApplicationException;


    /**
     * Executa o loginSession no aplicativo se possível.
     *
     * @param dto Credenciais para realização do loginSession
     * @return AuthenticationDTO dados do usuário autenticado
     * @throws ApplicationException
     */
    AuthenticationDTO loginUser( LoginCredentialDTO dto ) throws ApplicationException;

    /**
     * Finaliza a sessão (não confundir com a sessão do browser) do usuário.
     *
     * @param dto LoginDTO
     * @throws ApplicationException
     */
    void logoutUser( AuthenticationDTO dto ) throws ApplicationException;

    /**
     * Obtem o status do loginSession do usuário corrente (autenticado).
     *
     * @param currentUser AuthenticationDTO do usuário autenticado
     * @return Id do status do usuário
     */
    Integer getStatus( AuthenticationDTO currentUser ) throws ApplicationException;

    /**
     * Altera o status do usuário no banco de dados.
     *
     * @param currentUser Usuário autenticado.
     * @param newStatus Novo status a ser alterado no banco de dados.
     */
    void setStatus( AuthenticationDTO currentUser, Integer newStatus ) throws ApplicationException;


    public MediaDTO[] getLogo( AuthenticationDTO currentUser ) throws ApplicationException;

    List<ListLoginDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;

}


