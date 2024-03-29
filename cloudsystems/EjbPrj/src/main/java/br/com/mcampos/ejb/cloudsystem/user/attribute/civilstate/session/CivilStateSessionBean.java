package br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.entity.CivilState;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CivilStateSession", mappedName = "CivilStateSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CivilStateSessionBean extends Crud<Integer, CivilState> implements CivilStateSessionLocal
{
	public void delete( Integer key ) throws ApplicationException
	{
		delete( CivilState.class, key );
	}

	public CivilState get( Integer key ) throws ApplicationException
	{
		return get( CivilState.class, key );
	}

	public List<CivilState> getAll() throws ApplicationException
	{
		return getAll( CivilState.getAll );
	}

	public Integer nextIntegerId() throws ApplicationException
	{
		return nextIntegerId( CivilState.nextId );
	}
}


