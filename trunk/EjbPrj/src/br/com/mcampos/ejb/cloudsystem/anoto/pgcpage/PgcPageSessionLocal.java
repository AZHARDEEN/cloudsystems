package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage;


import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface PgcPageSessionLocal extends Serializable
{
    void delete( PgcPagePK key ) throws ApplicationException;

    PgcPage get( PgcPagePK key ) throws ApplicationException;

    PgcPage add( PgcPage entity ) throws ApplicationException;

    void setRevisedStatus( PgcPage page, Integer status ) throws ApplicationException;

    void getRevisedStatus( PgcPage page ) throws ApplicationException;
}
