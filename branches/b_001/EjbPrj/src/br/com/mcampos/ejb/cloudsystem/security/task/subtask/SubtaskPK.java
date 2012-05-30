package br.com.mcampos.ejb.cloudsystem.security.task.subtask;

import java.io.Serializable;

public class SubtaskPK implements Serializable
{
    private Integer subtaskId;
    private Integer taskId;

    public SubtaskPK()
    {
    }

    public SubtaskPK( Integer stk_id_in, Integer tsk_id_in )
    {
        this.subtaskId = stk_id_in;
        this.taskId = tsk_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof SubtaskPK ) {
            final SubtaskPK otherSubtaskPK = ( SubtaskPK )other;
            final boolean areEqual = ( otherSubtaskPK.subtaskId.equals( subtaskId ) && otherSubtaskPK.taskId.equals( taskId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getSubtaskId()
    {
        return subtaskId;
    }

    void setSubtaskId( Integer stk_id_in )
    {
        this.subtaskId = stk_id_in;
    }

    Integer getTaskId()
    {
        return taskId;
    }

    void setTaskId( Integer tsk_id_in )
    {
        this.taskId = tsk_id_in;
    }
}
