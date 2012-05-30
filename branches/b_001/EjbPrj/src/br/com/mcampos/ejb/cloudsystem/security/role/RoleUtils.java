package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RoleUtils
{
    public RoleUtils()
    {
        super();
    }

    public static List<RoleDTO> toRoleDTOList( List<Role> roles )
    {
        if ( SysUtils.isEmpty( roles ) )
            return Collections.emptyList();
        List<RoleDTO> dtos = new ArrayList<RoleDTO>( roles.size() );
        for ( Role role : roles )
            dtos.add( role.toDTO() );
        return dtos;
    }

    public static Role createEntity( RoleDTO source )
    {
        Role role = new Role();
        role.setId( source.getId() );
        return update( role, source );
    }

    public static Role update( Role role, RoleDTO source )
    {
        role.setDescription( source.getDescription() );
        role.setAsDefault( source.isDefault() );
        if ( source.getParent() != null )
            role.setParentRole( createEntity( source.getParent() ) );
        return role;
    }

    public static List<Role> getRoles( List<Role> roles )
    {
        List<Role> allRoles = new ArrayList<Role>();
        for ( Role role : roles ) {
            getRoles( role, allRoles );
        }
        return allRoles;
    }

    public static List<Role> getRoles( Role role, List<Role> allRoles )
    {
        if ( role == null )
            return allRoles;
        if ( SysUtils.isEmpty( role.getChildRoles() ) == false ) {
            for ( Role subRole : role.getChildRoles() )
                getRoles( subRole, allRoles );
        }
        if ( allRoles.contains( role ) == false )
            allRoles.add( role );
        return allRoles;
    }

    public static RoleDTO copy( Role role, boolean bParent, boolean bChild )
    {
        RoleDTO dto = new RoleDTO( role.getId(), role.getDescription() );
        dto.setDefault( role.isDefault() );
        if ( bParent && role.getParentRole() != null ) {
            dto.setParent( RoleUtils.copy( role.getParentRole(), false, false ) );
        }
        if ( bChild && role.getChildRoles() != null ) {
            for ( Role r : role.getChildRoles() ) {
                dto.add( copy( r, false, true ) );
            }
        }
        return dto;
    }
}
