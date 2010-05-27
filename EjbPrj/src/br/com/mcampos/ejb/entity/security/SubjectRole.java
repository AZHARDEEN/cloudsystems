package br.com.mcampos.ejb.entity.security;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
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
@Table( name = "subject_role" )
@IdClass( SubjectRolePK.class )
public class SubjectRole implements Serializable
{
    @Id
    @Column( name = "col_from_dt", nullable = false, insertable = false, updatable = false )
    private Timestamp from;
    @Id
    @Column( name = "col_id_in", nullable = false, insertable = false, updatable = false )
    private Integer collaboratorId;
    @Id
    @Column( name = "rol_id_in", nullable = false, insertable = false, updatable = false )
    private Integer roleId;
    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;
    @ManyToOne
    @JoinColumn( name = "rol_id_in" )
    private Role role;
    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in" ),
                    @JoinColumn( name = "col_id_in", referencedColumnName = "col_id_in" ),
                    @JoinColumn( name = "col_from_dt", referencedColumnName = "col_from_dt" ) } )
    private Collaborator collaborator;

    public SubjectRole()
    {
    }

    public Timestamp getFrom()
    {
        return from;
    }

    public void setFrom( Timestamp col_from_dt )
    {
        this.from = col_from_dt;
    }

    public Integer getCollaboratorId()
    {
        return collaboratorId;
    }

    public void setCollaboratorId( Integer col_id_in )
    {
        this.collaboratorId = col_id_in;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer rol_id_in )
    {
        this.roleId = rol_id_in;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole( Role role1 )
    {
        this.role = role1;
        if ( role1 != null ) {
            setRoleId( role1.getId() );
        }
    }

    public Collaborator getCollaborator()
    {
        return collaborator;
    }

    public void setCollaborator( Collaborator collaborator )
    {
        this.collaborator = collaborator;
        if ( collaborator != null ) {
            setFrom( collaborator.getFromDate() );
            setCollaboratorId( collaborator.getCollaboratorId() );
            setCompanyId( collaborator.getCompanyId() );
        }
    }
}
