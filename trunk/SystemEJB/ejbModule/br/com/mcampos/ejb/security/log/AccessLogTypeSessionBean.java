package br.com.mcampos.ejb.security.log;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.security.AccessLogType;

@Stateless( name = "AccessLogTypeSession", mappedName = "AccessLogTypeSession" )
public class AccessLogTypeSessionBean extends SimpleSessionBean<AccessLogType> implements AccessLogTypeSession, AccessLogTypeSessionLocal
{
	@Resource
	SessionContext sessionContext;

	@Override
	protected Class<AccessLogType> getEntityClass( )
	{
		return AccessLogType.class;
	}
}
