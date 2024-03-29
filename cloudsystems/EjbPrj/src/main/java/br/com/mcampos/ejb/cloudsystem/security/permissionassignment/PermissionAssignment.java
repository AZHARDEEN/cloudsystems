package br.com.mcampos.ejb.cloudsystem.security.permissionassignment;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
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
@NamedQueries( { @NamedQuery( name = PermissionAssignment.findAll, query = "select o from PermissionAssignment o" ),
                 @NamedQuery( name = PermissionAssignment.findByTask,
                              query = "select o from PermissionAssignment o where o.task = ?1" ),
                 @NamedQuery( name = PermissionAssignment.findByRole,
                              query = "select o from PermissionAssignment o where o.role = ?1" ) } )
@Table( name = "permission_assignment" )
@IdClass( PermissionAssignmentPK.class )
public class PermissionAssignment implements Serializable
{
    public static final String findByTask = "PermissionAssignment.findByTask";
    public static final String findAll = "PermissionAssignment.findAll";
    public static final String findByRole = "PermissionAssignment.findByRole";

    @Column( name = "prm_id_in", nullable = false )
    private Integer permissonId;
    @Id
    @Column( name = "rol_id_in", nullable = false, insertable = false, updatable = false )
    private Integer roleId;
    @Id
    @Column( name = "tsk_id_in", nullable = false, insertable = false, updatable = false )
    private Integer taskId;
    @ManyToOne
    @JoinColumn( name = "tsk_id_in" )
    private Task task;
    @ManyToOne
    @JoinColumn( name = "rol_id_in" )
    private Role role;

    public PermissionAssignment()
    {
    }

    public PermissionAssignment( Role role, Task task )
    {
        this.permissonId = 1;
        setRole( role );
        setTask( task );
    }

    public Integer getPermissonId()
    {
        return permissonId;
    }

    public void setPermissonId( Integer prm_id_in )
    {
        this.permissonId = prm_id_in;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer rol_id_in )
    {
        this.roleId = rol_id_in;
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

    public void setRole( Role role )
    {
        this.role = role;
        if ( role != null )
            setRoleId( role.getId() );
    }

    public Role getRole()
    {
        return role;
    }
}
