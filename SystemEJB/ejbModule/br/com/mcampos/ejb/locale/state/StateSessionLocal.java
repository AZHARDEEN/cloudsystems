package br.com.mcampos.ejb.locale.state;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface StateSessionLocal extends BaseSessionInterface<State>
{
	public List<State> getAll( String countryCode );
}
