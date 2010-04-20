package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcProcessedImage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcStatus;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcPagePK;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.Query;


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

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Pgc get( Integer key ) throws ApplicationException
    {
        return get( Pgc.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Pgc> getAll() throws ApplicationException
    {
        return getAll( Pgc.findAllQueryName );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Pgc> getSuspended() throws ApplicationException
    {
        return getAll( Pgc.findSuspended );
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

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPenPage> get( AnotoPage page ) throws ApplicationException
    {
        return ( List<AnotoPenPage> )getResultList( AnotoPenPage.pagePensQueryName, page );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcPenPage> get( Pgc pgc ) throws ApplicationException
    {
        return ( List<PgcPenPage> )getResultList( PgcPenPage.getAllFromPGC, pgc );
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcPenPage> getAll( AnotoPenPage penPage ) throws ApplicationException
    {
        List<PgcPenPage> list;
        list = ( List<PgcPenPage> )getResultList( PgcPenPage.getAllPgcQueryName, penPage );
        return list;
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcPenPage> getAll( AnotoPen pen ) throws ApplicationException
    {
        List<PgcPenPage> list;
        list = ( List<PgcPenPage> )getResultList( PgcPenPage.penGetAllPgcs, pen );
        return list;
    }

    public void add( PgcProcessedImage processedImage ) throws ApplicationException
    {
        getEntityManager().persist( processedImage );
    }

    public void add( PgcField pgcField ) throws ApplicationException
    {
        getEntityManager().persist( pgcField );
    }

    public void add( PgcAttachment pgcField ) throws ApplicationException
    {
        Integer sequence;

        sequence = getAttachmentSequence( pgcField );
        pgcField.setSequence( sequence );
        getEntityManager().persist( pgcField );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    protected Integer getAttachmentSequence( PgcAttachment entity )
    {
        String sql;

        sql = "SELECT COALESCE ( MAX ( pat_seq_in ), 0 ) + 1 AS ID " +
                "FROM  PGC_ATTACHMENT " +
                "WHERE PGC_ID_IN = ?1 AND PPG_BOOK_ID = ?2 AND PPG_PAGE_ID = ?3 ";
        Query query = getEntityManager().createNativeQuery( sql );
        query.setParameter( 1, entity.getPgcId() );
        query.setParameter( 2, entity.getBookId() );
        query.setParameter( 3, entity.getPageId() );
        Integer id = ( Integer )query.getSingleResult();
        return id;
    }

    public void add( PgcPage entity ) throws ApplicationException
    {
        getEntityManager().persist( entity );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Media> getImages( PgcPage page ) throws ApplicationException
    {
        List<Media> medias;

        List<PgcProcessedImage> ppis = ( List<PgcProcessedImage> )getResultList( PgcProcessedImage.findPgcPageImages, page );
        if ( SysUtils.isEmpty( ppis ) )
            return Collections.emptyList();
        medias = new ArrayList<Media>( ppis.size() );
        for ( PgcProcessedImage ppi : ppis )
            medias.add( ppi.getMedia() );
        return medias;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcField> getFields( PgcPage page ) throws ApplicationException
    {
        List<PgcField> fields;

        fields = ( List<PgcField> )getResultList( PgcField.findPageFields, page );
        return fields;
    }

    public void update( PgcField field ) throws ApplicationException
    {
        PgcField entity = getEntityManager().find( PgcField.class, new PgcFieldPK( field ) );
        if ( entity != null ) {
            entity.setRevisedText( field.getRevisedText() );
            getEntityManager().merge( entity );
        }
    }

    public Integer remove( AnotoResultList item ) throws ApplicationException
    {
        PgcPageDTO dto = item.getPgcPage();
        PgcPagePK key = new PgcPagePK( dto.getPgc().getId(), dto.getBookId(), dto.getPageId() );
        PgcPage page = getEntityManager().find( PgcPage.class, key );
        if ( page != null )
            getEntityManager().remove( page );
        return 1;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcAttachment> getAttachments( PgcPage page ) throws ApplicationException
    {
        List<PgcAttachment> attachments;

        attachments = ( List<PgcAttachment> )getResultList( PgcAttachment.findByPage, page );
        return attachments;
    }
}

