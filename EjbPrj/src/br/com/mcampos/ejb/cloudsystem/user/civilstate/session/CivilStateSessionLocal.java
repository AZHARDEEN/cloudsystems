package br.com.mcampos.ejb.cloudsystem.user.civilstate.session;


import br.com.mcampos.ejb.cloudsystem.user.civilstate.entity.CivilState;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CivilStateSessionLocal extends Serializable
{
	void delete( Integer key ) throws ApplicationException;

	CivilState get( Integer key ) throws ApplicationException;

	List<CivilState> getAll() throws ApplicationException;

	Integer nextIntegerId() throws ApplicationException;

	CivilState add( CivilState entity ) throws ApplicationException;

	CivilState update( CivilState entity ) throws ApplicationException;
}
