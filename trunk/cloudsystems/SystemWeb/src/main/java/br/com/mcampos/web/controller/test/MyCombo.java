package br.com.mcampos.web.controller.test;

import org.zkoss.zul.Combobox;

public class MyCombo extends Combobox
{
	private static final long serialVersionUID = -2727707472338450201L;

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
