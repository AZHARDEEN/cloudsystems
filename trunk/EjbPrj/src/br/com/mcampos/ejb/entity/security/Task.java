package br.com.mcampos.ejb.entity.security;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "Task.findAll", query = "select o from Task o where o.masterTaskList is EMPTY" ) } )
@Table( name = "\"task\"" )
public class Task implements Serializable
{
    private String description;
    private Integer id;
    private List<Subtask> subtasks;
    private List<PermissionAssignment> permissionAssignmentList;
    private List<TaskMenu> taskMenuList;
    private List<Subtask> masterTaskList;

    public Task()
    {
    }

    public Task( String tsk_description_ch, Integer tsk_id_in )
    {
        this.description = tsk_description_ch;
        this.id = tsk_id_in;
    }

    @Column( name = "tsk_description_ch", nullable = false )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String tsk_description_ch )
    {
        this.description = tsk_description_ch;
    }

    @Id
    @Column( name = "tsk_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer tsk_id_in )
    {
        this.id = tsk_id_in;
    }

    @OneToMany( mappedBy = "task" )
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

    @OneToMany( mappedBy = "task" )
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

    @OneToMany( mappedBy = "task", cascade = CascadeType.REFRESH )
    public List<TaskMenu> getTaskMenuList()
    {
        return taskMenuList;
    }

    public void setTaskMenuList( List<TaskMenu> taskMenuList )
    {
        this.taskMenuList = taskMenuList;
    }

    public TaskMenu addTaskMenu( TaskMenu taskMenu )
    {
        getTaskMenuList().add( taskMenu );
        taskMenu.setTask( this );
        return taskMenu;
    }

    public TaskMenu removeTaskMenu( TaskMenu taskMenu )
    {
        getTaskMenuList().remove( taskMenu );
        taskMenu.setTask( null );
        return taskMenu;
    }

    @OneToMany( mappedBy = "subTask" )
    public List<Subtask> getMasterTaskList()
    {
        return masterTaskList;
    }

    public void setMasterTaskList( List<Subtask> subtaskList1 )
    {
        this.masterTaskList = subtaskList1;
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
}
