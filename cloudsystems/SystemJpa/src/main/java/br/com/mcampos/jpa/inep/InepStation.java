package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.user.Client;

/**
 * The persistent class for the inep_station database table.
 *
 */
@Entity
@Table( name = "inep_station", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = "InepStation.findAll", query = "SELECT i FROM InepStation i" ),
		@NamedQuery( name = InepStation.getAllEventStations, query = "SELECT i FROM InepStation i where i.event = ?1" )
} )
public class InepStation extends BaseInepEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getAllEventStations = "InepStation.getAllEventStations";

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
		if ( id == null ) {
			id = new InepStationPK( );
		}
		return id;
	}

	public void setId( InepStationPK id )
	{
		this.id = id;
	}

	public Client getClient( )
	{
		return client;
	}

	public void setClient( Client client )
	{
		this.client = client;
		if ( client != null ) {
			setCompany( client.getCompany( ) );
			getId( ).setClientId( client.getId( ).getSequence( ) );
		}
		else {
			getId( ).setCompanyId( null );
			getId( ).setClientId( null );
		}

	}
}