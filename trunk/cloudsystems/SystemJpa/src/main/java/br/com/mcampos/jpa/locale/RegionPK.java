package br.com.mcampos.jpa.locale;

import java.io.Serializable;

public class RegionPK implements Serializable
{
	private static final long serialVersionUID = -5173394130371907731L;
	private String countryId;
	private Integer id;

	public RegionPK()
	{
	}

	public RegionPK( String ctr_code_ch, Integer reg_id_in )
	{
		this.countryId = ctr_code_ch;
		this.id = reg_id_in;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof RegionPK ) {
			final RegionPK otherRegionPK = ( RegionPK ) other;
			final boolean areEqual = ( otherRegionPK.countryId.equals( this.countryId ) && otherRegionPK.id.equals( this.id ) );
			return areEqual;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	public String getCountryId()
	{
		return this.countryId;
	}

	public void setCountryId( String ctr_code_ch )
	{
		this.countryId = ctr_code_ch;
	}

	public Integer getId()
	{
		return this.id;
	}

	public void setId( Integer reg_id_in )
	{
		this.id = reg_id_in;
	}
}
