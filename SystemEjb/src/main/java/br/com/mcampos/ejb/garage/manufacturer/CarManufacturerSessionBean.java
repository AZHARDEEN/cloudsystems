package br.com.mcampos.ejb.garage.manufacturer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.garage.CarManufacturer;

/**
 * Session Bean implementation class CarManufacturerSessionBean
 */
@Stateless( mappedName = "CarManufacturerSession", name = "CarManufacturerSession" )
@LocalBean
public class CarManufacturerSessionBean extends SimpleSessionBean<CarManufacturer> implements CarManufacturerSession, CarManufacturerSessionLocal
{

	@Override
	protected Class<CarManufacturer> getEntityClass( )
	{
		return CarManufacturer.class;
	}

}
