package br.com.mcampos.ejb.cloudsystem.account.event.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = AccountingRateType.getAll, query = "select o from AccountingRateType o order by o.id" ),
                 @NamedQuery( name = AccountingRateType.nextId, query = "select max (o.id) from AccountingRateType o" ) } )
@Table( name = "account_rate_type" )
public class AccountingRateType implements Serializable
{
    public static final String getAll = "AccountingRateType.findAll";
    public static final String nextId = "AccountingRateType.nextId";


    @Id
    @Column( name = "act_id_in", nullable = false )
    private Integer id;

    @Column( name = "act_description_ch", nullable = false )
    private String description;


    public AccountingRateType()
    {
        super();
    }

    public AccountingRateType( Integer id )
    {
        super();
        setId( id );
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
