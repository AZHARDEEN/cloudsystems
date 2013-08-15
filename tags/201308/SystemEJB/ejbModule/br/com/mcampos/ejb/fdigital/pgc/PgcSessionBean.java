package br.com.mcampos.ejb.fdigital.pgc;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class PgcSessionBean
 */
@Stateless( name = "PgcSession", mappedName = "PgcSession" )
@LocalBean
public class PgcSessionBean extends SimpleSessionBean<Pgc> implements PgcSession, PgcSessionLocal
{
	@Override
	protected Class<Pgc> getEntityClass( )
	{
		return Pgc.class;
	}

}
