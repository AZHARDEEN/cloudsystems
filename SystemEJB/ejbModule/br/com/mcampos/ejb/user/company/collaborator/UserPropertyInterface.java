package br.com.mcampos.ejb.user.company.collaborator;

import br.com.mcampos.ejb.user.company.collaborator.property.LoginProperty;
import br.com.mcampos.utils.dto.PrincipalDTO;

public interface UserPropertyInterface
{
	LoginProperty getProperty( PrincipalDTO collaborator, String propertyName );

	void setProperty( PrincipalDTO collaborator, String propertyName, String Value );

	LoginProperty remove( PrincipalDTO collaborator, String propertyName );

}
