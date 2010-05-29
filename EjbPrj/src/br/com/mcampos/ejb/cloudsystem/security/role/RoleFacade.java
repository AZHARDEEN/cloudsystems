package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface RoleFacade extends Serializable
{
	List<RoleDTO> getRoles( AuthenticationDTO auth ) throws ApplicationException;

	RoleDTO getRootRole( AuthenticationDTO auth ) throws ApplicationException;

	List<RoleDTO> getChildRoles( AuthenticationDTO auth, RoleDTO parent ) throws ApplicationException;

	RoleDTO add( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

	RoleDTO update( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

	Integer getRoleMaxId( AuthenticationDTO auth ) throws ApplicationException;

	void delete( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

	List<TaskDTO> getTasks( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

	List<MenuDTO> getMenus( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

	/**
	 * Obtem a lista dos objetos.
	 *
	 * @param auth - dto do usuário autenticado no sistema.
	 * @return Lista de objetos.
	 * @throws ApplicationException
	 */
	List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException;

	void remove( AuthenticationDTO auth, RoleDTO roleDTO, TaskDTO taskDTO ) throws ApplicationException;

	void add( AuthenticationDTO auth, RoleDTO roleDTO, TaskDTO taskDTO ) throws ApplicationException;

}
