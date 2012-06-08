package br.com.mcampos.web.controller.test;

import org.zkoss.zul.Div;

public class DivTest extends Div
{
	private static final long serialVersionUID = 6260887028051741352L;
	private String foo;

	public String getFoo( )
	{
		return this.foo;
	}

	public void setFoo( String foo )
	{
		this.foo = foo;
	}

}
