package br.com.mcampos.ejb.garage.manufacturer;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.garage.CarManufacturer;

@Local
public interface CarManufacturerSessionLocal extends BaseCrudSessionInterface<CarManufacturer>
{

}
