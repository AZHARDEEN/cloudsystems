package br.com.mcampos.ejb.core;

import java.io.Serializable;

public interface BasicEntityRenderer<T> extends Serializable, BaseEntity
{
	public String getField( Integer field );

	public int compareTo( T object, Integer field );
}
