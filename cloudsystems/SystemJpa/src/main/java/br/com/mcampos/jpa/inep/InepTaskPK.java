package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_task database table.
 * 
 */
@Embeddable
public class InepTaskPK extends BaseInepEventPK implements Serializable, Comparable<InepTaskPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "tsk_id_in" )
	private Integer id;

	public InepTaskPK( )
	{
	}

	public InepTaskPK( InepEvent e, int id )
	{
		this.setCompanyId( e.getCompany( ).getId( ) );
		this.setEventId( e.getId( ).getId( ) );
		this.setId( id );
	}

	public void set( InepEventPK key )
	{
		this.setCompanyId( key.getCompanyId( ) );
		this.setEventId( key.getId( ) );
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer taskId )
	{
		this.id = taskId;
	}

	@Override
	public boolean equals( Object other )
	{
		if( this == other ) {
			return true;
		}
		if( !(other instanceof InepTaskPK) ) {
			return false;
		}
		InepTaskPK castOther = (InepTaskPK) other;
		return super.equals( other ) && this.id.equals( castOther.id );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + super.hashCode( );
		hash = hash * prime + this.id.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( InepTaskPK o )
	{
		int nRet;

		nRet = super.compareTo( o );
		if( nRet == 0 ) {
			nRet = this.getId( ).compareTo( o.getId( ) );
		}
		return nRet;
	}
}