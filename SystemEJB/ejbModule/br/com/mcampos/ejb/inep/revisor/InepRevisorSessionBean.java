package br.com.mcampos.ejb.inep.revisor;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepRevisorPK;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.revisortype.RevisorTypeSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

/**
 * Session Bean implementation class InepRevisorSessionBean
 */
@Stateless( name = "InepRevisorSession", mappedName = "InepRevisorSession" )
@LocalBean
public class InepRevisorSessionBean extends SimpleSessionBean<InepRevisor> implements InepRevisorSession, InepRevisorSessionLocal
{
	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	private RevisorTypeSessionLocal typeSession;

	@Override
	protected Class<InepRevisor> getEntityClass( )
	{
		return InepRevisor.class;
	}

	@Override
	public List<InepPackage> getEvents( Collaborator auth )
	{
		return getTaskSession( ).getEvents( auth );
	}

	@Override
	public List<InepTask> getTasks( InepPackage event )
	{
		return getTaskSession( ).getAll( event );
	}

	private InepTaskSessionLocal getTaskSession( )
	{
		return taskSession;
	}

	@Override
	public List<InepRevisor> getAll( InepPackage p )
	{
		List<InepRevisor> list = Collections.emptyList( );
		InepPackage pm = getEntityManager( ).find( InepPackage.class, p.getId( ) );
		if ( pm == null ) {
			return list;
		}
		list = findByNamedQuery( InepRevisor.getAllTeamByEvent, p );
		return list;
	}

	@Override
	public List<InepRevisor> getAll( InepTask t )
	{
		List<InepRevisor> list = Collections.emptyList( );
		InepTask tm = getTaskSession( ).get( t.getId( ) );
		if ( tm != null ) {
			list = findByNamedQuery( InepRevisor.getAllTeamByEventAndTask, tm );
		}
		return list;
	}

	@Override
	public InepRevisor get( InepPackage event, Collaborator c )
	{
		if ( c == null ) {
			return null;
		}
		return get( new InepRevisorPK( c, event ) );
	}

	@Override
	public InepRevisor merge( InepRevisor newEntity )
	{
		if ( newEntity.isCoordenador( ) && newEntity.getType( ).getId( ) < 3 )
			newEntity.setType( typeSession.get( newEntity.getType( ).getId( ) + 2 ) );
		if ( newEntity.getType( ).getId( ) > 2 && newEntity.isCoordenador( ).equals( false ) )
			newEntity.setCoordenador( true );
		return super.merge( newEntity );
	}

}
