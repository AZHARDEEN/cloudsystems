package br.com.mcampos.ejb.cloudsystem.user.collaborator.entity;


import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity.CollaboratorType;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = "Collaborator.findAll", query = "select o from Collaborator o where o.toDate is null " ),
                 @NamedQuery( name = Collaborator.getAllCompanyCollaborator,
                              query = "select o from Collaborator o where o.company = ?1 and o.toDate is null" ),
                 @NamedQuery( name = Collaborator.getAllCompanyCollaboratorType,
                              query = "select o from Collaborator o where o.company = ?1 and o.collaboratorType.id = ?2 and o.toDate is null" ),
                 @NamedQuery( name = Collaborator.hasCollaborator,
                              query = "select o from Collaborator o where o.company = ?1 and o.person = ?2 and o.toDate is null " ),
                 @NamedQuery( name = Collaborator.findCompanies,
                              query = "select o from Collaborator o where o.person.id = ?1 and o.collaboratorType.id = 1 and o.toDate is null" ),
                 @NamedQuery( name = Collaborator.nextSequence,
                              query = "select max(o.sequence) from Collaborator o where o.company = ?1" ) } )
@Table( name = "collaborator" )
@IdClass( CollaboratorPK.class )
public class Collaborator implements Serializable
{
    public static final String findCompanies = "Collaborator.findCompanies";
    public static final String hasCollaborator = "Collaborator.hasCollaborator";
    public static final String getAllCompanyCollaborator = "Collaborator.getAllCompanyCollaborator";
    public static final String getAllCompanyCollaboratorType = "Collaborator.getAllCompanyCollaboratorType";
    public static final String nextSequence = "Collaborator.nextSequence";


    @Column( name = "col_from_dt", nullable = false )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date fromDate;

    @Column( name = "col_id_in", nullable = false, insertable = false, updatable = false )
    private Integer collaboratorId;

    @Id
    @Column( name = "col_seq_in", nullable = false )
    private Integer sequence;

    @Column( name = "col_to_dt" )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date toDate;

    @Column( name = "cps_id_in", nullable = false )
    private Integer companyPosition;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @ManyToOne
    @JoinColumn( name = "clt_id_in" )
    private CollaboratorType collaboratorType;

    @ManyToOne
    @JoinColumn( name = "usr_id_in" )
    private Company company;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "col_id_in", referencedColumnName = "usr_id_in", nullable = false )
    private Person person;

    public Collaborator()
    {
    }


    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Date col_from_dt )
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

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate( Date col_to_dt )
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

    public void setSequence( Integer collaboratorSequence )
    {
        this.sequence = collaboratorSequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }
}
