package br.com.mcampos.web.core.combobox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.user.person.civilstate.CivilStateSession;
import br.com.mcampos.entity.user.CivilState;

public class CivilStateCombobox extends ComboboxExt<CivilStateSession, CivilState>
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger( CivilStateCombobox.class );

	@Override
	protected Class<CivilStateSession> getSessionClass( )
	{
		return CivilStateSession.class;
	}

	@Override
	public void load( )
	{
		logger.info( "load" );
		load( (List<CivilState>) getSession( ).getAll( ), null, true );
	}

}
