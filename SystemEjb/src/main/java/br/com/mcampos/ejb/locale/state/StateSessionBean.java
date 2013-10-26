package br.com.mcampos.ejb.locale.state;

import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.locale.State;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class StateSessionBean
 */
@Stateless( name = "StateSession", mappedName = "StateSession" )
@LocalBean
public class StateSessionBean extends SimpleSessionBean<State> implements StateSession, StateSessionLocal
{

	@Override
	protected Class<State> getEntityClass( )
	{
		return State.class;
	}

	@Override
	public List<State> getAll( String countryCode )
	{
		if ( SysUtils.isEmpty( countryCode ) ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( State.getCountryStates, countryCode );
	}

}
