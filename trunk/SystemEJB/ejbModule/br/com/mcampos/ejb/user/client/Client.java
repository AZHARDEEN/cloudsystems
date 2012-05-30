package br.com.mcampos.ejb.user.client;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.company.Company;

/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@Table( name = "client" )
@NamedQueries( {
		@NamedQuery(
				name = Client.getAllPerson,
				query = "select o from Client o where o.company = ?1 and o.toDate is null and o.client.userType.id = '1' order by o.client.name" ),
		@NamedQuery(
				name = Client.countPerson,
				query = "select count(o) from Client o where o.company = ?1 and o.toDate is null and o.client.userType.id = '1'" ),
		@NamedQuery(
				name = Client.getAllCompany,
				query = "select o from Client o where o.company = ?1 and o.toDate is null and o.client.userType.id = '2' order by o.client.name" ),
		@NamedQuery(
				name = Client.countCompany,
				query = "select count(o) from Client o where o.company = ?1 and o.toDate is null and o.client.userType.id = '2'" )
} )
public class Client implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getAllPerson = "Client.getAllPerson";
	public static final String countPerson = "Client.countPerson";
	public static final String getAllCompany = "Client.getAllCompany";
	public static final String countCompany = "Client.countCompany";

	@EmbeddedId
	private ClientPK id;

	@Column( name = "cli_from_dt", nullable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Date fromDate;

	@ManyToOne( optional = false )
	@JoinColumn( name = "cli_id_in", referencedColumnName = "usr_id_in", insertable = true, updatable = true, nullable = false )
	private Users client;

	@ManyToOne( optional = false )
	@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false, nullable = false )
	private Company company;

	@Column( name = "cli_to_dt" )
	private Timestamp toDate;

	public Client( )
	{
	}

	public ClientPK getId( )
	{
		return this.id;
	}

	public void setId( ClientPK id )
	{
		this.id = id;
	}

	public Date getFromDate( )
	{
		return this.fromDate;
	}

	public void setFromDate( Date cliFromDt )
	{
		this.fromDate = cliFromDt;
	}

	public Timestamp getToDate( )
	{
		return this.toDate;
	}

	public void setToDate( Timestamp cliToDt )
	{
		this.toDate = cliToDt;
	}

	public Users getClient( )
	{
		return this.client;
	}

	public void setClient( Users client )
	{
		this.client = client;
	}

	public Company getCompany( )
	{
		return this.company;
	}

	public void setCompany( Company company )
	{
		this.company = company;
	}

}