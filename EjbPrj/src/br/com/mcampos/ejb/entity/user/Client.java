package br.com.mcampos.ejb.entity.user;

import br.com.mcampos.ejb.entity.user.pk.ClientsPK;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "Clients.findAll", 
              query = "select o from Client o "),
  @NamedQuery(name = "Clients.findAllActive", 
              query = "select o from Client o where o.owner.id = :owner and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )"),
  @NamedQuery(name = "Clients.find", 
              query = "select o from Client o where o.owner.id = :owner and o.client.id = :client and ( o.toDate is null or o.toDate >= CURRENT_TIMESTAMP )"),
  @NamedQuery(name = "Clients.countActive", 
              query = "select count(o) from Client o where o.owner.id = :owner and ( o.toDate is null and o.toDate >= CURRENT_TIMESTAMP ) ")
})
@Table( name = "\"clients\"" )
@IdClass( ClientsPK.class )
public class Client implements Serializable
{
    private Integer userId;
    private Integer clientId;
    private Timestamp fromDate;
    
    private Timestamp toDate;
    
    private Users client;
    private Users owner;



    public Client()
    {
    }


    @Id
    @Column( name="cli_from_dt", nullable = false )
    public Timestamp getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Timestamp fromDate )
    {
        this.fromDate = fromDate;
    }

    @Id
    @Column( name="cli_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId( Integer id )
    {
        this.clientId = id;
    }

    @Column( name="cli_to_dt" )
    public Timestamp getToDate()
    {
        return toDate;
    }

    public void setToDate( Timestamp toDate )
    {
        this.toDate = toDate;
    }

    @Id
    @Column( name="usr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer id )
    {
        this.userId = id;
    }

    public void setClient( Users client )
    {
        this.client = client;
        setClientId( getClient() != null ? getClient().getId(): null );
    }

    @ManyToOne
    @JoinColumn( name = "cli_id_in", referencedColumnName = "usr_id_in", nullable = false )
    public Users getClient()
    {
        return client;
    }

    public void setOwner( Users owner )
    {
        this.owner = owner;
        setUserId( getOwner() != null ? getOwner().getId(): null );
    }

    @ManyToOne
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false )
    public Users getOwner()
    {
        return owner;
    }
}
