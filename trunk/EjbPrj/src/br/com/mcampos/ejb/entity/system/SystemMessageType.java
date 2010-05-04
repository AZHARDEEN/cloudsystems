package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "SystemMessageType.findAll", query = "select o from SystemMessageType o" ) } )
@Table( name = "system_message_type" )
public class SystemMessageType implements Serializable
{
    @Column( name = "smt_description_ch" )
    private String description;
    @Id
    @Column( name = "smt_id_in", nullable = false )
    private Integer id;

    public SystemMessageType()
    {
    }

    public SystemMessageType( Integer id, String desc )
    {
        setDescription( desc );
        setId( id );
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
