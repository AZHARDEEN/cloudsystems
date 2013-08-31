package br.com.mcampos.ejb.security.log;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.AccessLog;
import br.com.mcampos.entity.security.Login;

@Local
public interface AccessLogSessionLocal extends BaseSessionInterface<AccessLog>
{
	public AccessLog getLastLogin( Login login );
}
