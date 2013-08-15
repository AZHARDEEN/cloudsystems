package br.com.mcampos.ejb.locale.state;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface StateSession extends BaseSessionInterface<State>
{
	public List<State> getAll( String countryCode );

}
