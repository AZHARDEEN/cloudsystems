package br.com.mcampos.ejb.locale;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.locale.state.State;

@Local
public interface CitySessionLocal extends BaseSessionInterface<City>
{
	public List<City> getAll( State state );

}
