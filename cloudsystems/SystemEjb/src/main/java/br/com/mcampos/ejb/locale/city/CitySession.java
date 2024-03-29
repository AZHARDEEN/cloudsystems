package br.com.mcampos.ejb.locale.city;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.locale.City;
import br.com.mcampos.jpa.locale.State;

@Remote
public interface CitySession extends BaseCrudSessionInterface<City>
{
	public List<City> getAll( State state );

}
