package br.com.mcampos.ejb.user.company.collaborator;

import br.com.mcampos.ejb.user.company.collaborator.property.LoginProperty;

public interface UserPropertyInterface
{
	LoginProperty getProperty( Collaborator collaborator, String propertyName );

	void setProperty( Collaborator collaborator, String propertyName, String Value );

	LoginProperty remove( Collaborator collaborator, String propertyName );

}
