package br.com.mcampos.ejb.cloudsystem.security.task.subtask;


import br.com.mcampos.ejb.cloudsystem.security.task.Task;

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
@NamedQueries( { @NamedQuery( name = Subtask.findAll, query = "select o from Subtask o" ),
                 @NamedQuery( name = Subtask.findbyTask, query = "select o from Subtask o where o.task = ?1" ) } )
@Table( name = "subtask" )
@IdClass( SubtaskPK.class )
public class Subtask implements Serializable
{
    public static final String findAll = "Subtask.findAll";
    public static final String findbyTask = "Subtask.findByTask";

    @Id
    @Column( name = "stk_id_in", nullable = false, insertable = false, updatable = false )
    private Integer subtaskId;

    @Id
    @Column( name = "tsk_id_in", nullable = false, insertable = false, updatable = false )
    private Integer taskId;

    @ManyToOne
    @JoinColumn( name = "stk_id_in" )
    private Task subTask;

    @ManyToOne
    @JoinColumn( name = "tsk_id_in" )
    private Task task;

    public Subtask()
    {
    }

    public Subtask( Task masterTask, Task subTask )
    {
        setTask( masterTask );
        setSubTask( subTask );
    }

    public Integer getSubtaskId()
    {
        return subtaskId;
    }

    public void setSubtaskId( Integer stk_id_in )
    {
        this.subtaskId = stk_id_in;
    }

    public Integer getTaskId()
    {
        return taskId;
    }

    public void setTaskId( Integer tsk_id_in )
    {
        this.taskId = tsk_id_in;
    }

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
