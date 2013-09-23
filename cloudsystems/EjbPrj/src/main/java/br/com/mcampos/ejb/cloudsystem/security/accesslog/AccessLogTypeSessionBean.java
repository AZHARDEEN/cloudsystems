package br.com.mcampos.ejb.cloudsystem.security.accesslog;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AccessLogTypeSession", mappedName = "AccessLogTypeSession" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AccessLogTypeSessionBean extends Crud<Integer, AccessLogType> implements AccessLogTypeSessionLocal
{
	public AccessLogTypeSessionBean()
	{
	}

	public void delete( Integer key ) throws ApplicationException
	{
		delete( AccessLogType.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public AccessLogType get( Integer key ) throws ApplicationException
	{
		return get( AccessLogType.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AccessLogType> getAll() throws ApplicationException
	{
		return ( List<AccessLogType> )getResultList( AccessLogType.getAll );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Integer getNextId() throws ApplicationException
	{
		return nextIntegerId( AccessLogType.getNextId );
	}
}
