package br.com.mcampos.web.inep.controller.combobox;

import java.util.List;

import br.com.mcampos.ejb.inep.entity.RevisorType;
import br.com.mcampos.ejb.inep.revisortype.RevisorTypeSession;
import br.com.mcampos.web.core.combobox.ComboboxExt;

public class RevisorTypeCombobox extends ComboboxExt<RevisorTypeSession, RevisorType>
{
	private static final long serialVersionUID = -1516976683037188398L;

	@Override
	protected Class<RevisorTypeSession> getSessionClass( )
	{
		return RevisorTypeSession.class;
	}

	@Override
	protected void load( )
	{
		load( (List<RevisorType>) getSession( ).getAll( ), null, true );
	}

}
