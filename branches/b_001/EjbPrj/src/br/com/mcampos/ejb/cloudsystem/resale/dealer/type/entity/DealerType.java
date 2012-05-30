package br.com.mcampos.ejb.cloudsystem.resale.dealer.type.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = DealerType.getAll, query = "select o from DealerType o" ),
                 @NamedQuery( name = DealerType.nextId, query = "select max(o.id) from DealerType o" ) } )
@Table( name = "dealer_type" )
public class DealerType implements Serializable
{
    public static final String getAll = "DealerType.findAll";
    public static final String nextId = "DealerType.nextId";

    public static final Integer typeDealer = 1;

    @Column( name = "dtp_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "dtp_id_in", nullable = false )
    private Integer id;

    public DealerType()
    {
    }

    public DealerType( Integer id )
    {
        setId( id );
    }

    public DealerType( Integer dtp_id_in, String dtp_description_ch )
    {
        setDescription( dtp_description_ch );
        setId( dtp_id_in );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String dtp_description_ch )
    {
        this.description = dtp_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer dtp_id_in )
    {
        this.id = dtp_id_in;
    }
}
