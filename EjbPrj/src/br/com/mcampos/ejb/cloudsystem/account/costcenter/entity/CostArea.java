package br.com.mcampos.ejb.cloudsystem.account.costcenter.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@NamedQueries( { @NamedQuery( name = CostArea.getAll, query = "select o from CostArea o where o.company = ?1 order by o.id" ),
                 @NamedQuery( name = CostArea.nextId, query = "select max(o.id) from CostArea o where o.company = ?1" ) } )
@Table( name = "cost_area" )
@IdClass( CostAreaPK.class )
public class CostArea implements Serializable
{

    public static final String getAll = "CostArea.findAll";
    public static final String nextId = "CostArea.nextId";

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @Id
    @Column( name = "car_id_in", nullable = false )
    private Integer id;

    @Column( name = "car_description_ch", nullable = false )
    private String description;

    @Column( name = "car_from_dt", nullable = false )
    @Temporal( TemporalType.DATE )
    private Date from;

    @Column( name = "car_to_dt", nullable = true )
    @Temporal( TemporalType.DATE )
    private Date to;


    @ManyToOne( optional = false )
    @JoinColumn( name = "usr_id_in", nullable = false, updatable = true, insertable = true )
    private Company company;


    public CostArea()
    {
    }


    public CostArea( Company owner, Integer id )
    {
        setCompany( owner );
        setId( id );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String car_description_ch )
    {
        this.description = car_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer car_id_in )
    {
        this.id = car_id_in;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public void setCompany( Company company )
    {
        this.company = company;
        setCompanyId( company != null ? company.getId() : null );
    }

    public Company getCompany()
    {
        return company;
    }

    @Override
    public String toString()
    {
        return getDescription();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof CostArea ) {
            CostArea c = ( CostArea )obj;
            return getCompany().equals( c.getCompany() ) && getId().equals( c.getId() );
        }
        else
            return false;
    }

    public void setFrom( Date from )
    {
        this.from = from;
    }

    public Date getFrom()
    {
        return from;
    }

    public void setTo( Date to )
    {
        this.to = to;
    }

    public Date getTo()
    {
        return to;
    }
}
