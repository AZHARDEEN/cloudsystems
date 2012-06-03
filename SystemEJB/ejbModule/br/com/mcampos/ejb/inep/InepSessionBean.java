package br.com.mcampos.ejb.inep;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepTask;

/**
 * Session Bean implementation class InepSessionBean
 */
@Stateless( name = "InepSession", mappedName = "InepSession" )
@LocalBean
public class InepSessionBean extends SimpleSessionBean<InepTask> implements InepSession, InepSessionLocal
{
	@Override
	protected Class<InepTask> getEntityClass( )
	{
		return InepTask.class;
	}

}
