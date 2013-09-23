package br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity;


import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = SystemUserProperty.getAll,
                              query = "select o from SystemUserProperty o order by o.description" ),
                 @NamedQuery( name = SystemUserProperty.nextId, query = "select max(o.id) from SystemUserProperty o" ) } )
@Table( name = "system_user_property" )
public class SystemUserProperty implements Serializable
{
    public static final String getAll = "SystemUserProperty.findAll";
    public static final String nextId = "SystemUserProperty.nextId";


    @Column( name = "sup_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "sup_id_in", nullable = false )
    private Integer id;

    @ManyToOne
    @JoinColumn( name = "flt_id_in" )
    private FieldType type;


    public SystemUserProperty()
    {
    }

    public SystemUserProperty( Integer sup_id_in )
    {
        setId( sup_id_in );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String sup_description_ch )
    {
        this.description = sup_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer sup_id_in )
    {
        this.id = sup_id_in;
    }

    public void setType( FieldType type )
    {
        this.type = type;
    }

    public FieldType getType()
    {
        return type;
    }
}
