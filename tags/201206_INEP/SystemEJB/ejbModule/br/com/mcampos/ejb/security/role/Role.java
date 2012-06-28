package br.com.mcampos.ejb.security.role;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mcampos.ejb.security.core.SelfRelationInterface;
import br.com.mcampos.ejb.security.core.TaskRelationInterface;
import br.com.mcampos.ejb.security.task.Task;

/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table( name = "role" )
public class Role implements Serializable, TaskRelationInterface, SelfRelationInterface<Role>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "rol_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "rol_default_bt" )
	private Boolean rolDefaultBt;

	@Column( name = "rol_description_ch", nullable = false, length = 64 )
	private String description;

	// bi-directional many-to-one association to Role
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "rol_parent_id" )
	private Role parent;

	// bi-directional many-to-one association to Role
	@OneToMany( mappedBy = "parent", fetch = FetchType.EAGER )
	private List<Role> childs;

	@ManyToMany
	@JoinTable( name = "permission_assignment", joinColumns = { @JoinColumn( name = "rol_id_in", nullable = false ) },
			inverseJoinColumns = { @JoinColumn( name = "tsk_id_in", nullable = false ) } )
	private List<Task> tasks;

	public Role( )
	{
	}

	public Role( Integer id, String description )
	{
		setId( id );
		setDescription( description );
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer rolIdIn )
	{
		this.id = rolIdIn;
	}

	public Boolean getRolDefaultBt( )
	{
		return this.rolDefaultBt;
	}

	public void setRolDefaultBt( Boolean rolDefaultBt )
	{
		this.rolDefaultBt = rolDefaultBt;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String rolDescriptionCh )
	{
		this.description = rolDescriptionCh;
	}

	public Role getParent( )
	{
		return this.parent;
	}

	/*
	 * A entidade corrente está recebendo a role como parent
	 */
	@Override
	public void setParent( Role parent )
	{
		if ( parent != null ) {
			if ( parent.equals( getParent( ) ) == false ) {
				if ( parent.getParent( ) != null ) {
					parent.getParent( ).remove( this );
				}
				this.parent = parent;
				getParent( ).add( this );
			}
		}
		else {
			this.parent = null;
		}
	}

	@Override
	public void add( Role child )
	{
		if ( child != null && getChilds( ).contains( child ) == false ) {
			getChilds( ).add( child );
			child.setParent( this );
		}
	}

	@Override
	public Role remove( Role child )
	{
		if ( child != null )
		{
			if ( getChilds( ).contains( child ) ) {
				getChilds( ).remove( child );
				child.setParent( null );
			}
		}
		return child;
	}

	public List<Role> getChilds( )
	{
		if ( this.childs == null ) {
			this.childs = new ArrayList<Role>( );
		}
		return this.childs;
	}

	public void setChilds( List<Role> roles )
	{
		this.childs = roles;
	}

	@Override
	public List<Task> getTasks( )
	{
		if ( this.tasks == null ) {
			this.tasks = new ArrayList<Task>( );
		}
		return this.tasks;
	}

	@Override
	public void setTasks( List<Task> tasks )
	{
		this.tasks = tasks;
	}

	@Override
	public Task add( Task task )
	{
		if ( task != null && getTasks( ).contains( task ) == false ) {
			getTasks( ).add( task );
			task.add( this );
		}
		return task;
	}

	@Override
	public Task remove( Task task )
	{
		if ( task != null ) {
			int nIndex = getTasks( ).indexOf( task );
			if ( nIndex >= 0 ) {
				getTasks( ).remove( nIndex );
				task.remove( this );
			}
		}
		return task;
	}
}