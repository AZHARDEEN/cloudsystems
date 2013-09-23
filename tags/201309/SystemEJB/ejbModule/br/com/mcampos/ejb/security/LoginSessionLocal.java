package br.com.mcampos.ejb.security;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.sysutils.dto.CredentialDTO;

@Local
public interface LoginSessionLocal extends BaseCrudSessionInterface<Login>
{
	Login loginByDocument( CredentialDTO credential );

	Login getByDocument( String document );

	Boolean sendValidationEmail( Integer userId );

	public Boolean add( Person person, String password );

	public Login getByToken( String token );

	boolean verifyPassword( Login login, String password );

	boolean verifyPassword( Integer userId, String password );

	public boolean validateToken( String token, String password );
}
