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
@NamedQueries( { @NamedQuery( name = "TaskMenu.findAll", query = "select o from TaskMenu o" ) } )
@Table( name = "\"task_menu\"" )
@IdClass( TaskMenuPK.class )
public class TaskMenu implements Serializable
{
    private Integer menuId;
    private Integer taskId;
    private Task task;
    private Menu menu;

    public TaskMenu()
    {
    }

    public TaskMenu( Integer mnu_id_in, Task task )
    {
        this.menuId = mnu_id_in;
        this.task = task;
    }

    @Id
    @Column( name = "mnu_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getMenuId()
    {
        return menuId;
    }

    public void setMenuId( Integer mnu_id_in )
    {
        this.menuId = mnu_id_in;
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
    @JoinColumn( name = "tsk_id_in" )
    public Task getTask()
    {
        return task;
    }

    public void setTask( Task task )
    {
        this.task = task;
        if ( task != null ) {
            this.taskId = task.getId();
        }
    }

    @ManyToOne
    @JoinColumn( name = "mnu_id_in" )
    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu( Menu menu )
    {
        this.menu = menu;
        if ( menu != null ) {
            this.menuId = menu.getId();
        }
    }
}
