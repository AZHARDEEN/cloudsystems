package br.com.mcampos.ejb.cloudsystem.locality.city.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CitySession", mappedName = "CitySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CitySessionBean extends Crud<Integer, City> implements CitySessionLocal
{
    public List<City> getAll( State state ) throws ApplicationException
    {
        List<City> list = Collections.emptyList();

        if ( state != null )
            list = ( List<City> )getResultList( City.getAllByState, state );
        return list;
    }

    public City get( Integer id ) throws ApplicationException
    {
        return get( City.class, id );
    }
}


