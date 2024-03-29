package br.com.mcampos.ejb.cloudsystem.locality.country.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CountrySession", mappedName = "CountrySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CountrySessionBean extends Crud<String, Country> implements CountrySessionLocal
{

    public void delete( String key ) throws ApplicationException
    {
        delete( Country.class, key );
    }

    public Country get( String key ) throws ApplicationException
    {
        return get( Country.class, key );
    }

    public List<Country> getAll() throws ApplicationException
    {
        return getAll( Country.getAll );
    }

    public List<Country> getAllWithCities() throws ApplicationException
    {
        return getAll( Country.getAllWithCities );
    }

}
