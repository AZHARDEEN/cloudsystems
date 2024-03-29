package br.com.mcampos.ejb.cloudsystem.client.entity;


import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = Client.getAll, query = "select o from Client o where o.company = ?1 and o.endDate is null" ),
                 @NamedQuery( name = Client.getAllCompany,
                              query = "select o from Client o where o.company = ?1 and o.client.userType.id = '2' and o.endDate is null" ),
                 @NamedQuery( name = Client.nextId, query = "select max (o.clientId) from Client o where o.company = ?1 " ),
                 @NamedQuery( name = Client.getClient,
                              query = "select o from Client o where o.company = ?1 and o.client = ?2 and o.endDate is null " ),
                 @NamedQuery( name = Client.getSponsor,
                              query = "select o from Client o where o.client = ?1 and o.endDate is null " ),
                 @NamedQuery( name = Client.getAllPerson,
                              query = "select o from Client o where o.company = ?1 and o.client.userType.id = '1' and o.endDate is null" ) } )
@Table( name = "client" )
@IdClass( ClientPK.class )
public class Client implements Serializable
{
    public static final String getAll = "Clients.findAll";
    public static final String nextId = "Clients.nextId";
    public static final String getClient = "Clients.getClient";
    public static final String getAllCompany = "Clients.findAllCompany";
    public static final String getAllPerson = "Clients.findAllPerson";
    public static final String getSponsor = "Clients.getSponsor";

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @Id
    @Column( name = "cli_seq_in", nullable = false )
    private Integer clientId;

    @Column( name = "cli_from_dt", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date insertDate;

    @Column( name = "cli_to_dt", nullable = true )
    @Temporal( TemporalType.TIMESTAMP )
    private Date endDate;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = true, updatable = true )
    private Company company;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "cli_id_in", referencedColumnName = "usr_id_in", insertable = true, updatable = true )
    private Users client;


    public Client()
    {
    }

    public Client( Company company, Users client )
    {
        setClient( client );
        setCompany( company );
        setInsertDate( new Date() );
    }


    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Date cli_from_dt )
    {
        this.insertDate = cli_from_dt;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer cli_id_in )
    {
        this.companyId = cli_id_in;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate( Date cli_to_dt )
    {
        this.endDate = cli_to_dt;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId( Integer usr_id_in )
    {
        this.clientId = usr_id_in;
    }

    public void setCompany( Company company )
    {
        this.company = company;
        if ( company != null )
            setCompanyId( company.getId() );
    }

    public Company getCompany()
    {
        return company;
    }

    public void setClient( Users client )
    {
        this.client = client;
    }

    public Users getClient()
    {
        return client;
    }
}
