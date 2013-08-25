package br.com.mcampos.web.controller.tables.garage;

import br.com.mcampos.ejb.garage.entities.CarManufacturer;
import br.com.mcampos.ejb.garage.manufacturer.CarManufacturerSession;
import br.com.mcampos.web.core.SimpleTableController;

public class CarManufacturerController extends SimpleTableController<CarManufacturerSession, CarManufacturer>
{
	private static final long serialVersionUID = -3782616898081685383L;

	@Override
	protected Class<CarManufacturerSession> getSessionClass( )
	{
		return CarManufacturerSession.class;
	}

}
