package br.com.mcampos.ejb.security;

import javax.ejb.Local;

import br.com.mcampos.dto.core.CredentialDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.jpa.user.Person;

@Local
public interface LoginSessionLocal extends BaseCrudSessionInterface<Login>
{
	Login loginByDocument( CredentialDTO credential );

	Login getByDocument( String document );

	Boolean sendValidationEmail( Integer userId );

	public Login add( Person person, String password );

	public Login getByToken( String token );

	boolean verifyPassword( Login login, String password );

	boolean verifyPassword( Integer userId, String password );

	public boolean validateToken( String token, String password );

	public void setPassword( Login login, String password );
}
