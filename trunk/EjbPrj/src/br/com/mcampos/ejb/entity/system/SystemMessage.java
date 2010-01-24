package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "SystemMessage.findAll", query = "select o from SystemMessage o")
})
@Table( name = "\"system_message\"" )
@IdClass( SystemMessagePK.class )
public class SystemMessage implements Serializable
{
    private Integer id;
    private String message;
    private Integer messageTypeID;

    public SystemMessage()
    {
    }

    public SystemMessage( Integer typeId, Integer id, String message  )
    {
        setId ( id );
        setMessage ( message );
        setMessageTypeID( typeId );
    }

    @Id
    @Column( name="smg_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    @Column( name="smg_message_ch", nullable = false )
    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    @Id
    @Column( name="smt_id_in", nullable = false )
    public Integer getMessageTypeID()
    {
        return messageTypeID;
    }

    public void setMessageTypeID( Integer typeId )
    {
        this.messageTypeID = typeId;
    }
}
