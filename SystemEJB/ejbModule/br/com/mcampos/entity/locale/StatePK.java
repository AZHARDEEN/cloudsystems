package br.com.mcampos.entity.locale;

import java.io.Serializable;

public class StatePK implements Serializable
{
	private static final long serialVersionUID = 7824208857913973416L;
	private String countryId;
	private Integer regionId;
	private Integer id;

	public StatePK()
	{
	}

	public StatePK( String ctr_code_ch, Integer reg_id_in, Integer sta_id_in )
	{
		this.countryId = ctr_code_ch;
		this.regionId = reg_id_in;
		this.id = sta_id_in;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof StatePK ) {
			final StatePK otherStatePK = ( StatePK ) other;
			final boolean areEqual = ( otherStatePK.countryId.equals( this.countryId ) && otherStatePK.regionId.equals( this.regionId ) && otherStatePK.id.equals( this.id ) );
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

	public Integer getRegionId()
	{
		return this.regionId;
	}

	public void setRegionId( Integer reg_id_in )
	{
		this.regionId = reg_id_in;
	}

	public Integer getId()
	{
		return this.id;
	}

	public void setId( Integer sta_id_in )
	{
		this.id = sta_id_in;
	}
}
