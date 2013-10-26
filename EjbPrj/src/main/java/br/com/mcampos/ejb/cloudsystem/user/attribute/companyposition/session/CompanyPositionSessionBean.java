package br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.entity.CompanyPosition;

@Stateless( name = "CompanyPositionSession", mappedName = "CompanyPositionSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CompanyPositionSessionBean extends Crud<Integer, CompanyPosition> implements CompanyPositionSessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 556478755942989447L;

	public CompanyPositionSessionBean( )
	{
	}

	@Override
	public void delete( Integer key ) throws ApplicationException
	{
		delete( CompanyPosition.class, key );
	}

	@Override
	public CompanyPosition get( Integer key ) throws ApplicationException
	{
		return get( CompanyPosition.class, key );
	}

	@Override
	public List<CompanyPosition> getAll( ) throws ApplicationException
	{
		return getAll( CompanyPosition.getAll );
	}

	@Override
	public Integer nextIntegerId( ) throws ApplicationException
	{
		return nextIntegerId( CompanyPosition.nextId );
	}
}
