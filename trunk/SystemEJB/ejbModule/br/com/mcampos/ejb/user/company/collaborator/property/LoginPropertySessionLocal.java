package br.com.mcampos.ejb.user.company.collaborator.property;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Local
public interface LoginPropertySessionLocal extends BaseSessionInterface<LoginProperty>
{
	LoginProperty getProperty( Collaborator collaborator, String propertyName );

	void setProperty( Collaborator collaborator, String propertyName, String Value );

	LoginProperty remove( Collaborator collaborator, String propertyName );
}
