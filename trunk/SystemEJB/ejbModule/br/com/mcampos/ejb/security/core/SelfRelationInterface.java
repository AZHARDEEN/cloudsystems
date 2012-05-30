package br.com.mcampos.ejb.security.core;

import java.io.Serializable;

public interface SelfRelationInterface<ENTITY> extends Serializable
{
	void setParent( ENTITY parent );

	void add( ENTITY child );

	ENTITY remove( ENTITY child );
}
