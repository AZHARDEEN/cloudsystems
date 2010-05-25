package br.com.mcampos.ejb.entity.address;


import br.com.mcampos.ejb.cloudsystem.user.SimplePK;

import java.io.Serializable;

public class RegionPK extends SimplePK implements Serializable
{
	public RegionPK( String countryId, Integer ttl_id_in )
	{
		super( countryId, ttl_id_in );
	}

	public RegionPK()
	{
	}

}
