package br.com.mcampos.ejb.garage.fueltype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.garage.FuelType;

@Remote
public interface FuelTypeSession extends BaseCrudSessionInterface<FuelType>
{

}
