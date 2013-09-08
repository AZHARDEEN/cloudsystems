package br.com.mcampos.entity.user;

import java.io.Serializable;
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

import br.com.mcampos.entity.BaseCompanyEntity;

/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@Table( name = "client", schema = "public" )
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
				query = "select count(o) from Client o where o.company = ?1 and o.toDate is null and o.client.userType.id = '2'" ),
		@NamedQuery(
				name = Client.getClientFromUser,
				query = "select o from Client o where o.company = ?1 and o.toDate is null and o.client = ?2" ),
		@NamedQuery(
				name = Client.nextId,
				query = "select coalesce ( max (o.id.sequence), 0 ) + 1 from Client o where o.company = ?1 " )
} )
public class Client extends BaseCompanyEntity implements Serializable, Comparable<Client>
{
	private static final long serialVersionUID = 1L;

	public static final String getAllPerson = "Client.getAllPerson";
	public static final String countPerson = "Client.countPerson";
	public static final String getAllCompany = "Client.getAllCompany";
	public static final String countCompany = "Client.countCompany";
	public static final String nextId = "Client.nextId";
	public static final String getClientFromUser = "Client.getClientFromUser";

	@EmbeddedId
	private ClientPK id;

	@Column( name = "cli_from_dt", nullable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Date fromDate;

	@ManyToOne( optional = false )
	@JoinColumn( name = "cli_id_in", referencedColumnName = "usr_id_in", insertable = true, updatable = true, nullable = false )
	private Users client;

	@Column( name = "cli_to_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date toDate;

	public Client( )
	{

	}

	public Client( Integer companyId, Users u )
	{
		getId( ).setCompanyId( companyId );
		setClient( u );
	}

	@Override
	public ClientPK getId( )
	{
		if ( id == null ) {
			id = new ClientPK( );
		}
		return id;
	}

	public void setId( ClientPK id )
	{
		this.id = id;
	}

	public Date getFromDate( )
	{
		return fromDate;
	}

	public void setFromDate( Date cliFromDt )
	{
		fromDate = cliFromDt;
	}

	public Date getToDate( )
	{
		return toDate;
	}

	public void setToDate( Date cliToDt )
	{
		toDate = cliToDt;
	}

	public Users getClient( )
	{
		return client;
	}

	public void setClient( Users client )
	{
		this.client = client;
	}

	@Override
	public int compareTo( Client o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof Client ) {
			Client other = (Client) obj;
			return getId( ).equals( other.getId( ) );
		}
		else if ( obj instanceof ClientPK )
		{
			ClientPK other = (ClientPK) obj;
			return getId( ).equals( other );
		}
		else {
			return false;
		}
	}
}