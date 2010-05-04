package br.com.mcampos.ejb.cloudsystem.security.entity;


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
@NamedQueries( { @NamedQuery( name = TaskMenu.findAll, query = "select o from TaskMenu o" ),
                 @NamedQuery( name = TaskMenu.findByMenu, query = "select o from TaskMenu o where o.menu = ?1" ),
                 @NamedQuery( name = TaskMenu.findByTask, query = "select o from TaskMenu o where o.task = ?1" ) } )
@Table( name = "task_menu" )
@IdClass( TaskMenuPK.class )
public class TaskMenu implements Serializable, Comparable<TaskMenu>
{
    public static final String findAll = "TaskMenu.findAll";
    public static final String findByTask = "TaskMenu.findByTask";
    public static final String findByMenu = "TaskMenu.findByMenu";

    @Id
    @Column( name = "mnu_id_in", nullable = false, insertable = false, updatable = false )
    private Integer menuId;
    @Id
    @Column( name = "tsk_id_in", nullable = false, insertable = false, updatable = false )
    private Integer taskId;
    @ManyToOne
    @JoinColumn( name = "tsk_id_in" )
    private Task task;
    @ManyToOne
    @JoinColumn( name = "mnu_id_in" )
    private Menu menu;

    public TaskMenu()
    {
    }

    public TaskMenu( Menu menu, Task task )
    {
        setMenu( menu );
        setTask( task );
    }

    public Integer getMenuId()
    {
        return menuId;
    }

    public void setMenuId( Integer mnu_id_in )
    {
        this.menuId = mnu_id_in;
    }

    public Integer getTaskId()
    {
        return taskId;
    }

    public void setTaskId( Integer tsk_id_in )
    {
        this.taskId = tsk_id_in;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask( Task task )
    {
        this.task = task;
        if ( task != null ) {
            setTaskId( task.getId() );
        }
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu( Menu menu )
    {
        this.menu = menu;
        if ( menu != null ) {
            setMenuId( menu.getId() );
        }
    }

    public int compareTo( TaskMenu o )
    {
        int nRet;
        nRet = getMenu().compareTo( o.getMenu() );
        if ( nRet != 0 )
            return nRet;
        return getTask().compareTo( o.getTask() );
    }
}
