package br.com.mcampos.ejb.security.log;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface AccessLogTypeSessionLocal extends BaseSessionInterface<AccessLogType>
{
}
