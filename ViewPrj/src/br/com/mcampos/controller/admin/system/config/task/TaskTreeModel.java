package br.com.mcampos.controller.admin.system.config.task;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.ejb.cloudsystem.security.facade.SecurityFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import java.util.List;

import org.zkoss.zul.AbstractTreeModel;


public class TaskTreeModel extends AbstractTreeModel
{
    private SecurityFacade session;
    private AuthenticationDTO currentUser;

    public TaskTreeModel( AuthenticationDTO currentUser, Object root ) throws ApplicationException
    {
        super( root );
        setCurrentUser( currentUser );
    }

    public boolean isLeaf( Object node )
    {
        return getChildCount( node ) == 0;
    }

    public Object getChild( Object parent, int index )
    {
        if ( parent instanceof List ) {
            return ( ( List )parent ).get( index );
        }
        else if ( parent instanceof TaskDTO ) {
            return ( ( TaskDTO )parent ).getSubtasks().get( index );
        }
        return null;
    }

    public int getChildCount( Object parent )
    {
        if ( parent == null )
            return 0;
        if ( parent instanceof List ) {
            return ( ( List )parent ).size();
        }
        else if ( parent instanceof TaskDTO ) {
            List<TaskDTO> subtasks;
            try {
                TaskDTO dto = ( TaskDTO )parent;
                if ( dto.getSubtasks() == null )
                    dto.setSubtasks( getSession().getSubtasks( getCurrentUser(), dto ) );
                return ( SysUtils.isEmpty( dto.getSubtasks() ) ) ? 0 : dto.getSubtasks().size();
            }
            catch ( ApplicationException e ) {
                e = null;
                return 0;
            }
        }
        return 0;
    }

    public SecurityFacade getSession()
    {
        if ( session == null )
            session = ( SecurityFacade )getRemoteSession( SecurityFacade.class );
        return session;
    }

    protected Object getRemoteSession( Class remoteClass )
    {
        try {
            return ServiceLocator.getInstance().getRemoteSession( remoteClass );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }


    public void setCurrentUser( AuthenticationDTO currentUser )
    {
        this.currentUser = currentUser;
    }

    public AuthenticationDTO getCurrentUser()
    {
        return currentUser;
    }
}
