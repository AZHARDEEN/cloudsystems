package br.com.mcampos.ejb.entity.user;

import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;

import br.com.mcampos.ejb.entity.user.pk.CollaboratorPK;

import java.io.Serializable;

import java.sql.Timestamp;

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
@NamedQueries({
  @NamedQuery(name = "Collaborator.findAll", 
              query = "select o from Collaborator o where o.toDate is null "),
  @NamedQuery(name = "Collaborator.hasManager", 
              query = "select count(o) from Collaborator o where o.company.id = :companyId and o.collaboratorType.id = 1 and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP ) "),
  @NamedQuery(name = "Collaborator.isManager", 
              query = "select o.collaboratorId from Collaborator o where o.company.id = :companyId and o.collaboratorType.id = 1 and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )"),
  @NamedQuery(name = "Collaborator.hasCollaborator", 
              query = "select o from Collaborator o where o.company.id = :companyId and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )"),
  @NamedQuery(name = "Collaborator.findCompanies", 
              query = "select o from Collaborator o where o.person.id = :personId and o.toDate is null"),
  @NamedQuery(name = "Collaborator.countBusinessEntity", 
              query = "select count(o) from Collaborator o where o.person.id = :personId and o.collaboratorType.id = 1 and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP ) "),
  @NamedQuery(name = "Collaborator.getBusinessList", 
              query = "select o from Collaborator o where o.collaboratorType.id = 1 and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )"),
  @NamedQuery(name = "Collaborator.getBusiness", 
              query = "select o from Collaborator o where o.company.id = :companyId and o.collaboratorType.id = 1 and o.person.id = :personId and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )")
})
@Table( name = "\"collaborator\"" )
@IdClass( CollaboratorPK.class )
public class Collaborator implements Serializable
{
    private Timestamp fromDate;
    private Integer collaboratorId;
    private Timestamp toDate;
    private Integer companyPosition;
    private Integer companyId;
    private CollaboratorType collaboratorType;
    
    private Company company;
    private Person person;

    public Collaborator()
    {
    }


    @Id
    @Column( name="col_from_dt", nullable = false )
    public Timestamp getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Timestamp col_from_dt )
    {
        this.fromDate = col_from_dt;
    }

    @Id
    @Column( name="col_id_in", nullable = false, insertable = false, updatable = false)
    public Integer getCollaboratorId()
    {
        return collaboratorId;
    }

    public void setCollaboratorId( Integer col_id_in )
    {
        this.collaboratorId = col_id_in;
    }

    @Column( name="col_to_dt" )
    public Timestamp getToDate()
    {
        return toDate;
    }

    public void setToDate( Timestamp col_to_dt )
    {
        this.toDate = col_to_dt;
    }

    @Column( name="cps_id_in", nullable = false )
    public Integer getCompanyPosition()
    {
        return companyPosition;
    }

    public void setCompanyPosition( Integer cps_id_in )
    {
        this.companyPosition = cps_id_in;
    }

    @Id
    @Column( name="usr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    @ManyToOne
    @JoinColumn( name = "clt_id_in" )
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
            this.companyId = company.getId();
    }

    @ManyToOne
    @JoinColumn( name = "usr_id_in" )
    public Company getCompany()
    {
        return company;
    }

    public void setPerson( Person person )
    {
        this.person = person;
        if ( getPerson() != null )
            this.collaboratorId = getPerson().getId();
    }

    @ManyToOne
    @JoinColumn( name = "col_id_in", referencedColumnName = "usr_id_in", nullable = false )
    public Person getPerson()
    {
        return person;
    }
}
