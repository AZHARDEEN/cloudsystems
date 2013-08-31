package br.com.mcampos.ejb.security;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.Login;
import br.com.mcampos.entity.user.Person;
import br.com.mcampos.utils.dto.Credential;

@Local
public interface LoginSessionLocal extends BaseSessionInterface<Login>
{
	Login loginByDocument( Credential credential );

	Login getByDocument( String document );

	Boolean sendValidationEmail( Integer userId );

	public Boolean add( Person person, String password );

	public Login getByToken( String token );

	boolean verifyPassword( Login login, String password );

	boolean verifyPassword( Integer userId, String password );

	public boolean validateToken( String token, String password );
}
