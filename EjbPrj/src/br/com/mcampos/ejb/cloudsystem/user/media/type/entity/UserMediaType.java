package br.com.mcampos.ejb.cloudsystem.user.media.type.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = UserMediaType.getAll, query = "select o from UserMediaType o" ),
                 @NamedQuery( name = UserMediaType.nextId, query = "select max(o) from UserMediaType o" ) } )
@Table( name = "user_media_type" )
public class UserMediaType implements Serializable
{
    public static final String getAll = "UserMediaType.findAll";
    public static final String nextId = "UserMediaType.nextId";

    public static final Integer typeLogo = 1;


    @Column( name = "umt_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "umt_id_in", nullable = false )
    private Integer id;


    public UserMediaType()
    {
    }

    public UserMediaType( Integer umt_id_in )
    {
        setId( umt_id_in );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String umt_description_ch )
    {
        this.description = umt_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer umt_id_in )
    {
        this.id = umt_id_in;
    }
}
