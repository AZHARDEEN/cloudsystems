package br.com.mcampos.web.core;

import java.io.Serializable;

public interface LoggedInterface extends Serializable
{
	public static final String lastLoggedUserId = "lastLoggedUserId";
	public static final String lastLoggedUserPassword = "lastLoggedUserPassword";

	boolean isLogged( );
}
