package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.locale.country.CountrySession;
import br.com.mcampos.entity.locale.Country;
import br.com.mcampos.web.core.listbox.ReadOnlyListboxController;

public class CountryController extends ReadOnlyListboxController<CountrySession, Country>
{
	private static final long serialVersionUID = 7751253410832175001L;

	@Override
	protected Class<CountrySession> getSessionClass( )
	{
		return CountrySession.class;
	}
}
