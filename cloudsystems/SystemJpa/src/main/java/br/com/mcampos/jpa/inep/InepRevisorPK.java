package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.jpa.user.Collaborator;

/**
 * The primary key class for the inep_revisor database table.
 * 
 */
@Embeddable
public class InepRevisorPK implements Serializable, Comparable<InepRevisorPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in" )
	private Integer companyId;

	@Column( name = "col_seq_in" )
	private Integer sequence;

	@Column( name = "pct_id_in" )
	private Integer eventId;

	public InepRevisorPK( )
	{
	}

	public InepRevisorPK( Collaborator c, InepEvent e )
	{
		this.set( c );
		this.set( e );
	}

	public void set( Collaborator c )
	{
		this.setCompanyId( c.getId( ).getCompanyId( ) );
		this.setSequence( c.getId( ).getSequence( ) );
	}

	public void set( InepTask c )
	{
		this.setCompanyId( c.getId( ).getCompanyId( ) );
		this.setEventId( c.getId( ).getEventId( ) );
	}

	public void set( InepEvent c )
	{
		this.setCompanyId( c.getId( ).getCompanyId( ) );
		this.setEventId( c.getId( ).getId( ) );
	}

	public Integer getCompanyId( )
	{
		return this.companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		this.companyId = usrIdIn;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer colSeqIn )
	{
		this.sequence = colSeqIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepRevisorPK ) ) {
			return false;
		}
		InepRevisorPK castOther = (InepRevisorPK) other;
		return this.getCompanyId( ).equals( castOther.getCompanyId( ) ) && this.getSequence( ).equals( castOther.getSequence( ) )
				&& this.getEventId( ).equals( castOther.getEventId( ) );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getCompanyId( ).hashCode( );
		hash = hash * prime + this.getSequence( ).hashCode( );
		hash = hash * prime + this.getEventId( ).hashCode( );

		return hash;
	}

	public Integer getEventId( )
	{
		return this.eventId;
	}

	public void setEventId( Integer eventId )
	{
		this.eventId = eventId;
	}

	@Override
	public int compareTo( InepRevisorPK o )
	{
		int nRet;

		nRet = this.getCompanyId( ).compareTo( o.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = this.getEventId( ).compareTo( o.getEventId( ) );
			if ( nRet == 0 ) {
				nRet = this.getSequence( ).compareTo( o.getSequence( ) );
			}
		}
		return nRet;
	}
}