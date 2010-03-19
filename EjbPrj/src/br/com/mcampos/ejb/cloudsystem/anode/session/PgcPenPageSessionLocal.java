package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcPenPagePK;
import br.com.mcampos.ejb.session.core.CrudInterface;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import java.util.Properties;

import javax.ejb.Local;

@Local
public interface PgcPenPageSessionLocal
{
    List<PgcPenPage> getAll( Properties props ) throws ApplicationException;

    PgcPenPage add( PgcPenPage entity ) throws ApplicationException;

    PgcPenPage update( PgcPenPage entity ) throws ApplicationException;

    void delete( PgcPenPagePK key ) throws ApplicationException;

    PgcPenPage get( PgcPenPagePK key ) throws ApplicationException;
}
