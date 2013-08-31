package br.com.mcampos.ejb.fdigital.penpage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.fdigital.AnotoPen;
import br.com.mcampos.entity.fdigital.AnotoPenPage;

/**
 * Session Bean implementation class AnotoPenPageSessionBean
 */
@Stateless( name = "AnotoPenPageSession", mappedName = "AnotoPenPageSession" )
@LocalBean
public class AnotoPenPageSessionBean extends SimpleSessionBean<AnotoPenPage> implements AnotoPenPageSession, AnotoPenPageSessionLocal
{

	@Override
	protected Class<AnotoPenPage> getEntityClass( )
	{
		return AnotoPenPage.class;
	}

	@Override
	public AnotoPenPage get( AnotoPen pen, String pageId )
	{
		return getByNamedQuery( AnotoPenPage.getPenPage, pen, pageId );
	}

}
