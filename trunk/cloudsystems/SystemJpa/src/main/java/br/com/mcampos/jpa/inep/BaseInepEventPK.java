package br.com.mcampos.jpa.inep;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import br.com.mcampos.jpa.BaseCompanyPK;

@MappedSuperclass
public abstract class BaseInepEventPK extends BaseCompanyPK
{
	private static final long serialVersionUID = 4309904096061001946L;

	@Column( name = "pct_id_in", nullable = false, updatable = true, insertable = true, columnDefinition = "Integer not null" )
	private Integer eventId;

	public BaseInepEventPK( )
	{
		super( );
	}

	public BaseInepEventPK( Integer companyId, Integer eventId )
	{
		super( companyId );
		this.setEventId( eventId );
	}

	public BaseInepEventPK( InepEvent inepEvent )
	{
		this.set( inepEvent );
	}

	public void set( InepEvent inepEvent )
	{
		this.setCompanyId( inepEvent.getId( ).getCompanyId( ) );
		this.setEventId( inepEvent.getId( ).getId( ) );
	}

	public Integer getEventId( )
	{
		return this.eventId;
	}

	public void setEventId( Integer eventId )
	{
		this.eventId = eventId;
	}

	public int compareTo( BaseInepEventPK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = this.getEventId( ).compareTo( o.getEventId( ) );
		}
		return nRet;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null || !( obj instanceof BaseInepEventPK ) ) {
			return false;
		}
		BaseInepEventPK other = (BaseInepEventPK) obj;
		return super.equals( obj ) && this.getEventId( ).equals( other.getEventId( ) );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = super.hashCode( );
		hash = hash * prime + this.getEventId( ).hashCode( );
		return hash;
	}

}
