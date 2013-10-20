package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import java.security.InvalidParameterException;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.Collaborator;

/**
 * The persistent class for the inep_station_reponsable database table.
 * 
 */
@Entity
@Table( name = "inep_station_reponsable", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = "InepStationReponsable.findAll", query = "SELECT i FROM InepStationReponsable i" ),
		@NamedQuery(
				name = InepStationReponsable.GET_ALL_FROM_COLLABORATOR,
				query = "SELECT i FROM InepStationReponsable i WHERE i.responsable = ?1 and i.event = ?2" )
} )
public class InepStationReponsable extends BaseInepEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String GET_ALL_FROM_COLLABORATOR = "InepStationReponsable.getAllFromCollaborator";

	@EmbeddedId
	private InepStationReponsablePK id;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn( name = "cli_seq_in", referencedColumnName = "cli_seq_in", nullable = false, insertable = false, updatable = false )
	} )
	private Client station;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn( name = "col_seq_in", referencedColumnName = "col_seq_in", nullable = false, insertable = false, updatable = false )
	} )
	private Collaborator responsable;

	public InepStationReponsable( )
	{
	}

	public InepStationReponsable( InepEvent event, Client station, Collaborator responsable )
	{
		if ( !event.getId( ).getCompanyId( ).equals( station.getId( ).getCompanyId( ) )
				|| !event.getId( ).getCompanyId( ).equals( responsable.getId( ).getCompanyId( ) ) ) {
			throw new InvalidParameterException( "Event Company is not equal station company or responsable company" );
		}
		this.getId( ).set( event );
		this.getId( ).setStationId( station.getId( ).getSequence( ) );
		this.getId( ).setResponsableId( responsable.getId( ).getSequence( ) );
	}

	@Override
	public InepStationReponsablePK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepStationReponsablePK( );
		}
		return this.id;
	}

	public void setId( InepStationReponsablePK id )
	{
		this.id = id;
	}

	public Client getStation( )
	{
		return this.station;
	}

	public void setStation( Client station )
	{
		this.station = station;
		if ( station != null ) {
			this.getId( ).setCompanyId( station.getCompanyId( ) );
			this.getId( ).setStationId( station.getId( ).getSequence( ) );
		}
		else {
			this.getId( ).setCompanyId( null );
			this.getId( ).setStationId( null );
		}
	}

	public Collaborator getResponsable( )
	{
		return this.responsable;
	}

	public void setResponsable( Collaborator responsable )
	{
		this.responsable = responsable;
		if ( responsable != null ) {
			this.getId( ).setCompanyId( responsable.getId( ).getCompanyId( ) );
			this.getId( ).setResponsableId( responsable.getId( ).getSequence( ) );
		}
		else {
			this.getId( ).setCompanyId( null );
			this.getId( ).setResponsableId( null );
		}
	}
}