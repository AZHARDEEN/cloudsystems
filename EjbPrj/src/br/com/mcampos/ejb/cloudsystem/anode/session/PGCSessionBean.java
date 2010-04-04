package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcProcessedImage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcStatus;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
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

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Pgc.class, key );
    }

    public Pgc get( Integer key ) throws ApplicationException
    {
        return get( Pgc.class, key );
    }

    public List<Pgc> getAll() throws ApplicationException
    {
        return getAll( Pgc.findAllQueryName );
    }

    public Pgc add( Pgc entity ) throws ApplicationException
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
        if ( pgc == null || penPage == null )
            return null;
        PgcPenPage entity = new PgcPenPage( penPage, pgc );
        getEntityManager().persist( entity );
        return entity;
    }

    public void setPgcStatus( Pgc pgc, Integer newStatus ) throws ApplicationException
    {
        getEntityManager().merge( pgc );
        pgc.setPgcStatus( getEntityManager().find( PgcStatus.class, newStatus ) );
    }

    public List<AnotoPenPage> get( AnotoPage page ) throws ApplicationException
    {
        return ( List<AnotoPenPage> )getResultList( AnotoPenPage.pagePensQueryName, page );
    }

    public List<PgcPenPage> get( Pgc pgc ) throws ApplicationException
    {
        return ( List<PgcPenPage> )getResultList( PgcPenPage.getAllFromPGC, pgc );
    }


    public List<PgcPenPage> getAll( AnotoPenPage penPage ) throws ApplicationException
    {
        List<PgcPenPage> list;
        list = ( List<PgcPenPage> )getResultList( PgcPenPage.getAllPgcQueryName, penPage );
        return list;
    }


    public List<PgcPenPage> getAll( AnotoPen pen ) throws ApplicationException
    {
        List<PgcPenPage> list;
        list = ( List<PgcPenPage> )getResultList( PgcPenPage.penGetAllPgcs, pen );
        return list;
    }

    public void add ( PgcProcessedImage processedImage ) throws ApplicationException
    {
        getEntityManager().persist( processedImage );
    }

    public void add ( PgcField pgcField ) throws ApplicationException
    {
        getEntityManager().persist( pgcField );
    }

}

