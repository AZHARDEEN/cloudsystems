package br.com.mcampos.ejb.cloudsystem.security.entity;

import java.io.Serializable;

public class TaskMenuPK implements Serializable
{
    private Integer menuId;
    private Integer taskId;

    public TaskMenuPK()
    {
    }

    public TaskMenuPK( Integer mnu_id_in, Integer tsk_id_in )
    {
        this.menuId = mnu_id_in;
        this.taskId = tsk_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof TaskMenuPK ) {
            final TaskMenuPK otherTaskMenuPK = ( TaskMenuPK )other;
            final boolean areEqual = ( otherTaskMenuPK.menuId.equals( menuId ) && otherTaskMenuPK.taskId.equals( taskId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getMenuId()
    {
        return menuId;
    }

    void setMenuId( Integer mnu_id_in )
    {
        this.menuId = mnu_id_in;
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
