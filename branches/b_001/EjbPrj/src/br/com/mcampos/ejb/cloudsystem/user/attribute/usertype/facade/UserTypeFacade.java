package br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface UserTypeFacade extends Serializable
{
	Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

	void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

	UserTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

	UserTypeDTO add( AuthenticationDTO auth, UserTypeDTO dto ) throws ApplicationException;

	UserTypeDTO update( AuthenticationDTO auth, UserTypeDTO dto ) throws ApplicationException;

	List<UserTypeDTO> getAll() throws ApplicationException;
}
