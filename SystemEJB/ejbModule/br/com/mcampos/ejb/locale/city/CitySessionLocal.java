package br.com.mcampos.ejb.locale.city;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.locale.City;
import br.com.mcampos.entity.locale.State;

@Local
public interface CitySessionLocal extends BaseCrudSessionInterface<City>
{
	public List<City> getAll( State state );

}
