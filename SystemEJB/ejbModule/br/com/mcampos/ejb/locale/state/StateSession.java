package br.com.mcampos.ejb.locale.state;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.locale.State;

@Remote
public interface StateSession extends BaseCrudSessionInterface<State>
{
	public List<State> getAll( String countryCode );

}
