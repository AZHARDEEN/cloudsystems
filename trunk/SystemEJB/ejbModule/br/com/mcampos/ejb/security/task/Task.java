package br.com.mcampos.ejb.security.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mcampos.ejb.security.menu.Menu;
import br.com.mcampos.ejb.security.role.Role;

/**
 * The persistent class for the task database table.
 * 
 */
@Entity
@Table( name = "task" )
@NamedQueries( { @NamedQuery( name = Task.getTop, query = "select o from Task o where o.parent is null" ) } )
public class Task implements Serializable, Comparable<Task>
{
	private static final long serialVersionUID = 1L;
	public static final String getTop = "Task.getTopTasks";

	@Id
	@Column( name = "tsk_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "tsk_description_ch", nullable = false, length = 64 )
	private String description;

	// bi-directional many-to-one association to Role
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "tsk_parent_id" )
	private Task parent;

	// bi-directional many-to-one association to Role
	@OneToMany( mappedBy = "parent", fetch = FetchType.EAGER )
	private List<Task> childs;

	@ManyToMany
	@JoinTable( name = "task_menu", joinColumns = { @JoinColumn( name = "tsk_id_in", nullable = false ) },
			inverseJoinColumns = { @JoinColumn( name = "mnu_id_in", nullable = false ) } )
	private List<Menu> menus;

	@ManyToMany( mappedBy = "tasks" )
	List<Role> roles;

	public Task( )
	{
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer tskIdIn )
	{
		this.id = tskIdIn;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String tskDescriptionCh )
	{
		this.description = tskDescriptionCh;
	}

	public List<Menu> getMenus( )
	{
		if ( this.menus == null ) {
			this.menus = new ArrayList<Menu>( );
		}
		return this.menus;
	}

	public void setMenus( List<Menu> menus )
	{
		this.menus = menus;
	}

	public Task getParent( )
	{
		return this.parent;
	}

	public void setParent( Task parent )
	{
		this.parent = parent;
	}

	public List<Task> getChilds( )
	{
		if ( this.childs == null ) {
			this.childs = new ArrayList<Task>( );
		}
		return this.childs;
	}

	public void setChilds( List<Task> childs )
	{
		this.childs = childs;
	}

	public Role remove( Role role )
	{
		if ( role != null ) {
			int nIndex = getRoles( ).indexOf( role );
			if ( nIndex >= 0 ) {
				getRoles( ).remove( nIndex );
				role.remove( this );
			}
		}
		return role;
	}

	public Role add( Role role )
	{
		if ( role != null && getRoles( ).contains( role ) == false ) {
			getRoles( ).add( role );
		}
		return role;
	}

	public List<Role> getRoles( )
	{
		if ( this.roles == null ) {
			this.roles = new ArrayList<Role>( );
		}
		return this.roles;
	}

	public void setRoles( List<Role> roles )
	{
		this.roles = roles;
	}

	public Menu remove( Menu menu )
	{
		if ( menu != null ) {
			int nIndex = getMenus( ).indexOf( menu );
			if ( nIndex >= 0 )
			{
				getMenus( ).remove( nIndex );
				menu.remove( this );
			}
		}
		return menu;
	}

	public Menu add( Menu menu )
	{
		if ( menu != null && getMenus( ).contains( menu ) == false ) {
			getMenus( ).add( menu );
		}
		return menu;
	}

	@Override
	public int compareTo( Task o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof Task ) {
			return getId( ).equals( ( (Task) obj ).getId( ) );
		}
		else {
			return false;
		}
	}

	@Override
	public String toString( )
	{
		String str = getId( ).toString( ) + " - " + getDescription( );
		if ( getParent( ) != null ) {
			str += " Parent: " + getParent( ).toString( );
		}
		return str;
	}

	public Task remove( Task child )
	{
		if ( child != null )
		{
			int nIndex = getChilds( ).indexOf( child );
			if ( nIndex >= 0 ) {
				getChilds( ).remove( nIndex );
				child.setParent( null );
			}
		}
		return child;
	}

	public Task add( Task child )
	{
		if ( child != null && getChilds( ).contains( child ) == false ) {
			getChilds( ).add( child );
			child.setParent( this );
		}
		return child;
	}
}