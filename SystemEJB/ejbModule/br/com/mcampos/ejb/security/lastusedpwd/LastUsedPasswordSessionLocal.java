package br.com.mcampos.ejb.security.lastusedpwd;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.security.LastUsedPassword;
import br.com.mcampos.entity.security.Login;

@Local
public interface LastUsedPasswordSessionLocal extends BaseCrudSessionInterface<LastUsedPassword>
{
	LastUsedPassword get( Login login );

	void closeAllUsedPassword( Login login );
}
