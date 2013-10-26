package br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = CompanyPosition.getAll, query = "select o from CompanyPosition o" ),
                 @NamedQuery( name = CompanyPosition.nextId, query = "select max(o.id) from CompanyPosition o" ) } )
@Table( name = "company_position" )
public class CompanyPosition implements Serializable
{
    public static final String getAll = "CompanyPosition.findAll";
    public static final String nextId = "CompanyPosition.nextId";

    @Column( name = "cps_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "cps_id_in", nullable = false )
    private Integer id;

    public CompanyPosition()
    {
    }

    public CompanyPosition( Integer id )
    {
        this.id = id;
    }

    public CompanyPosition( Integer id, String description )
    {
        this.description = description;
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }
}
