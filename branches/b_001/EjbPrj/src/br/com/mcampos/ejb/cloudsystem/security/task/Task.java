package br.com.mcampos.ejb.cloudsystem.security.task;


import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.task.subtask.Subtask;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenu;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = Task.findAll, query = "select o from Task o" ) } )
@NamedNativeQueries( { @NamedNativeQuery( name = Task.rootTasks,
										  query = "SELECT * FROM TASK T WHERE NOT EXISTS ( SELECT * FROM SUBTASK S WHERE T.TSK_ID_IN = S.STK_ID_IN )",
										  resultClass = Task.class ) } )
@Table( name = "task" )
public class Task implements Serializable, EntityCopyInterface<TaskDTO>, Comparable<Task>
{
	public static final String findAll = "Task.findAll";
	public static final String rootTasks = "Task.rootTasks";

	@Column( name = "tsk_description_ch", nullable = false )
	private String description;
	@Id
	@Column( name = "tsk_id_in", nullable = false )
	private Integer id;

	@OneToMany( mappedBy = "task", cascade = CascadeType.REFRESH )
	private List<Subtask> subtasks;

	@OneToMany( mappedBy = "task", cascade = CascadeType.REFRESH )
	private List<PermissionAssignment> permissionAssignmentList;

	@OneToMany( mappedBy = "task", cascade = CascadeType.REFRESH )
	private List<TaskMenu> taskMenuList;

	@OneToMany( mappedBy = "subTask", cascade = CascadeType.REFRESH )
	private List<Subtask> masterTaskList;

	public Task()
	{
	}

	public Task( String tsk_description_ch, Integer tsk_id_in )
	{
		this.description = tsk_description_ch;
		this.id = tsk_id_in;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String tsk_description_ch )
	{
		this.description = tsk_description_ch;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer tsk_id_in )
	{
		this.id = tsk_id_in;
	}

	public List<Subtask> getSubtasks()
	{
		return subtasks;
	}

	public void setSubtasks( List<Subtask> subtaskList )
	{
		this.subtasks = subtaskList;
	}

	public Subtask addSubtask( Subtask subtask )
	{
		getSubtasks().add( subtask );
		subtask.setSubTask( this );
		return subtask;
	}

	public Subtask removeSubtask( Subtask subtask )
	{
		getSubtasks().remove( subtask );
		subtask.setSubTask( null );
		return subtask;
	}

	public List<PermissionAssignment> getPermissionAssignmentList()
	{
		return permissionAssignmentList;
	}

	public void setPermissionAssignmentList( List<PermissionAssignment> permissionAssignmentList )
	{
		this.permissionAssignmentList = permissionAssignmentList;
	}

	public PermissionAssignment addPermissionAssignment( PermissionAssignment permissionAssignment )
	{
		getPermissionAssignmentList().add( permissionAssignment );
		permissionAssignment.setTask( this );
		return permissionAssignment;
	}

	public PermissionAssignment removePermissionAssignment( PermissionAssignment permissionAssignment )
	{
		getPermissionAssignmentList().remove( permissionAssignment );
		permissionAssignment.setTask( null );
		return permissionAssignment;
	}

	public List<TaskMenu> getTaskMenuList()
	{
		return taskMenuList;
	}

	public void setTaskMenuList( List<TaskMenu> taskMenuList )
	{
		this.taskMenuList = taskMenuList;
	}

	public TaskMenu add( TaskMenu taskMenu )
	{
		getTaskMenuList().add( taskMenu );
		taskMenu.setTask( this );
		return taskMenu;
	}

	public TaskMenu remove( TaskMenu taskMenu )
	{
		if ( getTaskMenuList() != null ) {
			getTaskMenuList().remove( taskMenu );
			taskMenu.setTask( null );
		}
		return taskMenu;
	}

	public Subtask addSubtask1( Subtask subtask )
	{
		getMasterTaskList().add( subtask );
		subtask.setTask( this );
		return subtask;
	}

	public Subtask removeSubtask1( Subtask subtask )
	{
		getMasterTaskList().remove( subtask );
		subtask.setTask( null );
		return subtask;
	}

	public List<Subtask> getMasterTaskList()
	{
		return masterTaskList;
	}

	public void setMasterTaskList( List<Subtask> subtaskList1 )
	{
		this.masterTaskList = subtaskList1;
	}

	@Override
	public TaskDTO toDTO()
	{
		return TaskUtil.copy( this );
	}

	@Override
	public int compareTo( Task o )
	{
		return getId().compareTo( o.getId() );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof Task )
			return getId().equals( ( ( Task )obj ).getId() );
		else if ( obj instanceof Integer )
			return getId().equals( ( Integer )obj );
		else
			return false;
	}
}