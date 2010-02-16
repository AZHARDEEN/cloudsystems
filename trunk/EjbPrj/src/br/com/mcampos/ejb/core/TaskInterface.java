package br.com.mcampos.ejb.core;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public interface TaskInterface
{
    /**
     * Obtem a lista dos objetos.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return Lista de objetos.
     * @throws ApplicationException
     */
    List<TaskDTO> getTasks( AuthenticationDTO auth ) throws ApplicationException;


    /**
     * Atualiza o Objeto.
     * Esta função é usada para atualizar um objecto (persistir) no banco de dados.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @param dto - O objeto a ser atualizado
     * @throws ApplicationException
     */
    TaskDTO update( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException;


    /**
     * Obtém o próximo id disponível.
     * Esta função obtém o próximo número disponível para o id do Menu (Max(id)+1).
     * Não há necessidade de usar sequence para inclusão visto que a atualização desta
     * tabela é mímina.
     *
     * @param auth.
     * @return O próximo id disponível.
     * @throws ApplicationException
     */
    Integer getNextTaskId( AuthenticationDTO auth ) throws ApplicationException;


    /**
     * Adiciona um novo registro (Menu) no banco de dados - Persist
     * Insere um novo menu no banco de dados.
     *
     * @param auth.
     * @param dto - DTO com os dados no novo menu.
     * @throws ApplicationException
     */
    TaskDTO add( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException;


    /**
     * Validates this DTO.
     * This interface is used to implement a full validation on this dto.
     *
     * @param dto - dto to validate
     * @return true or false. If this is a valid dto then return true
     * @throws ApplicationException
     */
    Boolean validate( TaskDTO dto, Boolean isNew ) throws ApplicationException;


    /**
     * Delete an existing menu.
     *
     * @param auth - authentication dto.
     * @param id - menu id.
     * @throws ApplicationException
     */
    void delete( AuthenticationDTO auth, TaskDTO id ) throws ApplicationException;
}
