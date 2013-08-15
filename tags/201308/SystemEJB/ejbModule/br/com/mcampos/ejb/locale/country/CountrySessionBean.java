package br.com.mcampos.ejb.locale.country;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class CountrySessionBean
 */
@Stateless( name = "CountrySession" )
public class CountrySessionBean extends SimpleSessionBean<Country> implements CountrySession,
		CountrySessionBeanLocal
{

	@Override
	protected Class<Country> getEntityClass( )
	{
		return Country.class;
	}

}
