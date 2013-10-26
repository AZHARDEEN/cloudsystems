package br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.entity.CivilState;

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
