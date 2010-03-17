package br.com.mcampos.controller.admin.system.config.task;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.ejb.cloudsystem.security.facade.SecurityFacade;
import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zul.AbstractTreeModel;

public class TaskTreeModel extends AbstractTreeModel
{
    private SecurityFacade session;
    private AuthenticationDTO currentUser;

    public TaskTreeModel( SecurityFacade session, AuthenticationDTO currentUser, TaskRootNode object )
    {
        super( object );
        setSession( session );
        setCurrentUser( currentUser );
    }

    public boolean isLeaf( Object node )
    {
        return getChildCount( node ) == 0;
    }

    protected boolean isRoot( Object object )
    {
        return ( object instanceof TaskRootNode );
    }

    protected boolean isTask( Object object )
    {
        return ( object instanceof TaskDTO );
    }

    public Object getChild( Object parent, int index )
    {
        if ( isRoot( parent ) )
            return ( ( TaskRootNode )parent ).getTasks().get( index );
        else if ( isTask( parent ) )
            return ( ( TaskDTO )parent ).getSubtasks().get( index );
        else
            return null;
    }

    public int getChildCount( Object parent )
    {
        if ( parent == null )
            return 0;

        if ( isRoot( parent ) )
            return ( ( TaskRootNode )parent ).getTasks().size();
        else if ( isTask( parent ) )
            return ( ( TaskDTO )parent ).getSubtasks().size();
        else
            return 0;
    }

    protected int getChildCont( TaskDTO dto )
    {
        if ( dto == null )
            return 0;
        if ( SysUtils.isEmpty( dto.getSubtasks() ) )
            getSession();
        return 0;
    }

    public void setSession( SecurityFacade session )
    {
        this.session = session;
    }

    public SecurityFacade getSession()
    {
        return session;
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
