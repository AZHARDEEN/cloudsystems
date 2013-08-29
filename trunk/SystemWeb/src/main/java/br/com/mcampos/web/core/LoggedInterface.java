package br.com.mcampos.web.core;

import java.io.Serializable;

import br.com.mcampos.utils.dto.PrincipalDTO;

public interface LoggedInterface extends Serializable
{
	public static final String currentPrincipal = "currentPrincipal";

	public static final String lastLoggedUserId = "lastLoggedUserId";
	public static final String lastLoggedUserPassword = "lastLoggedUserPassword";

	boolean isLogged( );

	PrincipalDTO getPrincipal( );
}
