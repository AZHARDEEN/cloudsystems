package br.com.mcampos.ejb.cloudsystem.locality.city.session;


import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CitySessionLocal extends Serializable
{
    List<City> getAll( State state ) throws ApplicationException;

    City get( Integer id ) throws ApplicationException;
}
