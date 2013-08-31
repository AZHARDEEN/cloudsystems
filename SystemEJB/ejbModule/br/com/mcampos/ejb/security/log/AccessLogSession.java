package br.com.mcampos.ejb.security.log;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.AccessLog;
import br.com.mcampos.entity.security.Login;

@Remote
public interface AccessLogSession extends BaseSessionInterface<AccessLog>
{
	public AccessLog getLastLogin( Login login );
}
