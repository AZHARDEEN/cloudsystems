package br.com.mcampos.ejb.cloudsystem.security.task;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface TaskFacade extends Serializable
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
	 * Obtém o próximo formId disponível.
	 * Esta função obtém o próximo número disponível para o formId do Menu (Max(formId)+1).
	 * Não há necessidade de usar type para inclusão visto que a atualização desta
	 * tabela é mímina.
	 *
	 * @param auth .
	 * @return O próximo formId disponível.
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
	 * @param id - menu formId.
	 * @throws ApplicationException
	 */
	void delete( AuthenticationDTO auth, TaskDTO id ) throws ApplicationException;

	TaskDTO getTask( AuthenticationDTO auth, Integer taskId ) throws ApplicationException;

	List<MenuDTO> getMenus( AuthenticationDTO auth, TaskDTO taskId ) throws ApplicationException;

	List<RoleDTO> getRoles( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException;

	List<TaskDTO> getSubTasks( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException;

	/**
	 * Obtem a lista dos objetos.
	 *
	 * @param auth - dto do usuário autenticado no sistema.
	 * @return Lista de objetos.
	 * @throws ApplicationException
	 */
	List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException;


	/**
	 * Obtem a lista dos objetos.
	 *
	 * @param auth - dto do usuário autenticado no sistema.
	 * @return Lista de objetos.
	 * @throws ApplicationException
	 */
	List<MenuDTO> getParentMenus( AuthenticationDTO auth ) throws ApplicationException;

	void remove( AuthenticationDTO auth, TaskDTO dtoTask, MenuDTO menuDTO ) throws ApplicationException;

	void add( AuthenticationDTO auth, TaskDTO dtoTask, MenuDTO menuDTO ) throws ApplicationException;

	RoleDTO getRootRole( AuthenticationDTO auth ) throws ApplicationException;

	void add( AuthenticationDTO auth, TaskDTO taskDTO, RoleDTO roleDTO ) throws ApplicationException;

	void remove( AuthenticationDTO auth, TaskDTO taskDTO, RoleDTO roleDTO ) throws ApplicationException;

}
