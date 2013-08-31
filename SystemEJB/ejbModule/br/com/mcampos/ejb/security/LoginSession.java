package br.com.mcampos.ejb.security;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.Login;
import br.com.mcampos.utils.dto.Credential;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface LoginSession extends BaseSessionInterface<Login>
{
	Login loginByDocument( Credential credential );

	Boolean changePassword( Integer loginId, Credential credential, String oldPasswor, String newPassword );

	String getProperty( String id );

	Login getByDocument( String document );

	Boolean sendValidationEmail( Integer userId );

	boolean verifyPassword( Integer userId, String password );

	Boolean isPasswordUsed( Integer id, String newPassword );

	void logout( Integer id, Credential crecential );

	/*
	 * Administrative Tasks
	 */

	List<Login> search( String searchField, String lookFor );

	Login resetLogin( PrincipalDTO admin, Login toReset, Credential credential );

	String randomPassword( int count );

}
