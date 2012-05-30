package br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage;


import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;
import java.util.Properties;

import javax.ejb.Local;


@Local
public interface PgcPenPageSessionLocal extends Serializable
{
    List<PgcPage> getAll( Properties props, Integer maxRecords, Boolean bNewFirst ) throws ApplicationException;

    PgcPenPage add( PgcPenPage entity ) throws ApplicationException;

    PgcPenPage update( PgcPenPage entity ) throws ApplicationException;

    void delete( PgcPenPagePK key ) throws ApplicationException;

    PgcPenPage get( PgcPenPagePK key ) throws ApplicationException;

    void delete( Pgc entity ) throws ApplicationException;
}
