package br.com.mcampos.ejb.inep.packs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

/**
 * Session Bean implementation class InepPackageSessionBean
 */
@Stateless( name = "InepPackageSession", mappedName = "InepPackageSession" )
@LocalBean
public class InepPackageSessionBean extends CollaboratorBaseSessionBean<InepPackage> implements InepPackageSession,
		InepPackageSessionLocal
{
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
	public List<InepPackage> getAll( Collaborator auth )
	{
		return getAll( auth, null );
	}

	@Override
	public List<InepPackage> getAll( Collaborator c, DBPaging page )
	{
		if ( c == null ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( InepPackage.getAll, page, c.getCompany( ) );
	}

	@Override
	public Integer getNextId( Collaborator c )
	{
		if ( c == null ) {
			return 0;
		}
		Query query = getEntityManager( ).createQuery(
				"select max( o.id.id ) + 1 from InepPackage o where o.company = ?1" );
		query.setParameter( 1, c.getCompany( ) );
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

}
