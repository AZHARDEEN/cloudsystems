package br.com.mcampos.ejb.cloudsystem.account.nature.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = AccountingNature.getAll, query = "select o from AccountingNature o order by o.id" ) } )
@Table( name = "accounting_nature" )
public class AccountingNature implements Serializable
{
    public static final String getAll = "AccountingNature.findAll";

    @Column( name = "acn_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "acn_id_in", nullable = false, unique = true, columnDefinition = "CHAR", length = 1 )
    private String id;

    public AccountingNature()
    {
    }

    public AccountingNature( String acn_id_in )
    {
        setId( acn_id_in );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String acn_description_ch )
    {
        this.description = acn_description_ch;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String acn_id_in )
    {
        this.id = acn_id_in;
    }
}
