package br.com.mcampos.ejb.garage.fueltype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.garage.FuelType;

@Remote
public interface FuelTypeSession extends BaseSessionInterface<FuelType>
{

}
