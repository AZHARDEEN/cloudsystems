package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcStatus;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;

import com.anoto.api.core.NoSuchPermissionException;
import com.anoto.api.core.Pen;
import com.anoto.api.core.PenCreationException;
import com.anoto.api.core.PenHome;

import java.io.ByteArrayInputStream;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PGCSession", mappedName = "CloudSystems-EjbPrj-PGCSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PGCSessionBean extends Crud<Integer, Pgc> implements PGCSessionLocal
{
    public PGCSessionBean()
    {
    }

    public void delete( Integer key )
    {
        delete( Pgc.class, key );
    }

    public Pgc get( Integer key )
    {
        return get( Pgc.class, key );
    }

    public List<Pgc> getAll()
    {
        return getAll( Pgc.findAllQueryName );
    }

    @Override
    public Pgc add( Pgc entity )
    {
        entity.setInsertDate( new Date() );
        PgcStatus status;
        if ( entity.getPgcStatus() != null )
            status = getEntityManager().find( PgcStatus.class, entity.getPgcStatus().getId() );
        else
            status = getEntityManager().find( PgcStatus.class, 1 );
        entity.setPgcStatus( status );
        return super.add( entity );
    }

    public PgcPenPage attach( Pgc pgc, AnotoPenPage penPage ) throws ApplicationException
    {
        PgcPenPage entity = new PgcPenPage( penPage, pgc );
        getEntityManager().persist( entity );
        return entity;
    }

}

