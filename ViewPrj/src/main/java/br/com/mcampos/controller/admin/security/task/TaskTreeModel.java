package br.com.mcampos.controller.admin.security.task;

import java.util.List;

import org.zkoss.zul.AbstractTreeModel;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskFacade;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;

public class TaskTreeModel extends AbstractTreeModel
{
	private TaskFacade session;
	private AuthenticationDTO currentUser;

	public TaskTreeModel( AuthenticationDTO currentUser, Object root ) throws ApplicationException
	{
		super( root );
		setCurrentUser( currentUser );
	}

	@Override
	public boolean isLeaf( Object node )
	{
		return getChildCount( node ) == 0;
	}

	@Override
	public Object getChild( Object parent, int index )
	{
		if ( parent instanceof List )
			return ( (List) parent ).get( index );
		else if ( parent instanceof TaskDTO )
			return ( (TaskDTO) parent ).getSubtasks( ).get( index );
		return null;
	}

	@Override
	public int getChildCount( Object parent )
	{
		if ( parent == null )
			return 0;
		if ( parent instanceof List )
			return ( (List) parent ).size( );
		else if ( parent instanceof TaskDTO ) {
			List<TaskDTO> subtasks;
			try {
				TaskDTO dto = (TaskDTO) parent;
				if ( dto.getSubtasks( ) == null )
					dto.setSubtasks( getSession( ).getSubTasks( getCurrentUser( ), dto ) );
				return ( SysUtils.isEmpty( dto.getSubtasks( ) ) ) ? 0 : dto.getSubtasks( ).size( );
			}
			catch ( ApplicationException e ) {
				e = null;
				return 0;
			}
		}
		return 0;
	}

	public TaskFacade getSession( )
	{
		if ( session == null )
			session = (TaskFacade) getRemoteSession( TaskFacade.class );
		return session;
	}

	protected Object getRemoteSession( Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( remoteClass, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

	public void setCurrentUser( AuthenticationDTO currentUser )
	{
		this.currentUser = currentUser;
	}

	public AuthenticationDTO getCurrentUser( )
	{
		return currentUser;
	}
}
