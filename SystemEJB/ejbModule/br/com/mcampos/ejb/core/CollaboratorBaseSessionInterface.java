package br.com.mcampos.ejb.core;

import br.com.mcampos.utils.dto.PrincipalDTO;

public interface CollaboratorBaseSessionInterface<ENTITY> extends BaseSessionInterface<ENTITY>
{
	String getProperty( PrincipalDTO auth, String name );

	void setProperty( PrincipalDTO auth, String name, String value );

	void remove( PrincipalDTO auth, String name );
}
