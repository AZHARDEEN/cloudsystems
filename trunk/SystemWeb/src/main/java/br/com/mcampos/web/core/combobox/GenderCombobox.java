package br.com.mcampos.web.core.combobox;

import java.util.List;

import br.com.mcampos.ejb.user.person.gender.GenderSession;
import br.com.mcampos.entity.user.Gender;

public class GenderCombobox extends ComboboxExt<GenderSession, Gender>
{
	private static final long serialVersionUID = -7706250007578273124L;

	@Override
	protected Class<GenderSession> getSessionClass( )
	{
		return GenderSession.class;
	}

	@Override
	public void load( )
	{
		load( ( (List<Gender>) getSession( ).getAll( getPrincipal( ) ) ), null, true );
	}
}
