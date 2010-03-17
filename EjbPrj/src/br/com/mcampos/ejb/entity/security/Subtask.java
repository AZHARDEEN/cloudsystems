package br.com.mcampos.ejb.entity.security;

import br.com.mcampos.ejb.cloudsystem.security.entity.Task;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Subtask.findAll", query = "select o from Subtask o" ) } )
@Table( name = "\"subtask\"" )
@IdClass( SubtaskPK.class )
public class Subtask implements Serializable
{
    private Integer subtaskId;
    private Integer taskId;
    private Task subTask;
    private Task task;

    public Subtask()
    {
    }

    public Subtask( Task task, Task task1 )
    {
        this.subTask = task;
        this.task = task1;
    }

    @Id
    @Column( name = "stk_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getSubtaskId()
    {
        return subtaskId;
    }

    public void setSubtaskId( Integer stk_id_in )
    {
        this.subtaskId = stk_id_in;
    }

    @Id
    @Column( name = "tsk_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getTaskId()
    {
        return taskId;
    }

    public void setTaskId( Integer tsk_id_in )
    {
        this.taskId = tsk_id_in;
    }

    @ManyToOne
    @JoinColumn( name = "stk_id_in" )
    public Task getSubTask()
    {
        return subTask;
    }

    public void setSubTask( Task task )
    {
        this.subTask = task;
        if ( task != null ) {
            this.subtaskId = task.getId();
        }
    }

    @ManyToOne
    @JoinColumn( name = "tsk_id_in" )
    public Task getTask()
    {
        return task;
    }

    public void setTask( Task task1 )
    {
        this.task = task1;
        if ( task1 != null ) {
            this.taskId = task1.getId();
        }
    }
}
