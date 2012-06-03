package br.com.mcampos.ejb.core;

import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

public interface CollaboratorBaseSessionInterface<ENTITY> extends BaseSessionInterface<ENTITY>
{
	String getProperty( Collaborator auth, String name );

	void setProperty( Collaborator auth, String name, String value );

	void remove( Collaborator auth, String name );
}
