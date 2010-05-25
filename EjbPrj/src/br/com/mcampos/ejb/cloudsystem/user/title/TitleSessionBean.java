package br.com.mcampos.ejb.cloudsystem.user.title;


import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "TitleSession", mappedName = "CloudSystems-EjbPrj-TitleSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class TitleSessionBean extends Crud<TitlePK, Title> implements TitleSessionLocal
{
	public TitleSessionBean()
	{
	}

	public void delete( TitlePK key ) throws ApplicationException
	{
		delete( Title.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Title get( TitlePK key ) throws ApplicationException
	{
		return get( Title.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<Title> getAll() throws ApplicationException
	{
		return ( List<Title> )getResultList( Title.getAll );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Integer getNextId() throws ApplicationException
	{
		return nextIntegerId( Title.getNextId );
	}
}
