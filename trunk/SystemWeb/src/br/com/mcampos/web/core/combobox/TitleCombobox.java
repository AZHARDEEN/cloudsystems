package br.com.mcampos.web.core.combobox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.user.person.gender.Gender;
import br.com.mcampos.ejb.user.person.title.Title;
import br.com.mcampos.ejb.user.person.title.TitleSession;

public class TitleCombobox extends ComboboxExt<TitleSession, Title> implements DetailInterface
{
	private static final Logger logger = LoggerFactory.getLogger( TitleCombobox.class );
	private static final long serialVersionUID = 3813861902193560443L;

	@Override
	protected Class<TitleSession> getSessionClass( )
	{
		return TitleSession.class;
	}

	public void load( Gender gender )
	{
		load( getSession( ).getAll( gender ), null, true );
	}

	@Override
	public void onChangeMaster( Object master )
	{
		if ( master instanceof Gender )
		{
			load( (Gender) master );
		}
	}

	@Override
	protected void load( )
	{
	}

}
