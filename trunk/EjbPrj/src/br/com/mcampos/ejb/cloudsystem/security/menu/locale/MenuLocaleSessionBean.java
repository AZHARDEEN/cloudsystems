package br.com.mcampos.ejb.cloudsystem.security.menu.locale;


import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless( name = "MenuLocaleSession", mappedName = "CloudSystems-EjbPrj-MenuLocaleSession" )
@Local
public class MenuLocaleSessionBean extends Crud<MenuLocalePK, MenuLocale> implements MenuLocaleSessionLocal
{
	public MenuLocaleSessionBean()
	{
	}

	public void delete( MenuLocalePK key ) throws ApplicationException
	{
		if ( get( key ) != null )
			delete( MenuLocale.class, key );
	}

	public MenuLocale get( MenuLocalePK key ) throws ApplicationException
	{
		return get( MenuLocale.class, key );
	}

	public List<MenuLocale> getAll() throws ApplicationException
	{
		return getAll( MenuLocale.getAll );
	}

	public List<MenuLocale> getAll( Menu menu ) throws ApplicationException
	{
		return ( List<MenuLocale> )getResultList( MenuLocale.getAllByMenu, menu );
	}

	public List<MenuLocale> getAll( Country country ) throws ApplicationException
	{
		return ( List<MenuLocale> )getResultList( MenuLocale.getAllByCountry, country );
	}
}
