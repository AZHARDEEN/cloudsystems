package br.com.mcampos.ejb.cloudsystem.security.menu;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface MenuFacade extends Serializable
{
	/**
	 * Obtém a lista de tarefas associadas ao menu.
	 * Um menu deve ser associada a um tarefa, para que este, por sua vez, seja associada a uma role.
	 * Somente faz sentido associar um menu de último nível a alguma tarefa.
	 *
	 * @param auth - dto do usuário autenticado no sistema.
	 * @param menuId - Chave primária do menu.
	 * @return Lista de tarefas associadas ao menu.
	 * @throws ApplicationException
	 */
	List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException;


	/**
	 * Each menu is configured to show in a especific order, and this is the order.
	 *
	 *
	 * @param auth
	 * @param parentId
	 * @return next available type.
	 * @throws ApplicationException
	 */
	Integer getNextSequence( AuthenticationDTO auth, Integer parentId ) throws ApplicationException;


	/**
	 * Obtem a lista dos objetos.
	 *
	 * @param auth - dto do usuário autenticado no sistema.
	 * @return Lista de objetos.
	 * @throws ApplicationException
	 */
	List<MenuDTO> getParentMenus( AuthenticationDTO auth ) throws ApplicationException;


	/**
	 * Atualiza o Objeto.
	 * Esta função é usada para atualizar um objecto (persistir) no banco de dados.
	 *
	 * @param auth - dto do usuário autenticado no sistema.
	 * @param dto - O objeto a ser atualizado
	 * @throws ApplicationException
	 */
	MenuDTO update( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException;


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
	Integer getNextMenuId( AuthenticationDTO auth ) throws ApplicationException;


	/**
	 * Adiciona um novo registro (Menu) no banco de dados - Persist
	 * Insere um novo menu no banco de dados.
	 *
	 * @param auth.
	 * @param dto - DTO com os dados no novo menu.
	 * @throws ApplicationException
	 */
	MenuDTO add( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException;


	/**
	 * Validates this DTO.
	 * This interface is used to implement a full validation on this dto.
	 *
	 * @param dto - dto to validate
	 * @return true or false. If this is a valid dto then return true
	 * @throws ApplicationException
	 */
	Boolean validate( MenuDTO dto, Boolean isNew ) throws ApplicationException;


	/**
	 * Delete an existing menu.
	 *
	 * @param auth - authentication dto.
	 * @param id - menu formId.
	 * @throws ApplicationException
	 */
	void delete( AuthenticationDTO auth, MenuDTO id ) throws ApplicationException;

	void addMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException;

	void removeMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException;

	/**
	 * Obtem a lista dos objetos.
	 *
	 * @param auth - dto do usuário autenticado no sistema.
	 * @return Lista de objetos.
	 * @throws ApplicationException
	 */
	List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException;


}
