package br.com.mcampos.ejb.cloudsystem.security.menu.locale;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;

import java.util.List;

import javax.ejb.Local;


@Local
public interface MenuLocaleSessionLocal
{
	MenuLocale add( MenuLocale entity ) throws ApplicationException;

	MenuLocale update( MenuLocale entity ) throws ApplicationException;

	void delete( MenuLocalePK key ) throws ApplicationException;

	MenuLocale get( MenuLocalePK key ) throws ApplicationException;

	List<MenuLocale> getAll() throws ApplicationException;

	List<MenuLocale> getAll( Menu menu ) throws ApplicationException;

	List<MenuLocale> getAll( Country country ) throws ApplicationException;
}
