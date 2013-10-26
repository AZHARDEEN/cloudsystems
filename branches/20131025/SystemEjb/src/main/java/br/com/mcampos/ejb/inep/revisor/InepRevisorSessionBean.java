package br.com.mcampos.ejb.inep.revisor;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.revisortype.RevisorTypeSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepRevisorPK;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.user.Collaborator;

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

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@Override
	protected Class<InepRevisor> getEntityClass( )
	{
		return InepRevisor.class;
	}

	@Override
	public List<InepEvent> getEvents( PrincipalDTO auth )
	{
		return getTaskSession( ).getEvents( auth );
	}

	@Override
	public List<InepTask> getTasks( InepEvent event )
	{
		return getTaskSession( ).getAll( event );
	}

	private InepTaskSessionLocal getTaskSession( )
	{
		return taskSession;
	}

	@Override
	public List<InepRevisor> getAll( InepEvent p )
	{
		List<InepRevisor> list = Collections.emptyList( );
		InepEvent pm = getEntityManager( ).find( InepEvent.class, p.getId( ) );
		if ( pm == null ) {
			return list;
		}
		list = findByNamedQuery( InepRevisor.getAllTeamByEvent, p.getId( ).getCompanyId( ), p.getId( ).getId( ) );
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
	public InepRevisor get( InepEvent event, PrincipalDTO c )
	{
		if ( c == null ) {
			return null;
		}
		Collaborator collaborator = collaboratorSession.find( c );
		return get( event, collaborator );
	}

	@Override
	public InepRevisor get( InepEvent event, Collaborator collaborator )
	{
		if ( collaborator != null ) {
			return get( new InepRevisorPK( collaborator, event ) );
		}
		else {
			return null;
		}
	}

	@Override
	public InepRevisor merge( InepRevisor newEntity )
	{
		if ( newEntity.getType( ) == null ) {
			newEntity.setType( typeSession.get( 1 ) );
		}
		if ( newEntity.isCoordenador( ) && newEntity.getType( ).getId( ) < 3 ) {
			newEntity.setType( typeSession.get( newEntity.getType( ).getId( ) + 2 ) );
		}
		if ( newEntity.getType( ).getId( ) > 2 && newEntity.isCoordenador( ).equals( false ) ) {
			newEntity.setCoordenador( true );
		}
		return super.merge( newEntity );
	}

	@Override
	public List<InepRevisor> getOralCoordinator( InepEvent event )
	{
		return findByNamedQuery( InepRevisor.getOralCoordinatorByEvent, event );
	}

}
