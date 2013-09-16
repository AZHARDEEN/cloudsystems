package br.com.mcampos.ejb.fdigital.pgcstatus;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.fdigital.PgcStatus;

/**
 * Session Bean implementation class PgcStatusSessionBean
 */
@Stateless( name = "PgcStatusSession", mappedName = "PgcStatusSession" )
@LocalBean
public class PgcStatusSessionBean extends SimpleSessionBean<PgcStatus> implements PgcStatusSession, PgcStatusSessionLocal
{
	@Override
	protected Class<PgcStatus> getEntityClass( )
	{
		return PgcStatus.class;
	}

}
