package br.com.mcampos.ejb.cloudsystem.locality.region.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.region.entity.Region;
import br.com.mcampos.ejb.cloudsystem.locality.region.entity.RegionPK;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface RegionSessionLocal extends Serializable
{
    void delete( RegionPK key ) throws ApplicationException;

    Region get( RegionPK key ) throws ApplicationException;

    List<Region> getAll( Country country ) throws ApplicationException;

    Integer nextIntegerId( Country country ) throws ApplicationException;

    Region add( Region entity ) throws ApplicationException;

    Region update( Region entity ) throws ApplicationException;
}
