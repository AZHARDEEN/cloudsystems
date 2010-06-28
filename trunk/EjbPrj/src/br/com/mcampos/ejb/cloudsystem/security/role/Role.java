package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = Role.roleGetAll, query = "select o from Role o" ),
                 @NamedQuery( name = Role.roleGetRoot, query = "select o from Role o where o.id = 1" ),
                 @NamedQuery( name = Role.roleGetChilds, query = "select o from Role o where o.parentRole = ?1" ),
                 @NamedQuery( name = Role.roleDefaults, query = "select o from Role o where o.isDefault = true" ) } )
@NamedNativeQueries( { @NamedNativeQuery( name = Role.roleMaxId,
                                          query = "select coalesce ( max (  rol_id_in ), 0 ) + 1 from role" ) } )
@Table( name = "role" )
public class Role implements Serializable, EntityCopyInterface<RoleDTO>, Comparable<Role>
{
    public static final Integer systemAdmimRoleLevel = 1;

    public static final String roleGetAll = "Role.findAll";
    public static final String roleGetRoot = "Role.getRoot";
    public static final String roleGetChilds = "Role.getChilds";
    public static final String roleMaxId = "Role.maxId";
    public static final String roleDefaults = "Role.getDefaultRoles";

    @Column( name = "rol_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "rol_id_in", nullable = false )
    private Integer id;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "rol_parent_id" )
    private Role parentRole;

    @OneToMany( mappedBy = "parentRole", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH )
    private List<Role> childRoles;

    @Column( name = "rol_default_bt", nullable = true )
    private Boolean isDefault;

    @OneToMany( mappedBy = "role", fetch = FetchType.EAGER )
    private List<PermissionAssignment> permissionAssignmentList;


    public Role()
    {
    }

    public Role( String rol_description_ch, Integer rol_id_in )
    {
        this.description = rol_description_ch;
        this.id = rol_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String rol_description_ch )
    {
        this.description = rol_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer rol_id_in )
    {
        this.id = rol_id_in;
    }


    public Role getParentRole()
    {
        return parentRole;
    }

    public void setParentRole( Role role )
    {
        this.parentRole = role;
    }

    public List<Role> getChildRoles()
    {
        if ( childRoles == null )
            childRoles = new ArrayList<Role>();
        return childRoles;
    }

    public void setChildRoles( List<Role> roleList )
    {
        this.childRoles = roleList;
    }

    public Role addRole( Role role )
    {
        getChildRoles().add( role );
        role.setParentRole( this );
        return role;
    }

    public Role removeRole( Role role )
    {
        getChildRoles().remove( role );
        role.setParentRole( null );
        return role;
    }

    public List<PermissionAssignment> getPermissionAssignmentList()
    {
        if ( permissionAssignmentList == null )
            permissionAssignmentList = new ArrayList<PermissionAssignment>();
        return permissionAssignmentList;
    }

    public void setPermissionAssignmentList( List<PermissionAssignment> permissionAssignmentList )
    {
        this.permissionAssignmentList = permissionAssignmentList;
    }

    public PermissionAssignment addPermissionAssignment( PermissionAssignment permissionAssignment )
    {
        getPermissionAssignmentList().add( permissionAssignment );
        permissionAssignment.setRole( this );
        return permissionAssignment;
    }

    public PermissionAssignment removePermissionAssignment( PermissionAssignment permissionAssignment )
    {
        getPermissionAssignmentList().remove( permissionAssignment );
        permissionAssignment.setRole( null );
        return permissionAssignment;
    }

    public RoleDTO toDTO()
    {
        return RoleUtils.copy( this, true, true );
    }

    public int compareTo( Role o )
    {
        return getId().compareTo( o.getId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof Role )
            return getId().equals( ( ( Role )obj ).getId() );
        else if ( obj instanceof Integer )
            return getId().equals( ( ( Integer )obj ) );
        else
            return false;
    }

    public void setAsDefault( Boolean isDefault )
    {
        this.isDefault = isDefault;
    }

    public Boolean isDefault()
    {
        return isDefault;
    }
}
