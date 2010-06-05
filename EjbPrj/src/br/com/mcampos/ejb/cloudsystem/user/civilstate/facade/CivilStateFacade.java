package br.com.mcampos.ejb.cloudsystem.user.civilstate.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CivilStateFacade extends Serializable
{
	Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

	void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

	CivilStateDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

	CivilStateDTO add( AuthenticationDTO auth, CivilStateDTO dto ) throws ApplicationException;

	CivilStateDTO update( AuthenticationDTO auth, CivilStateDTO dto ) throws ApplicationException;

	List<CivilStateDTO> getAll() throws ApplicationException;
}
