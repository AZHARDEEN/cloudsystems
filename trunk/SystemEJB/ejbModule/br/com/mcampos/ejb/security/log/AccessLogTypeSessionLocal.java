package br.com.mcampos.ejb.security.log;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.AccessLogType;

@Local
public interface AccessLogTypeSessionLocal extends BaseSessionInterface<AccessLogType>
{
}
