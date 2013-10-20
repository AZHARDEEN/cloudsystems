package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.Collaborator;

/**
 * The primary key class for the inep_station_reponsable database table.
 * 
 */
@Embeddable
public class InepStationReponsablePK extends BaseInepEventPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "cli_seq_in", insertable = false, updatable = false, unique = true, nullable = false )
	private Integer stationId;

	@Column( name = "col_seq_in", insertable = false, updatable = false, unique = true, nullable = false )
	private Integer responsableId;

	public InepStationReponsablePK( )
	{
	}

	public InepStationReponsablePK( InepEvent event, Client station, Collaborator resonsable )
	{
		this.set( event );
		this.setStationId( station.getId( ).getSequence( ) );
		this.setResponsableId( resonsable.getId( ).getSequence( ) );
	}

	public Integer getStationId( )
	{
		return this.stationId;
	}

	public void setStationId( Integer cliSeqIn )
	{
		this.stationId = cliSeqIn;
	}

	public Integer getResponsableId( )
	{
		return this.responsableId;
	}

	public void setResponsableId( Integer cliSeq2In )
	{
		this.responsableId = cliSeq2In;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepStationReponsablePK ) ) {
			return false;
		}
		InepStationReponsablePK castOther = (InepStationReponsablePK) other;
		return super.equals( castOther ) && this.stationId.equals( castOther.stationId ) && this.responsableId.equals( castOther.responsableId );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + super.hashCode( );
		hash = hash * prime + this.stationId.hashCode( );
		hash = hash * prime + this.responsableId.hashCode( );

		return hash;
	}
}