package br.com.mcampos.ejb.cloudsystem.product.type.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = ProductType.getAll, query = "select o from ProductType o" ),
                 @NamedQuery( name = ProductType.nextId, query = "select max(o.id) from ProductType o" ) } )
@Table( name = "product_type" )
public class ProductType implements Serializable
{
    public static final String getAll = "ProductType.findAll";
    public static final String nextId = "ProductType.nextId";

    @Column( name = "pdt_default_bt", nullable = false )
    private Boolean isDefault;

    @Column( name = "pdt_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "pdt_id_in", nullable = false )
    private Integer id;


    public ProductType()
    {
    }


    public ProductType( Integer id )
    {
        setId( id );
    }

    public Boolean getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault( Boolean pdt_default_bt )
    {
        this.isDefault = pdt_default_bt;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String pdt_description_ch )
    {
        this.description = pdt_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer pdt_id_in )
    {
        this.id = pdt_id_in;
    }
}
