package br.com.mcampos.ejb.garage.fueltype;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.garage.entities.FuelType;

/**
 * Session Bean implementation class FuelTypeSessionBean
 */
@Stateless( mappedName = "FuelTypeSession", name = "FuelTypeSession" )
@LocalBean
public class FuelTypeSessionBean extends SimpleSessionBean<FuelType> implements FuelTypeSession, FuelTypeSessionLocal
{
	@Override
	protected Class<FuelType> getEntityClass( )
	{
		return FuelType.class;
	}

}
