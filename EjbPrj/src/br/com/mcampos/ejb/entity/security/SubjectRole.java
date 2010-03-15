package br.com.mcampos.ejb.entity.security;

import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.entity.user.Collaborator;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "SubjectRole.findAll", query = "select o from SubjectRole o" ),
                 @NamedQuery( name = "SubjectRole.findCollaboratorRoles",
                              query = "select o from SubjectRole o where o.collaborator.collaboratorId = :id and ( o.collaborator.toDate is null or o.collaborator.toDate >= CURRENT_TIMESTAMP )" ) } )
@Table( name = "\"subject_role\"" )
@IdClass( SubjectRolePK.class )
public class SubjectRole implements Serializable
{
    private Timestamp from;
    private Integer collaboratorId;
    private Integer roleId;
    private Integer companyId;
    private Role role;
    private Collaborator collaborator;

    public SubjectRole()
    {
    }

    @Id
    @Column( name = "col_from_dt", nullable = false, insertable = false, updatable = false )
    public Timestamp getFrom()
    {
        return from;
    }

    public void setFrom( Timestamp col_from_dt )
    {
        this.from = col_from_dt;
    }

    @Id
    @Column( name = "col_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getCollaboratorId()
    {
        return collaboratorId;
    }

    public void setCollaboratorId( Integer col_id_in )
    {
        this.collaboratorId = col_id_in;
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
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    @ManyToOne
    @JoinColumn( name = "rol_id_in" )
    public Role getRole()
    {
        return role;
    }

    public void setRole( Role role1 )
    {
        this.role = role1;
        if ( role1 != null ) {
            this.roleId = role1.getId();
        }
    }

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in" ),
                    @JoinColumn( name = "col_id_in", referencedColumnName = "col_id_in" ),
                    @JoinColumn( name = "col_from_dt", referencedColumnName = "col_from_dt" ) } )
    public Collaborator getCollaborator()
    {
        return collaborator;
    }

    public void setCollaborator( Collaborator collaborator )
    {
        this.collaborator = collaborator;
        if ( collaborator != null ) {
            this.from = collaborator.getFromDate();
            this.collaboratorId = collaborator.getCollaboratorId();
            this.companyId = collaborator.getCompanyId();
        }
    }
}
