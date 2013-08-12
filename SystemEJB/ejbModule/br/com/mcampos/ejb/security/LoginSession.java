package br.com.mcampos.ejb.security;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.utils.dto.Credential;

@Remote
public interface LoginSession extends BaseSessionInterface<Login>
{
	Login loginByDocument( Credential credential );

	Boolean changePassword( Login login, Credential credential, String oldPasswor, String newPassword );

	String getProperty( String id );

	Login getByDocument( String document );

	Boolean sendValidationEmail( Integer userId );

	boolean verifyPassword( Integer userId, String password );

	Boolean isPasswordUsed( Integer id, String newPassword );

	void logout( Login login, Credential crecential );

}
