package br.com.mcampos.ejb.inep.packs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.utils.dto.PrincipalDTO;

/**
 * Session Bean implementation class InepPackageSessionBean
 */
@Stateless( name = "InepPackageSession", mappedName = "InepPackageSession" )
@LocalBean
public class InepPackageSessionBean extends CollaboratorBaseSessionBean<InepPackage> implements InepPackageSession,
		InepPackageSessionLocal
{
	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	private CompanySessionLocal companySession;

	@Override
	protected Class<InepPackage> getEntityClass( )
	{
		return InepPackage.class;
	}

	@Override
	public Collection<InepPackage> getAll( )
	{
		/*
		 * Deve esconder este metodo, pois o evento (InepPackage) depende da
		 * empresa.
		 */
		return Collections.emptyList( );
	}

	@Override
	public List<InepPackage> getAll( PrincipalDTO auth )
	{
		return getAll( auth, null );
	}

	@Override
	public List<InepPackage> getAll( PrincipalDTO c, DBPaging page )
	{
		if ( c == null ) {
			return Collections.emptyList( );
		}
		Company company = companySession.get( c.getCompanyID( ) );
		return findByNamedQuery( InepPackage.getAll, page, company );
	}

	@Override
	public Integer getNextId( PrincipalDTO c )
	{
		if ( c == null ) {
			return 0;
		}
		Query query = getEntityManager( ).createQuery(
				"select max( o.id.id ) + 1 from InepPackage o where o.company = ?1" );
		query.setParameter( 1, companySession.get( c.getCompanyID( ) ) );
		Integer id;
		try {
			id = (Integer) query.getSingleResult( );
			if ( id == null || id.equals( 0 ) ) {
				id = 1;
			}
		}
		catch ( Exception e ) {
			id = 1;
		}
		return id;
	}

	@Override
	public List<InepPackage> getAvailable( )
	{
		return findByNamedQuery( InepPackage.getAllAvailable );
	}

	@Override
	public InepPackage merge( InepPackage newEntity )
	{
		newEntity = super.merge( newEntity );
		for ( int i = 1; i <= 4; i++ ) {
			InepTask task = new InepTask( newEntity );
			task.getId( ).setId( i );
			task.setDescription( "Tarefa " + i );
			taskSession.merge( task );
		}
		return newEntity;
	}

}
