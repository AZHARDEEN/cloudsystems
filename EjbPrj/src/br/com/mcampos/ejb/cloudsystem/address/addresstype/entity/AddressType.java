package br.com.mcampos.ejb.cloudsystem.address.addresstype.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = AddressType.getAll, query = "select o from AddressType o order by o.id" ),
                 @NamedQuery( name = AddressType.nextId, query = "select max(o.id) from AddressType o" ) } )
@Table( name = "address_type" )
public class AddressType implements Serializable
{
    public static final String getAll = "AddressType.findAll";
    public static final String nextId = "AddressType.nextId";

    @Id
    @Column( name = "adt_id_in", nullable = false )
    protected Integer id;

    @Column( name = "adt_description_ch", nullable = false, length = 32 )
    protected String description;

    public AddressType()
    {
        super();
    }

    public AddressType( Integer id )
    {
        super();
        this.id = id;
    }

    public AddressType( Integer id, String description )
    {
        super();
        setId( id );
        setDescription( description );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String adt_description_ch )
    {
        this.description = adt_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer adt_id_in )
    {
        this.id = adt_id_in;
    }
}
