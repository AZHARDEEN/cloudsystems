package br.com.mcampos.web.controller.company;

import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.ejb.user.company.collaborator.type.CollaboratorTypeSession;
import br.com.mcampos.jpa.user.CollaboratorType;
import br.com.mcampos.web.core.SimpleTableController;
import br.com.mcampos.web.renderer.CollaboratorTypeListRenderer;

public class CollaboratorTypeController extends SimpleTableController<CollaboratorTypeSession, CollaboratorType>
{
	private static final long serialVersionUID = -465511069784913876L;

	@Override
	protected Class<CollaboratorTypeSession> getSessionClass( )
	{
		return CollaboratorTypeSession.class;
	}

	@Override
	protected ListitemRenderer<?> getListRenderer( )
	{
		return new CollaboratorTypeListRenderer( );
	}

}
