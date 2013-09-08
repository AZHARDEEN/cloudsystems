package br.com.mcampos.ejb.inep.packs;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.entity.user.Company;
import br.com.mcampos.utils.dto.PrincipalDTO;

/**
 * Session Bean implementation class InepPackageSessionBean
 */
@Stateless( name = "InepPackageSession", mappedName = "InepPackageSession" )
@LocalBean
public class InepPackageSessionBean extends CollaboratorBaseSessionBean<InepEvent> implements InepPackageSession,
		InepPackageSessionLocal
{
	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	private CompanySessionLocal companySession;

	@Override
	protected Class<InepEvent> getEntityClass( )
	{
		return InepEvent.class;
	}

	@Override
	public List<InepEvent> getAll( PrincipalDTO auth )
	{
		return getAll( auth, null );
	}

	@Override
	public List<InepEvent> getAll( PrincipalDTO c, DBPaging page )
	{
		if ( c == null ) {
			return Collections.emptyList( );
		}
		Company company = companySession.get( c.getCompanyID( ) );
		return findByNamedQuery( InepEvent.getAll, page, company );
	}

	@Override
	public Integer getNextId( PrincipalDTO c )
	{
		if ( c == null ) {
			return 0;
		}
		Query query = getEntityManager( ).createQuery(
				"select max( o.id.id ) + 1 from InepEvent o where o.company = ?1" );
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
	public List<InepEvent> getAvailable( )
	{
		return findByNamedQuery( InepEvent.getAllAvailable );
	}

	private void createTasks( PrincipalDTO auth, InepEvent newEntity )
	{
		for ( int i = 1; i <= 4; i++ ) {
			InepTask task = new InepTask( newEntity );
			task.getId( ).setId( i );
			task.setDescription( "Tarefa " + i );
			taskSession.add( auth, task );
		}
	}

	@Override
	public InepEvent add( PrincipalDTO auth, InepEvent newEntity )
	{
		newEntity = super.add( auth, newEntity );
		createTasks( auth, newEntity );
		return newEntity;
	}

	@Override
	public InepEvent remove( PrincipalDTO auth, Serializable key )
	{
		InepEvent event = get( key );
		if ( event != null ) {
			taskSession.remove( event );
			return super.remove( auth, key );
		}
		return null;
	}

}
