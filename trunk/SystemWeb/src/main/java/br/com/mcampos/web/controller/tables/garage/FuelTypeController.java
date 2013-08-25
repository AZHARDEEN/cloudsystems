package br.com.mcampos.web.controller.tables.garage;

import br.com.mcampos.ejb.garage.entities.FuelType;
import br.com.mcampos.ejb.garage.fueltype.FuelTypeSession;
import br.com.mcampos.web.core.SimpleTableController;

public class FuelTypeController extends SimpleTableController<FuelTypeSession, FuelType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2352770523375118692L;

	@Override
	protected Class<FuelTypeSession> getSessionClass( )
	{
		return FuelTypeSession.class;
	}

}
