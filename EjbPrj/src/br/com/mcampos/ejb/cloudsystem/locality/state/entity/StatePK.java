package br.com.mcampos.ejb.cloudsystem.locality.state.entity;


import br.com.mcampos.ejb.cloudsystem.user.SimplePK;

import java.io.Serializable;

public class StatePK extends SimplePK implements Serializable
{
	private Integer regionId;

	public StatePK()
	{
	}

	public StatePK( String countryId, Integer regionId, Integer id )
	{
		setCountryId( countryId );
		setRegionId( regionId );
		setId( id );
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof StatePK ) {
			final StatePK otherStatePK = ( StatePK )other;
			final boolean areEqual =
						 ( otherStatePK.getCountryId().equals( getCountryId() ) && otherStatePK.regionId.equals( regionId ) && otherStatePK.getId().equals( getId() ) );
			return areEqual;
		}
		return false;
	}

	Integer getRegionId()
	{
		return regionId;
	}

	void setRegionId( Integer reg_id_in )
	{
		this.regionId = reg_id_in;
	}

}
