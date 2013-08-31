package br.com.mcampos.ejb.garage.manufacturer;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.garage.CarManufacturer;

@Remote
public interface CarManufacturerSession extends BaseSessionInterface<CarManufacturer>
{

}
