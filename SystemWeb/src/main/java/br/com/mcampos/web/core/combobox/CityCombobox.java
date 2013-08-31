package br.com.mcampos.web.core.combobox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.locale.city.CitySession;
import br.com.mcampos.entity.locale.City;
import br.com.mcampos.entity.locale.State;

public class CityCombobox extends ComboboxExt<CitySession, City> implements DetailInterface
{
	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( CityCombobox.class );
	private static final long serialVersionUID = 1180477829375738059L;

	@Override
	public void onChangeMaster( Object master )
	{
		load( ( (State) master ) );
	}

	public void load( State state )
	{
		load( getSession( ).getAll( state ), null, true );
	}

	@Override
	protected Class<CitySession> getSessionClass( )
	{
		return CitySession.class;
	}

	@Override
	protected void load( )
	{
	}

}
