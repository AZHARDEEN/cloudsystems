package br.com.mcampos.web.controller.tables.email;

import br.com.mcampos.ejb.email.EMail;
import br.com.mcampos.ejb.email.EmailSession;
import br.com.mcampos.web.core.SimpleTableController;

public class EmailTemplateController extends SimpleTableController<EmailSession, EMail>
{
	private static final long serialVersionUID = -4283675143558584121L;

	@Override
	protected Class<EmailSession> getSessionClass( )
	{
		return EmailSession.class;
	}

}
