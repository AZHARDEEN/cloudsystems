package br.com.mcampos.ejb.garage.manufacturer;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.garage.CarManufacturer;

@Local
public interface CarManufacturerSessionLocal extends BaseSessionInterface<CarManufacturer>
{

}
