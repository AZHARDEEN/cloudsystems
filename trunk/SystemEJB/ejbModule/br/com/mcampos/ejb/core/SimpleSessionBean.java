package br.com.mcampos.ejb.core;

import javax.persistence.Query;

public abstract class SimpleSessionBean<T> extends BaseSessionBean<T>
{
	@Override
	protected void setParameters( Query query )
	{
	}
}
