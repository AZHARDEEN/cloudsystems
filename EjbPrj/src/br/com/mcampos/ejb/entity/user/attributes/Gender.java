package br.com.mcampos.ejb.entity.user.attributes;


import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "Gender.findAll", query = "select o from Gender o")
})
@Table( name = "\"gender\"" )
public class Gender implements Serializable
{
    protected Integer id;
    protected String description;
    protected List<Title> titles; 
    
    public Gender()
    {
        super ();
    }

    public Gender ( Integer id )
    {
        super ();
        this.id = id;
    }
    
 
    protected void init ( Integer id, String description )
    {
        this.id = id;
        this.description = description;
    }
    
    public Gender( Integer id, String description )
    {
        init ( id, description );
    }

    @Column( name="gnd_description_ch", nullable = false, length = 32 )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Id
    @Column( name="gnd_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }


    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable ( 
                name = "gender_title", 
                joinColumns = @JoinColumn ( name = "gnd_id_in", referencedColumnName = "gnd_id_in", nullable = false ),
                inverseJoinColumns = @JoinColumn( name = "ttl_id_in", referencedColumnName = "ttl_id_in", nullable = false )
            )
    public List<Title> getTitles()
    {
        return titles;
    }
    
    public void setTitles( List<Title> titles )
    {
        this.titles = titles;
    }
}
