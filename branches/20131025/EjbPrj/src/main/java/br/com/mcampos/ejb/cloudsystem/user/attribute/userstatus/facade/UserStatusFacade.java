package br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface UserStatusFacade extends Serializable
{
	Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

	void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

	UserStatusDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

	UserStatusDTO add( AuthenticationDTO auth, UserStatusDTO dto ) throws ApplicationException;

	UserStatusDTO update( AuthenticationDTO auth, UserStatusDTO dto ) throws ApplicationException;

	List<UserStatusDTO> getAll() throws ApplicationException;
}
