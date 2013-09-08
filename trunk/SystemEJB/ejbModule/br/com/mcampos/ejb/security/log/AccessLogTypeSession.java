package br.com.mcampos.ejb.security.log;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.security.AccessLogType;

@Remote
public interface AccessLogTypeSession extends BaseCrudSessionInterface<AccessLogType>
{
}
