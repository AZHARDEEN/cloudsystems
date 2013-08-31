package br.com.mcampos.web.core.combobox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.user.person.gender.GenderSession;
import br.com.mcampos.entity.user.Gender;

public class GenderCombobox extends ComboboxExt<GenderSession, Gender>
{
	private static final Logger logger = LoggerFactory.getLogger( GenderCombobox.class );
	private static final long serialVersionUID = -7706250007578273124L;

	@Override
	protected Class<GenderSession> getSessionClass( )
	{
		return GenderSession.class;
	}

	@Override
	public void load( )
	{
		logger.info( "load" );
		load( ( (List<Gender>) getSession( ).getAll( ) ), null, true );
	}
}
