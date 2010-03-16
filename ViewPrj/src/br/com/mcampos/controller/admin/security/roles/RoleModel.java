package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.security.facade.SecurityFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zul.AbstractTreeModel;

public class RoleModel extends AbstractTreeModel
{
    SecurityFacade session;
    AuthenticationDTO currentUser;

    public RoleModel( SecurityFacade session, AuthenticationDTO currentUser, Object object )
    {
        super( object );
        this.session = session;
        this.currentUser = currentUser;
    }


    protected boolean isRole ( Object node )
    {
        return ( node instanceof RoleDTO );
    }

    public boolean isLeaf( Object node )
    {
        return getChildCount( node ) == 0;
    }

    public Object getChild( Object parent, int index )
    {
        Object child = null;
        if ( isRole( parent ) )
            child = ((RoleDTO)parent).getChildRoles().get( index );
        return child;
    }

    public int getChildCount( Object parent )
    {
        if ( isRole( parent ) )
            return getChildCount( ( (RoleDTO) parent) );
        return 0;
    }

    protected int getChildCount ( RoleDTO role )
    {
        int childs = 0;

        if ( role == null )
            return childs;
        if ( role.getChildRoles() == null ) {
            try {
                role.setChildRoles( getSession().getChildRoles( getCurrentUser(), role ) );
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        childs = ( role.getChildRoles() != null ) ? role.getChildRoles().size() : 0;
        return childs;
    }

    protected SecurityFacade getSession()
    {
        return session;
    }

    protected AuthenticationDTO getCurrentUser()
    {
        return currentUser;
    }
}
