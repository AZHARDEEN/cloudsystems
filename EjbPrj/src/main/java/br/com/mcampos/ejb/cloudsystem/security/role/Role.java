package br.com.mcampos.ejb.cloudsystem.security.role;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;

@Entity
@NamedQueries( { @NamedQuery( name = Role.roleGetAll, query = "select o from Role o" ),
		@NamedQuery( name = Role.roleGetRoot, query = "select o from Role o where o.id = 1" ),
		@NamedQuery( name = Role.roleGetChilds, query = "select o from Role o where o.parentRole = ?1" ),
		@NamedQuery( name = Role.roleDefaults, query = "select o from Role o where o.isDefault = true" ),
		@NamedQuery( name = Role.roleMaxId, query = "select coalesce ( max ( o.id ), 0 ) + 1 from Role o" )
} )
@Table( name = "role" )
public class Role implements Serializable, EntityCopyInterface<RoleDTO>, Comparable<Role>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1246013879651654082L;

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

	/*
	@OneToMany( mappedBy = "parentRole", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<Role> childRoles;
	*/

	@Column( name = "rol_default_bt", nullable = true )
	private Boolean isDefault;

	/*
	@OneToMany( mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<PermissionAssignment> permissionAssignmentList;
	*/

	public Role( )
	{
	}

	public Role( String rol_description_ch, Integer rol_id_in )
	{
		description = rol_description_ch;
		id = rol_id_in;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String rol_description_ch )
	{
		description = rol_description_ch;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer rol_id_in )
	{
		id = rol_id_in;
	}

	public Role getParentRole( )
	{
		return parentRole;
	}

	public void setParentRole( Role role )
	{
		parentRole = role;
	}

	public List<Role> getChildRoles( )
	{ /*
		if ( childRoles == null )
			childRoles = new ArrayList<Role>( );
		return childRoles;
		*/
		return null;
	}

	public void setChildRoles( List<Role> roleList )
	{
		// childRoles = roleList;
	}

	public Role addRole( Role role )
	{
		getChildRoles( ).add( role );
		role.setParentRole( this );
		return role;
	}

	public Role removeRole( Role role )
	{
		getChildRoles( ).remove( role );
		role.setParentRole( null );
		return role;
	}

	public List<PermissionAssignment> getPermissionAssignmentList( )
	{
		/*
		if ( permissionAssignmentList == null )
			permissionAssignmentList = new ArrayList<PermissionAssignment>( );
		return permissionAssignmentList;
		*/
		return null;
	}

	public void setPermissionAssignmentList( List<PermissionAssignment> permissionAssignmentList )
	{
		// this.permissionAssignmentList = permissionAssignmentList;
	}

	public PermissionAssignment addPermissionAssignment( PermissionAssignment permissionAssignment )
	{
		getPermissionAssignmentList( ).add( permissionAssignment );
		permissionAssignment.setRole( this );
		return permissionAssignment;
	}

	public PermissionAssignment removePermissionAssignment( PermissionAssignment permissionAssignment )
	{
		getPermissionAssignmentList( ).remove( permissionAssignment );
		permissionAssignment.setRole( null );
		return permissionAssignment;
	}

	@Override
	public RoleDTO toDTO( )
	{
		return RoleUtils.copy( this, true, true );
	}

	@Override
	public int compareTo( Role o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof Role )
			return getId( ).equals( ( (Role) obj ).getId( ) );
		else if ( obj instanceof Integer )
			return getId( ).equals( ( obj ) );
		else
			return false;
	}

	public void setAsDefault( Boolean isDefault )
	{
		this.isDefault = isDefault;
	}

	public Boolean isDefault( )
	{
		return isDefault;
	}
}
