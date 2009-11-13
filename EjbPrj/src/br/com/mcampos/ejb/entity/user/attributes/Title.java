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
  @NamedQuery(name = "Title.findAll", query = "select o from Title o order by o.abbreviation")
})
@Table( name = "\"title\"" )
public class Title implements Serializable
{
    private String abbreviation;
    private String description;
    private Integer id;

    public Title()
    {
        super ();
    }

    public Title ( Integer id )
    {
        super ();
        this.id = id;
    }

    public Title ( Integer id, String description, String abbreviation )
    {
        super ();
        init ( id, description, abbreviation );
    }
    
    protected void init ( Integer id, String description, String abbreviation )
    {
        this.id = id;
        this.abbreviation = abbreviation;
        this.description = description;
    }

    @Column( name="ttl_abrev_ch", length = 12 )
    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation( String abbrev )
    {
        this.abbreviation = abbrev;
    }


    @Column( name="ttl_description_ch", nullable = false, length = 32 )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Id
    @Column( name="ttl_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }
}

