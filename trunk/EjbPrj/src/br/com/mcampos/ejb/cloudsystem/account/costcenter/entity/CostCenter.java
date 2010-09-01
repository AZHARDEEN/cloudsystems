package br.com.mcampos.ejb.cloudsystem.account.costcenter.entity;


import java.io.Serializable;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = CostCenter.getAll, query = "select o from CostCenter o where o.costArea = ?1 order by o.id" ),
                 @NamedQuery( name = CostCenter.nextId, query = "select max(o.id) from CostCenter o where o.costArea = ?1" ) } )
@Table( name = "cost_center" )
@IdClass( CostCenterPK.class )
public class CostCenter implements Serializable
{
    public static final String getAll = "CostCenter.findAll";
    public static final String nextId = "CostCenter.nextId";

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @Id
    @Column( name = "car_id_in", nullable = false, insertable = false, updatable = false )
    private Integer areaId;

    @Id
    @Column( name = "cct_id_in", nullable = false )
    private Integer id;

    @Column( name = "cct_description_ch", nullable = false )
    private String description;

    @Column( name = "cct_from_dt", nullable = false )
    @Temporal( TemporalType.DATE )
    private Date from;

    @Column( name = "cct_to_dt", nullable = true )
    @Temporal( TemporalType.DATE )
    private Date to;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in" ),
                    @JoinColumn( name = "car_id_in", referencedColumnName = "car_id_in" ) } )
    private CostArea costArea;


    public CostCenter()
    {
    }


    public CostCenter( CostArea costArea, Integer id )
    {
        setCostArea( costArea );
        setId( id );
    }

    public Integer getAreaId()
    {
        return areaId;
    }

    public void setAreaId( Integer car_id_in )
    {
        this.areaId = car_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String cct_description_ch )
    {
        this.description = cct_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer cct_id_in )
    {
        this.id = cct_id_in;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public CostArea getCostArea()
    {
        return costArea;
    }

    public void setCostArea( CostArea costArea )
    {
        this.costArea = costArea;
        if ( costArea != null ) {
            this.areaId = costArea.getId();
            this.companyId = costArea.getCompanyId();
        }
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

    @Override
    public String toString()
    {
        return getDescription();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof CostCenter ) {
            CostCenter c = ( CostCenter )obj;
            return getCostArea().equals( c.getCostArea() ) && getId().equals( c.getId() );
        }
        else
            return false;
    }
}
