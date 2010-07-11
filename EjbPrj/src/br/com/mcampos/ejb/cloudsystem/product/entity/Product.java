package br.com.mcampos.ejb.cloudsystem.product.entity;


import br.com.mcampos.ejb.cloudsystem.product.type.entity.ProductType;

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
@NamedQueries( { @NamedQuery( name = "Product.findAll", query = "select o from Product o" ) } )
@Table( name = "\"product\"" )
@IdClass( ProductPK.class )
public class Product implements Serializable
{
    @Column( name = "prd_code_ch" )
    private String code;

    @Column( name = "prd_description_tx" )
    private String description;

    @Column( name = "prd_from_dt", nullable = false )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date fromDate;

    @Id
    @Column( name = "prd_id_in", nullable = false )
    private Integer id;

    @Column( name = "prd_name_ch", nullable = false )
    private String name;

    @Column( name = "prd_obs_tx", nullable = true )
    private String obs;

    @Column( name = "prd_to_dt", nullable = true )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date toDate;

    @Column( name = "prd_visible_bt" )
    private Boolean visible;

    @Id
    @Column( name = "usr_id_in", nullable = false )
    private Integer companyId;

    @ManyToOne( optional = false )
    @JoinColumn( name = "pdt_id_in", nullable = false, updatable = true, insertable = true )
    private ProductType type;

    public Product()
    {
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String prd_code_ch )
    {
        this.code = prd_code_ch;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String prd_description_tx )
    {
        this.description = prd_description_tx;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Date prd_from_dt )
    {
        this.fromDate = prd_from_dt;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer prd_id_in )
    {
        this.id = prd_id_in;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String prd_name_ch )
    {
        this.name = prd_name_ch;
    }

    public String getObs()
    {
        return obs;
    }

    public void setObs( String prd_obs_tx )
    {
        this.obs = prd_obs_tx;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate( Date prd_to_dt )
    {
        this.toDate = prd_to_dt;
    }

    public Boolean getVisible()
    {
        return visible;
    }

    public void setVisible( Boolean prd_visible_bt )
    {
        this.visible = prd_visible_bt;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public void setType( ProductType type )
    {
        this.type = type;
    }

    public ProductType getType()
    {
        return type;
    }
}
