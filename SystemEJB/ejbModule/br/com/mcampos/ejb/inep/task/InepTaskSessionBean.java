package br.com.mcampos.ejb.inep.task;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

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
	public List<InepPackage> getEvents( Collaborator auth )
	{
		return getEventSession( ).getAll( auth );
	}

	@Override
	public List<InepTask> getAll( InepPackage event )
	{
		List<InepTask> tasks = Collections.emptyList( );
		InepPackage merged = getEventSession( ).get( event.getId( ) );
		if ( merged != null ) {
			tasks = findByNamedQuery( InepTask.getAllEventTasks, merged );
		}
		return tasks;
	}

	private InepPackageSessionLocal getEventSession( )
	{
		return this.eventSession;
	}

}
