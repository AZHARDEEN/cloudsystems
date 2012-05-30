package br.com.mcampos.ejb.core;

import br.com.mcampos.dto.Authentication;

public interface CollaboratorBaseSessionInterface<ENTITY> extends BaseSessionInterface<ENTITY>
{
	String getProperty( Authentication auth, String name );

	void setProperty( Authentication auth, String name, String value );

	void remove( Authentication auth, String name );
}
