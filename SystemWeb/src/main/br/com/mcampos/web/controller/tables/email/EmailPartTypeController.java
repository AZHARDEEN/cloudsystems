package br.com.mcampos.web.controller.tables.email;

import br.com.mcampos.ejb.email.parttype.EMailPartType;
import br.com.mcampos.ejb.email.parttype.EmailPartTypeSession;
import br.com.mcampos.web.core.SimpleTableController;

public class EmailPartTypeController extends SimpleTableController<EmailPartTypeSession, EMailPartType>
{
	private static final long serialVersionUID = 6070469234658220338L;

	@Override
	protected Class<EmailPartTypeSession> getSessionClass( )
	{
		return EmailPartTypeSession.class;
	}

}
