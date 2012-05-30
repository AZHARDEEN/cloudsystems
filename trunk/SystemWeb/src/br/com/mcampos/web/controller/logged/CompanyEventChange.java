package br.com.mcampos.web.controller.logged;

import org.zkoss.zk.ui.event.Event;

import br.com.mcampos.dto.Authentication;

public class CompanyEventChange extends Event
{
	private static final long serialVersionUID = 2038162350038996955L;
	public static final String eventName = "onChangeCompany";
	Authentication auth;

	public CompanyEventChange( Authentication auth )
	{
		super( eventName );
		this.auth = auth;
	}

	public Authentication getAuth( )
	{
		return this.auth;
	}

	public void setAuth( Authentication auth )
	{
		this.auth = auth;
	}

}
