package br.com.mcampos.ejb.fdigital.form.pad.page;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class AnotoPageSessionBean
 */
@Stateless
@LocalBean
public class AnotoPageSessionBean extends SimpleSessionBean<AnotoPage> implements AnotoPageSessionLocal
{

	@Override
	protected Class<AnotoPage> getEntityClass( )
	{
		// TODO Auto-generated method stub
		return AnotoPage.class;
	}

}
