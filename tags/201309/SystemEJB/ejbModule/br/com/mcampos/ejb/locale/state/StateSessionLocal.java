package br.com.mcampos.ejb.locale.state;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.locale.State;

@Local
public interface StateSessionLocal extends BaseCrudSessionInterface<State>
{
	public List<State> getAll( String countryCode );
}
