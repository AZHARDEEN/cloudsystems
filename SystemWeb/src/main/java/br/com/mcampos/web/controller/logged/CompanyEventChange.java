package br.com.mcampos.web.controller.logged;

import org.zkoss.zk.ui.event.Event;

import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

public class CompanyEventChange extends Event
{
	private static final long serialVersionUID = 2038162350038996955L;
	public static final String eventName = "onChangeCompany";
	private Collaborator collaborator;

	public CompanyEventChange( Collaborator collaborator )
	{
		super( eventName );
		setCollaborator( collaborator );
	}

	public Collaborator getCollaborator( )
	{
		return collaborator;
	}

	private void setCollaborator( Collaborator collaborator )
	{
		this.collaborator = collaborator;
	}
}
