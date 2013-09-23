package br.com.mcampos.ejb.cloudsystem.security.task;

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

import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.task.subtask.Subtask;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenu;

@Entity
@NamedQueries( {
		@NamedQuery( name = Task.findAll, query = "select o from Task o" ),
		@NamedQuery( name = Task.rootTasks, query = "select o from Task o WHERE o.parent.id = 1" )
} )
@Table( name = "task" )
public class Task implements Serializable, EntityCopyInterface<TaskDTO>, Comparable<Task>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8227446754438634987L;
	public static final String findAll = "Task.findAll";
	public static final String rootTasks = "Task.rootTasks";

	@Column( name = "tsk_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "tsk_id_in", nullable = false )
	private Integer id;

	/*
	@OneToMany( mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<Subtask> subtasks;

	@OneToMany( mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<PermissionAssignment> permissionAssignmentList;

	@OneToMany( mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<TaskMenu> taskMenuList;

	@OneToMany( mappedBy = "subTask", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<Subtask> masterTaskList;
	*/

	// bi-directional many-to-one association to Role
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "tsk_parent_id" )
	private Task parent;

	public Task( )
	{
	}

	public Task( String tsk_description_ch, Integer tsk_id_in )
	{
		description = tsk_description_ch;
		id = tsk_id_in;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String tsk_description_ch )
	{
		description = tsk_description_ch;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer tsk_id_in )
	{
		id = tsk_id_in;
	}

	public List<Subtask> getSubtasks( )
	{
		return null;// subtasks;
	}

	public void setSubtasks( List<Subtask> subtaskList )
	{
		// subtasks = subtaskList;
	}

	public Subtask addSubtask( Subtask subtask )
	{
		getSubtasks( ).add( subtask );
		subtask.setSubTask( this );
		return subtask;
	}

	public Subtask removeSubtask( Subtask subtask )
	{
		getSubtasks( ).remove( subtask );
		subtask.setSubTask( null );
		return subtask;
	}

	public List<PermissionAssignment> getPermissionAssignmentList( )
	{
		return null;// permissionAssignmentList;
	}

	public void setPermissionAssignmentList( List<PermissionAssignment> permissionAssignmentList )
	{
		// this.permissionAssignmentList = permissionAssignmentList;
	}

	public PermissionAssignment addPermissionAssignment( PermissionAssignment permissionAssignment )
	{
		getPermissionAssignmentList( ).add( permissionAssignment );
		permissionAssignment.setTask( this );
		return permissionAssignment;
	}

	public PermissionAssignment removePermissionAssignment( PermissionAssignment permissionAssignment )
	{
		getPermissionAssignmentList( ).remove( permissionAssignment );
		permissionAssignment.setTask( null );
		return permissionAssignment;
	}

	public List<TaskMenu> getTaskMenuList( )
	{
		return null;// taskMenuList;
	}

	public void setTaskMenuList( List<TaskMenu> taskMenuList )
	{
		// this.taskMenuList = taskMenuList;
	}

	public TaskMenu add( TaskMenu taskMenu )
	{
		getTaskMenuList( ).add( taskMenu );
		taskMenu.setTask( this );
		return taskMenu;
	}

	public TaskMenu remove( TaskMenu taskMenu )
	{
		if ( getTaskMenuList( ) != null ) {
			getTaskMenuList( ).remove( taskMenu );
			taskMenu.setTask( null );
		}
		return taskMenu;
	}

	public Subtask addSubtask1( Subtask subtask )
	{
		getMasterTaskList( ).add( subtask );
		subtask.setTask( this );
		return subtask;
	}

	public Subtask removeSubtask1( Subtask subtask )
	{
		getMasterTaskList( ).remove( subtask );
		subtask.setTask( null );
		return subtask;
	}

	public List<Subtask> getMasterTaskList( )
	{
		return null;// masterTaskList;
	}

	public void setMasterTaskList( List<Subtask> subtaskList1 )
	{
		// masterTaskList = subtaskList1;
	}

	@Override
	public TaskDTO toDTO( )
	{
		return TaskUtil.copy( this );
	}

	@Override
	public int compareTo( Task o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof Task )
			return getId( ).equals( ( (Task) obj ).getId( ) );
		else if ( obj instanceof Integer )
			return getId( ).equals( obj );
		else
			return false;
	}

	public Task getParent( )
	{
		return parent;
	}

	public void setParent( Task parent )
	{
		this.parent = parent;
	}
}
