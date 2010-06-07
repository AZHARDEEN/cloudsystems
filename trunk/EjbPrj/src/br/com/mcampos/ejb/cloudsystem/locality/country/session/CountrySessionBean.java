package br.com.mcampos.ejb.cloudsystem.locality.country.session;


import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CountrySession", mappedName = "CloudSystems-EjbPrj-CountrySession" )
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

}
