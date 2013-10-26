package br.com.mcampos.jpa;

import java.io.Serializable;
import java.util.List;

public interface SelfRelationInterface<ENTITY> extends Serializable
{
	void setParent( ENTITY parent );

	ENTITY getParent( );

	void add( ENTITY child );

	ENTITY remove( ENTITY child );

	List<ENTITY> getChilds( );
}
