package br.com.mcampos.ejb.cloudsystem.security.entity;


import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;
import br.com.mcampos.ejb.entity.security.PermissionAssignment;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

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
                 @NamedQuery( name = Role.roleGetChilds, query = "select o from Role o where o.parentRole = ?1" ) } )
@NamedNativeQueries( { @NamedNativeQuery( name = Role.roleMaxId,
                                          query = "select coalesce ( max (  rol_id_in ), 0 ) + 1 from role" ) } )
@Table( name = "role" )
public class Role implements Serializable, EntityCopyInterface<RoleDTO>
{
    public static final Integer systemAdmimRoleLevel = 1;

    public static final String roleGetAll = "Role.findAll";
    public static final String roleGetRoot = "Role.getRoot";
    public static final String roleGetChilds = "Role.getChilds";
    public static final String roleMaxId = "Role.maxId";

    @Column( name = "rol_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "rol_id_in", nullable = false )
    private Integer id;
    @ManyToOne
    @JoinColumn( name = "rol_parent_id" )
    private Role parentRole;
    @OneToMany( mappedBy = "parentRole", fetch = FetchType.LAZY )
    private List<Role> childRoles;

    @OneToMany( mappedBy = "role", fetch = FetchType.LAZY )
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
        RoleDTO dto = new RoleDTO( getId(), getDescription() );
        if ( getParentRole() != null )
            dto.setParent( getParentRole().toDTO() );
        return dto;
    }
}
