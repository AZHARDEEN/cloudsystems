package br.com.mcampos.ejb.entity.security;


import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
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
@NamedQueries( {
    @NamedQuery( name = PermissionAssignment.findAll, query = "select o from PermissionAssignment o" ),
    @NamedQuery( name = PermissionAssignment.findByTask, query = "select o from PermissionAssignment o where o.role = ?1" )
    } )
@Table( name = "permission_assignment" )
@IdClass( PermissionAssignmentPK.class )
public class PermissionAssignment implements Serializable
{
    public static final String findByTask = "PermissionAssignment.findByTasks";
    public static final String findAll = "PermissionAssignment.findAll";
    private Integer permissonId;
    private Integer roleId;
    private Integer taskId;
    private Task task;
    private Role role;

    public PermissionAssignment()
    {
    }

    public PermissionAssignment( Integer prm_id_in, Integer rol_id_in, Task task )
    {
        this.permissonId = prm_id_in;
        this.roleId = rol_id_in;
        this.task = task;
    }

    @Column( name = "prm_id_in", nullable = false )
    public Integer getPermissonId()
    {
        return permissonId;
    }

    public void setPermissonId( Integer prm_id_in )
    {
        this.permissonId = prm_id_in;
    }

    @Id
    @Column( name = "rol_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer rol_id_in )
    {
        this.roleId = rol_id_in;
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

    public void setRole( Role role )
    {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn( name = "rol_id_in" )
    public Role getRole()
    {
        return role;
    }
}
