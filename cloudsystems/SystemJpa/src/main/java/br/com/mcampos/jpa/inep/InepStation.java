package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.user.Client;

/**
 * The persistent class for the inep_station database table.
 * 
 */
@Entity
@Table( name = "inep_station", schema = "inep" )
@NamedQuery( name = "InepStation.findAll", query = "SELECT i FROM InepStation i" )
public class InepStation extends BaseInepEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InepStationPK id;

	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false, nullable = false ),
			@JoinColumn( name = "cli_seq_in", referencedColumnName = "cli_seq_in", insertable = false, updatable = false, nullable = false )
	} )
	private Client client;

	public InepStation( )
	{
	}

	@Override
	public InepStationPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepStationPK( );
		}
		return this.id;
	}

	public void setId( InepStationPK id )
	{
		this.id = id;
	}

	public Client getClient( )
	{
		return this.client;
	}

	public void setClient( Client client )
	{
		this.client = client;
		if ( client != null ) {
			this.setCompany( client.getCompany( ) );
			this.getId( ).setClientId( client.getId( ).getSequence( ) );
		}
		else {
			this.getId( ).setCompanyId( null );
			this.getId( ).setClientId( null );
		}

	}
}