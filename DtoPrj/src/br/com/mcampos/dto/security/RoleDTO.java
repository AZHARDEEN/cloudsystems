package br.com.mcampos.dto.security;


import br.com.mcampos.dto.core.SimpleTableDTO;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO extends SimpleTableDTO
{
    protected List<RoleDTO> childRoles;
    protected RoleDTO parent;

    public RoleDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public RoleDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public RoleDTO( Integer integer )
    {
        super( integer );
    }

    public RoleDTO()
    {
        super();
    }

    public void setChildRoles( List<RoleDTO> childRoles )
    {
        this.childRoles = childRoles;
    }

    public List<RoleDTO> getChildRoles()
    {
        return childRoles;
    }

    public void add ( RoleDTO child )
    {
        if ( child != null ) {
            if ( getChildRoles() == null )
                setChildRoles( new ArrayList<RoleDTO> () );
            getChildRoles().add( child );
            child.setParent( this );
        }
    }

    public void setParent( RoleDTO parent )
    {
        this.parent = parent;
    }

    public RoleDTO getParent()
    {
        return parent;
    }
}
