package br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity.PgcProperty;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface PgcPropertySessionLocal extends Serializable
{
    List<PgcProperty> get( Integer pgcId, Integer propertyId ) throws ApplicationException;

    List<PgcProperty> getGPS( Integer pgcId ) throws ApplicationException;
}
