package br.com.mcampos.ejb.entity.security;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Role.findAll", query = "select o from Role o" ) } )
@Table( name = "\"role\"" )
public class Role implements Serializable
{
    public static final Integer systemAdmimRoleLevel = 1;


    private String description;
    private Integer id;
    private Role parentRole;
    private List<Role> childRoles;

    private List<PermissionAssignment> permissionAssignmentList;


    public Role()
    {
    }

    public Role( String rol_description_ch, Integer rol_id_in )
    {
        this.description = rol_description_ch;
        this.id = rol_id_in;
    }

    @Column( name = "rol_description_ch", nullable = false )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String rol_description_ch )
    {
        this.description = rol_description_ch;
    }

    @Id
    @Column( name = "rol_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer rol_id_in )
    {
        this.id = rol_id_in;
    }


    @ManyToOne
    @JoinColumn( name = "rol_parent_id" )
    public Role getParentRole()
    {
        return parentRole;
    }

    public void setParentRole( Role role )
    {
        this.parentRole = role;
    }

    @OneToMany( mappedBy = "parentRole" )
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

    @OneToMany( mappedBy = "role" )
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
}
