package br.com.mcampos.ejb.entity.user.attributes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "CompanyPosition.findAll", query = "select o from CompanyPosition o")
})
@Table( name = "\"company_position\"" )
public class CompanyPosition implements Serializable
{
    private String description;
    private Integer id;

    public CompanyPosition()
    {
    }

    public CompanyPosition( Integer id, String description )
    {
        this.description = description;
        this.id = id;
    }

    @Column( name="cps_description_ch", nullable = false )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Id
    @Column( name="cps_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }
}
