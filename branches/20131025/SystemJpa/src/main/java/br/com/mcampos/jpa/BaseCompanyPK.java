package br.com.mcampos.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseCompanyPK implements Serializable
{
	private static final long serialVersionUID = 7171207060157995783L;

	@Column( name = "usr_id_in", unique = true, nullable = false, updatable = true, insertable = true, columnDefinition = "Integer not null" )
	private Integer companyId;

	public BaseCompanyPK( )
	{

	}

	public BaseCompanyPK( Integer companyId )
	{
		this.setCompanyId( companyId );
	}

	public Integer getCompanyId( )
	{
		return this.companyId;
	}

	public void setCompanyId( Integer companyId )
	{
		this.companyId = companyId;
	}

	public int compareTo( BaseCompanyPK o )
	{
		if ( o != null ) {
			return this.getCompanyId( ).compareTo( o.getCompanyId( ) );
		}
		return -1;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null || ( obj instanceof BaseCompanyPK ) == false ) {
			return false;
		}
		BaseCompanyPK other = (BaseCompanyPK) obj;
		return this.getCompanyId( ).equals( other.getCompanyId( ) );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getCompanyId( ).hashCode( );
		return hash;
	}

}
