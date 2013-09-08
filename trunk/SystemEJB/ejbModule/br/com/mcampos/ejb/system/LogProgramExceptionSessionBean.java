package br.com.mcampos.ejb.system;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.system.LogProgramException;

/**
 * Session Bean implementation class LogProgramExceptionSessionBean
 */
@Stateless
public class LogProgramExceptionSessionBean extends SimpleSessionBean<LogProgramException> implements LogProgramExceptionSessionLocal
{

	@Override
	protected Class<LogProgramException> getEntityClass( )
	{
		return LogProgramException.class;
	}

}
