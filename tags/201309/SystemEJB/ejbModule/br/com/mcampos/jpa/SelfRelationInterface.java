package br.com.mcampos.jpa;

import java.io.Serializable;

public interface SelfRelationInterface<ENTITY> extends Serializable
{
	void setParent( ENTITY parent );

	void add( ENTITY child );

	ENTITY remove( ENTITY child );
}
