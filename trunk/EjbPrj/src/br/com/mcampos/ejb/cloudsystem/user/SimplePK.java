package br.com.mcampos.ejb.cloudsystem.user;


import java.io.Serializable;

public abstract class SimplePK implements Serializable
{
	private String countryId;
	private Integer id;

	public SimplePK()
	{
	}

	public SimplePK( String countryId, Integer ttl_id_in )
	{
		this.countryId = countryId;
		this.id = ttl_id_in;
	}

	public boolean equals( Object other )
	{
		if ( other instanceof SimplePK ) {
			final SimplePK otherTitlePK = ( SimplePK )other;
			final boolean areEqual = ( otherTitlePK.countryId.equals( countryId ) && otherTitlePK.id.equals( id ) );
			return areEqual;
		}
		return false;
	}

	public int hashCode()
	{
		return super.hashCode();
	}

	protected String getCountryId()
	{
		return countryId;
	}

	protected void setCountryId( String ctr_code_ch )
	{
		this.countryId = ctr_code_ch;
	}

	protected Integer getId()
	{
		return id;
	}

	protected void setId( Integer ttl_id_in )
	{
		this.id = ttl_id_in;
	}
}
