package br.com.mcampos.ejb.garage.fueltype;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.garage.FuelType;

@Local
public interface FuelTypeSessionLocal extends BaseSessionInterface<FuelType>
{

}
