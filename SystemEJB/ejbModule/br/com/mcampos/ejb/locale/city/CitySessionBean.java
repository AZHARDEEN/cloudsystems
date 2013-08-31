package br.com.mcampos.ejb.locale.city;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.locale.City;
import br.com.mcampos.entity.locale.State;

/**
 * Session Bean implementation class CitySessionBean
 */
@Stateless( name = "CitySession", mappedName = "CitySession" )
@LocalBean
public class CitySessionBean extends SimpleSessionBean<City> implements CitySession, CitySessionLocal
{

	@Override
	protected Class<City> getEntityClass( )
	{
		return City.class;
	}

	@Override
	public List<City> getAll( State state )
	{
		return findByNamedQuery( City.getAllByState, state );
	}

}
