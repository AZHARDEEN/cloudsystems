package br.com.mcampos.dto;

import java.io.Serializable;

public class AuthorizedPageOptions implements Serializable
{
	private static final long serialVersionUID = -8683417600633371742L;

	private boolean authorized;

	/**
	 * @return the authorized
	 */
	public boolean isAuthorized( )
	{
		return authorized;
	}

	/**
	 * @param authorized
	 *            the authorized to set
	 */
	public void setAuthorized( boolean authorized )
	{
		this.authorized = authorized;
	}
}
