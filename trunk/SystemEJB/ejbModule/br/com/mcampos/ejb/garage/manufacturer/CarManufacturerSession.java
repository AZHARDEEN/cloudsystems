package br.com.mcampos.ejb.garage.manufacturer;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.garage.CarManufacturer;

@Remote
public interface CarManufacturerSession extends BaseCrudSessionInterface<CarManufacturer>
{

}
