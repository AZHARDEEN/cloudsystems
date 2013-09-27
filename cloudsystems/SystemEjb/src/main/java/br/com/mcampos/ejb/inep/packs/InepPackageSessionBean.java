package br.com.mcampos.ejb.inep.packs;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.user.Company;

/**
 * Session Bean implementation class InepPackageSessionBean
 */
@Stateless( name = "InepPackageSession", mappedName = "InepPackageSession" )
@LocalBean
public class InepPackageSessionBean extends CollaboratorBaseSessionBean<InepEvent> implements InepPackageSession,
		InepPackageSessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4327503286585562526L;

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
		return this.getAll( auth, null );
	}

	@Override
	public List<InepEvent> getAll( PrincipalDTO c, DBPaging page )
	{
		if ( c == null ) {
			return Collections.emptyList( );
		}
		Company company = this.companySession.get( c.getCompanyID( ) );
		return this.findByNamedQuery( InepEvent.getAll, page, company );
	}

	@Override
	public Integer getNextId( PrincipalDTO c )
	{
		if ( c == null ) {
			return 0;
		}
		Query query = this.getEntityManager( ).createQuery(
				"select max( o.id.id ) + 1 from InepEvent o where o.company = ?1" );
		query.setParameter( 1, this.companySession.get( c.getCompanyID( ) ) );
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
	public List<InepEvent> getAvailable( PrincipalDTO c )
	{
		Company company = this.companySession.get( c.getCompanyID( ) );
		if ( company == null ) {
			return Collections.emptyList( );
		}

		return this.findByNamedQuery( InepEvent.getAvailable, company );
	}

	@Override
	public List<InepEvent> getAvailable( )
	{
		return this.findByNamedQuery( InepEvent.getAllAvailable );
	}

	private void createTasks( PrincipalDTO auth, InepEvent newEntity )
	{
		for ( int i = 1; i <= 4; i++ ) {
			InepTask task = new InepTask( newEntity );
			task.getId( ).setId( i );
			task.setDescription( "Tarefa " + i );
			this.taskSession.add( auth, task );
		}
	}

	@Override
	public InepEvent add( PrincipalDTO auth, InepEvent newEntity )
	{
		newEntity = super.add( auth, newEntity );
		this.createTasks( auth, newEntity );
		return newEntity;
	}

	@Override
	public InepEvent remove( PrincipalDTO auth, Serializable key )
	{
		InepEvent event = this.get( key );
		if ( event != null ) {
			this.taskSession.remove( event );
			return super.remove( auth, key );
		}
		return null;
	}

}
