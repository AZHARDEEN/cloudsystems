package br.com.mcampos.ejb.fdigital.form;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class AnotoFormSessionBean
 */
@Stateless( name = "AnotoFormSession", mappedName = "AnotoFormSession" )
@LocalBean
public class AnotoFormSessionBean extends SimpleSessionBean<AnotoForm> implements AnotoFormSession, AnotoFormLocal
{
	@Override
	protected Class<AnotoForm> getEntityClass( )
	{
		return AnotoForm.class;
	}
}
