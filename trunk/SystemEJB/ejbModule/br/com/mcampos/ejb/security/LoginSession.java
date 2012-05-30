package br.com.mcampos.ejb.security;

import javax.ejb.Remote;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.utils.dto.Credential;

@Remote
public interface LoginSession extends BaseSessionInterface<Login>
{
	Login loginByDocument( Credential credential );

	Boolean changePassword( Authentication auth, Credential credential, String oldPasswor, String newPassword );

	String getProperty( String id );

	Login getByDocument( String document );

	Boolean sendValidationEmail( Integer userId );

}
