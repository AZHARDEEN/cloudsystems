package br.com.mcampos.ejb.cloudsystem;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;

import java.util.List;

public interface AccessLogTypeInterface
{
    /**
     * Obtem a lista dos objetos.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return Lista de objetos.
     * @throws ApplicationException
     */
    List<AccessLogTypeDTO> getAccessLogTypes( AuthenticationDTO auth ) throws ApplicationException;

    /**
     * Obtém o próximo formId disponível.
     * Esta função obtém o próximo número disponível para o formId do Menu (Max(formId)+1).
     * Não há necessidade de usar type para inclusão visto que a atualização desta
     * tabela é mímina.
     *
     * @param auth .
     * @return O próximo formId disponível.
     * @throws ApplicationException
     */
    Integer getNextAccessLogTypeId( AuthenticationDTO auth ) throws ApplicationException;


    /**
     * Atualiza o Objeto.
     * Esta função é usada para atualizar um objecto (persistir) no banco de dados.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @param dto - O objeto a ser atualizado
     * @throws ApplicationException
     */
    AccessLogTypeDTO update( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException;


    /**
     * Adiciona um novo registro (Menu) no banco de dados - Persist
     * Insere um novo menu no banco de dados.
     *
     * @param auth.
     * @param dto - DTO com os dados no novo menu.
     * @throws ApplicationException
     */
    AccessLogTypeDTO add( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException;


    /**
     * Validates this DTO.
     * This interface is used to implement a full validation on this dto.
     *
     * @param dto - dto to validate
     * @return true or false. If this is a valid dto then return true
     * @throws ApplicationException
     */
    Boolean validate( AccessLogTypeDTO dto, Boolean isNew ) throws ApplicationException;


    /**
     * Delete an existing menu.
     *
     * @param auth - authentication dto.
     * @param id - menu formId.
     * @throws ApplicationException
     */
    void delete( AuthenticationDTO auth, AccessLogTypeDTO id ) throws ApplicationException;
}
