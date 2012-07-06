package br.com.mcampos.ejb.security.lastusedpwd;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.security.Login;

@Local
public interface LastUsedPasswordSessionLocal extends BaseSessionInterface<LastUsedPassword>
{
	LastUsedPassword get( Login login );

	void closeAllUsedPassword( Login login );
}
