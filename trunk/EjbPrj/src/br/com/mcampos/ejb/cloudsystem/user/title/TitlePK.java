package br.com.mcampos.ejb.cloudsystem.user.title;


import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.cloudsystem.user.SimplePK;

import java.io.Serializable;

public class TitlePK extends SimplePK implements Serializable
{
	public TitlePK( TitleDTO dto )
	{
		setCountryId( dto.getCountryId() );
		setId( dto.getId() );
	}

}
