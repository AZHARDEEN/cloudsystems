package br.com.mcampos.web.fdigital.combobox;

import java.util.List;

import br.com.mcampos.ejb.fdigital.form.AnotoForm;
import br.com.mcampos.ejb.fdigital.form.AnotoFormSession;
import br.com.mcampos.web.core.combobox.ComboboxExt;

public class FormCombobox extends ComboboxExt<AnotoFormSession, AnotoForm>
{
	private static final long serialVersionUID = -6887939105799602327L;

	@Override
	protected Class<AnotoFormSession> getSessionClass( )
	{
		return AnotoFormSession.class;
	}

	@Override
	protected void load( )
	{
		load( (List<AnotoForm>) getSession( ).getAll( ), null, true );
	}

}
