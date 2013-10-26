package br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = Title.getAll, query = "select o from Title o" ),
                 @NamedQuery( name = Title.getNextId, query = "select max(o.id) from Title o " ) } )
@Table( name = "title" )
public class Title implements Serializable
{
    public static final String getAll = "Title.findAll";
    public static final String getNextId = "Title.nextId";


    @Column( name = "ttl_abrev_ch", length = 12 )
    private String abbreviation;

    @Column( name = "ttl_description_ch", nullable = false, length = 32 )
    private String description;

    @Id
    @Column( name = "ttl_id_in", nullable = false )
    private Integer id;

    public Title()
    {
    }

    public Title( Integer id )
    {
        setId( id );
    }


    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation( String ttl_abrev_ch )
    {
        this.abbreviation = ttl_abrev_ch;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String ttl_description_ch )
    {
        this.description = ttl_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer ttl_id_in )
    {
        this.id = ttl_id_in;
    }
}
