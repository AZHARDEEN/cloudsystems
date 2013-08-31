package br.com.mcampos.ejb.fdigital.pen;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.fdigital.AnotoPen;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class AnotoPenSessionBean
 */
@Stateless( name = "AnotoPenSession", mappedName = "AnotoPenSession" )
@LocalBean
public class AnotoPenSessionBean extends SimpleSessionBean<AnotoPen> implements AnotoPenSession, AnotoPenSessionLocal
{
	@Override
	protected Class<AnotoPen> getEntityClass( )
	{
		return AnotoPen.class;
	}

	@Override
	protected String allQueryOrderByClause( String entityAlias )
	{
		String orderBy;

		if ( SysUtils.isEmpty( entityAlias ) ) {
			entityAlias = "AnotoPen";
		}
		orderBy = entityAlias + ".id";
		return orderBy;
	}
}
