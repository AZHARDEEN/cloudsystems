package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "SystemMessageType.findAll", query = "select o from SystemMessageType o")
})
@Table( name = "\"system_message_type\"" )
public class SystemMessageType implements Serializable
{
    private String id;
    private String description;
    private List<SystemMessage> systemMessageList;

    public SystemMessageType()
    {
    }

    @Column( name="smt_description_ch" )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String smt_description_ch )
    {
        this.description = smt_description_ch;
    }

    @Id
    @Column( name="smt_id_in", nullable = false )
    public String getId()
    {
        return id;
    }

    public void setId( String smt_id_in )
    {
        this.id = smt_id_in;
    }

    @OneToMany( mappedBy = "systemMessageType" )
    public List<SystemMessage> getSystemMessageList()
    {
        return systemMessageList;
    }

    public void setSystemMessageList( List<SystemMessage> systemMessageList )
    {
        this.systemMessageList = systemMessageList;
    }

    public SystemMessage addSystemMessage( SystemMessage systemMessage )
    {
        getSystemMessageList().add(systemMessage);
        systemMessage.setSystemMessageType(this);
        return systemMessage;
    }

    public SystemMessage removeSystemMessage( SystemMessage systemMessage )
    {
        getSystemMessageList().remove(systemMessage);
        systemMessage.setSystemMessageType(null);
        return systemMessage;
    }
}
