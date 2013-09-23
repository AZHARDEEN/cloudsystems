package br.com.mcampos.ejb.user.company.collaborator;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.jpa.security.LoginProperty;

public interface UserPropertyInterface
{
	LoginProperty getProperty( PrincipalDTO collaborator, String propertyName );

	void setProperty( PrincipalDTO collaborator, String propertyName, String Value );

	LoginProperty remove( PrincipalDTO collaborator, String propertyName );

}
