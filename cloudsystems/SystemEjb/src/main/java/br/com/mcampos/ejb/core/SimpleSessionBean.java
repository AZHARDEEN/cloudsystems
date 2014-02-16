package br.com.mcampos.ejb.core;

import javax.persistence.Query;

public abstract class SimpleSessionBean<T> extends BaseCrudSessionBean<T>
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5952783682500521280L;

	@Override
	protected void setParameters( Query query )
	{
	}
}
