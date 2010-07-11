package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field;


import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PgcFieldSessionLocal extends Serializable
{
    PgcField add( PgcField entity ) throws ApplicationException;

    PgcField update( PgcField entity ) throws ApplicationException;

    void delete( PgcFieldPK key ) throws ApplicationException;

    PgcField get( PgcFieldPK key ) throws ApplicationException;

    List<PgcField> getAll( PgcPage pgcPage ) throws ApplicationException;


}
