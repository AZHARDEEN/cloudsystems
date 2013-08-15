package br.com.mcampos.web.core;

import java.io.Serializable;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

public interface LoggedInterface extends Serializable
{
	public static final String userSessionParamName = "currentLoggedUser";
	public static final String currentCollaborator = "currentCollaborator";

	public static final String lastLoggedUserId = "lastLoggedUserId";
	public static final String lastLoggedUserPassword = "lastLoggedUserPassword";

	boolean isLogged( );

	Login getLoggedUser( );

	Collaborator getCurrentCollaborator( );
}
