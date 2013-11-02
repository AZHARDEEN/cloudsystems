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

	@Column( name = "cli_seq_in", insertable = true, updatable = true, nullable = false )
	private Integer stationId;

	@Column( name = "col_seq_in", insertable = true, updatable = true, nullable = false )
	private Integer responsableId;

	public InepStationReponsablePK( )
	{
	}

	public InepStationReponsablePK( InepEvent event, Client station, Collaborator resonsable )
	{
		set( event );
		setStationId( station.getId( ).getSequence( ) );
		setResponsableId( resonsable.getId( ).getSequence( ) );
	}

	public Integer getStationId( )
	{
		return stationId;
	}

	public void setStationId( Integer cliSeqIn )
	{
		stationId = cliSeqIn;
	}

	public Integer getResponsableId( )
	{
		return responsableId;
	}

	public void setResponsableId( Integer cliSeq2In )
	{
		responsableId = cliSeq2In;
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
		return super.equals( castOther ) && stationId.equals( castOther.stationId ) && responsableId.equals( castOther.responsableId );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + super.hashCode( );
		hash = hash * prime + stationId.hashCode( );
		hash = hash * prime + responsableId.hashCode( );

		return hash;
	}
}