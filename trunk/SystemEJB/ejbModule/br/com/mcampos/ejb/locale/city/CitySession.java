package br.com.mcampos.ejb.locale.city;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.locale.state.State;

@Remote
public interface CitySession extends BaseSessionInterface<City>
{
	public List<City> getAll( State state );

}