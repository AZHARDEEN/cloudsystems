package br.com.mcampos.ejb.cloudsystem.locality.country.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface CountrySessionLocal extends Serializable
{
    void delete( String key ) throws ApplicationException;

    Country get( String key ) throws ApplicationException;

    List<Country> getAll() throws ApplicationException;

    List<Country> getAllWithCities() throws ApplicationException;

    Country add( Country entity ) throws ApplicationException;

    Country update( Country entity ) throws ApplicationException;
}
