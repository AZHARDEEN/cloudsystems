package br.com.mcampos.ejb.security.log;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.security.AccessLog;
import br.com.mcampos.jpa.security.Login;

@Remote
public interface AccessLogSession extends BaseCrudSessionInterface<AccessLog>
{
	public AccessLog getLastLogin( Login login );
}
