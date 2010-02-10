package br.com.mcampos.ejb.entity.security;

import java.io.Serializable;

public class PermissionAssignmentPK implements Serializable
{
    private Integer roleId;
    private Integer taskId;

    public PermissionAssignmentPK()
    {
    }

    public PermissionAssignmentPK( Integer rol_id_in, Integer tsk_id_in )
    {
        this.roleId = rol_id_in;
        this.taskId = tsk_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PermissionAssignmentPK ) {
            final PermissionAssignmentPK otherPermissionAssignmentPK = ( PermissionAssignmentPK )other;
            final boolean areEqual = ( otherPermissionAssignmentPK.roleId.equals( roleId ) && otherPermissionAssignmentPK.taskId.equals( taskId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getRoleId()
    {
        return roleId;
    }

    void setRoleId( Integer rol_id_in )
    {
        this.roleId = rol_id_in;
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
