package br.com.mcampos.ejb.security;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.sysutils.dto.CredentialDTO;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Remote
public interface LoginSession extends BaseCrudSessionInterface<Login>
{
	Login loginByDocument( CredentialDTO credential );

	Boolean changePassword( Integer loginId, CredentialDTO credential, String oldPasswor, String newPassword );

	String getProperty( String id );

	Login getByDocument( String document );

	Boolean sendValidationEmail( Integer userId );

	boolean verifyPassword( Integer userId, String password );

	Boolean isPasswordUsed( Integer id, String newPassword );

	void logout( Integer id, CredentialDTO crecential );

	/*
	 * Administrative Tasks
	 */

	List<Login> search( String searchField, String lookFor );

	Login resetLogin( PrincipalDTO admin, Login toReset, CredentialDTO credential );

	String randomPassword( int count );

}
