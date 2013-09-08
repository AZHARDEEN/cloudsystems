package br.com.mcampos.ejb.inep.task;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.utils.dto.PrincipalDTO;

/**
 * Session Bean implementation class InepTaskSessionBean
 */
@Stateless( name = "InepTaskSession", mappedName = "InepTaskSession" )
@LocalBean
public class InepTaskSessionBean extends SimpleSessionBean<InepTask> implements InepTaskSession, InepTaskSessionLocal
{
	@EJB
	InepPackageSessionLocal eventSession;

	@Override
	protected Class<InepTask> getEntityClass( )
	{
		return InepTask.class;
	}

	@Override
	public List<InepEvent> getEvents( PrincipalDTO auth )
	{
		return getEventSession( ).getAll( auth );
	}

	@Override
	public List<InepTask> getAll( InepEvent event )
	{
		List<InepTask> tasks = Collections.emptyList( );
		InepEvent merged = getEventSession( ).get( event.getId( ) );
		if ( merged != null ) {
			tasks = findByNamedQuery( InepTask.getAllEventTasks, merged );
		}
		return tasks;
	}

	private InepPackageSessionLocal getEventSession( )
	{
		return eventSession;
	}

	@Override
	public void remove( InepEvent event )
	{
		List<InepTask> tasks = getAll( event );

		for ( InepTask task : tasks ) {
			getEntityManager( ).remove( task );
		}
	}

}
