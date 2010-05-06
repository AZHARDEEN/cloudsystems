package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage;


import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Local;

@Local
public interface PgcPageSessionLocal
{
    void delete( PgcPagePK key ) throws ApplicationException;

    PgcPage get( PgcPagePK key ) throws ApplicationException;
}
