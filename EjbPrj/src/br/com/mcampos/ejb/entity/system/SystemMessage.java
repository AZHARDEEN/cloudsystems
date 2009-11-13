package br.com.mcampos.ejb.entity.system;

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
@NamedQueries({
  @NamedQuery(name = "SystemMessage.findAll", query = "select o from SystemMessage o")
})
@Table( name = "\"system_message\"" )
public class SystemMessage implements Serializable
{
    private Integer id;
    private String message;
    private SystemMessageType systemMessageType;

    public SystemMessage()
    {
    }

    @Id
    @Column( name="smg_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer smg_ig_in )
    {
        this.id = smg_ig_in;
    }

    @Column( name="smg_message_ch", nullable = false )
    public String getMessage()
    {
        return message;
    }

    public void setMessage( String smg_message_ch )
    {
        this.message = smg_message_ch;
    }


    @ManyToOne
    @JoinColumn( name = "smt_id_in" )
    public SystemMessageType getSystemMessageType()
    {
        return systemMessageType;
    }

    public void setSystemMessageType( SystemMessageType systemMessageType )
    {
        this.systemMessageType = systemMessageType;
    }
}
