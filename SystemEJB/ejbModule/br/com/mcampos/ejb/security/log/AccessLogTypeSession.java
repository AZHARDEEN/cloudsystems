package br.com.mcampos.ejb.security.log;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.security.AccessLogType;

@Remote
public interface AccessLogTypeSession extends BaseSessionInterface<AccessLogType>
{
}
