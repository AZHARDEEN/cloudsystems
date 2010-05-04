package br.com.mcampos.ejb.entity.user;


import br.com.mcampos.ejb.entity.security.SubjectRole;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;
import br.com.mcampos.ejb.entity.user.pk.CollaboratorPK;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "Collaborator.findAll", query = "select o from Collaborator o where o.toDate is null " ),
                 @NamedQuery( name = "Collaborator.hasManager",
                              query = "select count(o) from Collaborator o where o.company.id = :companyId and o.collaboratorType.id = 1 and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP ) " ),
                 @NamedQuery( name = "Collaborator.isManager",
                              query = "select o.collaboratorId from Collaborator o where o.company.id = :companyId and o.collaboratorType.id = 1 and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )" ),
                 @NamedQuery( name = "Collaborator.hasCollaborator",
                              query = "select o from Collaborator o where o.company.id = :companyId and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )" ),
                 @NamedQuery( name = "Collaborator.findCompanies",
                              query = "select o from Collaborator o where o.person.id = :personId and o.collaboratorType.id = 1 and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )" ),
                 @NamedQuery( name = "Collaborator.countBusinessEntity",
                              query = "select count(o) from Collaborator o where o.person.id = :personId and o.collaboratorType.id = 1 and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP ) " ),
                 @NamedQuery( name = "Collaborator.getBusinessList",
                              query = "select o from Collaborator o where o.collaboratorType.id = 1 and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )" ),
                 @NamedQuery( name = "Collaborator.getBusiness",
                              query = "select o from Collaborator o where o.company.id = :companyId and o.collaboratorType.id = 1 and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )" ) } )
@Table( name = "collaborator" )
@IdClass( CollaboratorPK.class )
public class Collaborator implements Serializable
{
    @Id
    @Column( name = "col_from_dt", nullable = false )
    private Timestamp fromDate;

    @Id
    @Column( name = "col_id_in", nullable = false, insertable = false, updatable = false )
    private Integer collaboratorId;

    @Column( name = "col_to_dt" )
    private Timestamp toDate;

    @Column( name = "cps_id_in", nullable = false )
    private Integer companyPosition;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @ManyToOne
    @JoinColumn( name = "clt_id_in" )
    private CollaboratorType collaboratorType;

    @OneToMany( mappedBy = "collaborator" )
    private List<SubjectRole> subjectRoleList;

    @ManyToOne
    @JoinColumn( name = "usr_id_in" )
    private Company company;

    @ManyToOne
    @JoinColumn( name = "col_id_in", referencedColumnName = "usr_id_in", nullable = false )
    private Person person;

    public Collaborator()
    {
    }


    public Timestamp getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Timestamp col_from_dt )
    {
        this.fromDate = col_from_dt;
    }

    public Integer getCollaboratorId()
    {
        return collaboratorId;
    }

    public void setCollaboratorId( Integer col_id_in )
    {
        this.collaboratorId = col_id_in;
    }

    public Timestamp getToDate()
    {
        return toDate;
    }

    public void setToDate( Timestamp col_to_dt )
    {
        this.toDate = col_to_dt;
    }

    public Integer getCompanyPosition()
    {
        return companyPosition;
    }

    public void setCompanyPosition( Integer cps_id_in )
    {
        this.companyPosition = cps_id_in;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public CollaboratorType getCollaboratorType()
    {
        return collaboratorType;
    }

    public void setCollaboratorType( CollaboratorType collaboratorType )
    {
        this.collaboratorType = collaboratorType;
    }

    public void setCompany( Company company )
    {
        this.company = company;
        if ( getCompany() != null )
            setCompanyId( company.getId() );
    }

    public Company getCompany()
    {
        return company;
    }

    public void setPerson( Person person )
    {
        this.person = person;
        if ( getPerson() != null )
            setCollaboratorId( getPerson().getId() );
    }

    public Person getPerson()
    {
        return person;
    }

    public List<SubjectRole> getSubjectRoleList()
    {
        return subjectRoleList;
    }

    public void setSubjectRoleList( List<SubjectRole> subjectRoleList )
    {
        this.subjectRoleList = subjectRoleList;
    }

    public SubjectRole addSubjectRole( SubjectRole subjectRole )
    {
        getSubjectRoleList().add( subjectRole );
        subjectRole.setCollaborator( this );
        return subjectRole;
    }

    public SubjectRole removeSubjectRole( SubjectRole subjectRole )
    {
        getSubjectRoleList().remove( subjectRole );
        subjectRole.setCollaborator( null );
        return subjectRole;
    }
}
