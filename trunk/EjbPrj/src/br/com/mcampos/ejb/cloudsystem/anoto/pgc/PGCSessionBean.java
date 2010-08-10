package br.com.mcampos.ejb.cloudsystem.anoto.pgc;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity.PgcProperty;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.image.PgcProcessedImage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcstatus.PgcStatus;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.session.RevisionStatusSessionLocal;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Stateless( name = "PGCSession", mappedName = "CloudSystems-EjbPrj-PGCSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PGCSessionBean extends Crud<Integer, Pgc> implements PGCSessionLocal
{

    public static final String formParameterName = "form";
    public static final String penParameterName = "pen";
    public static final String initDateParameterName = "initDate";
    public static final String endDateParameterName = "endDate";
    public static final String barCodeParameterName = "barCode";
    public static final String bookIdFromParameterName = "bookIdFrom";
    public static final String bookIdToParameterName = "bookIdTo";
    public static final String fieldValueParameterName = "pgcFieldValue";
    public static final String revisedStatusParameterName = "revisedStatus";

    @EJB
    private PgcPageSessionLocal pgcPageSession;

    @EJB
    private RevisionStatusSessionLocal revisionSession;

    public PGCSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        deleteProperty( key );
        delete( Pgc.class, key );
    }

    private void deleteProperty( int id ) throws ApplicationException
    {
        Query query = getEntityManager().createNamedQuery( PgcProperty.deleteFromPGC );
        query.setParameter( 1, id );
        query.executeUpdate();
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
        entity.setRevisionStatus( revisionSession.get( 1 ) );
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

    public void add( PgcPageAttachment pgcField ) throws ApplicationException
    {
        Integer sequence;

        sequence = getAttachmentSequence( pgcField );
        pgcField.setSequence( sequence );
        getEntityManager().persist( pgcField );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    protected Integer getAttachmentSequence( PgcPageAttachment entity )
    {
        String sql;

        sql =
"SELECT COALESCE ( MAX ( pat_seq_in ), 0 ) + 1 AS ID " + "FROM  PGC_PAGE_ATTACHMENT " + "WHERE PGC_ID_IN = ?1 AND PPG_BOOK_ID = ?2 AND PPG_PAGE_ID = ?3 ";
        Query query = getEntityManager().createNativeQuery( sql );
        query.setParameter( 1, entity.getPgcId() );
        query.setParameter( 2, entity.getBookId() );
        query.setParameter( 3, entity.getPageId() );
        Integer id = ( Integer )query.getSingleResult();
        return id;
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
        List<PgcField> fields = Collections.emptyList();

        PgcPage merged = pgcPageSession.get( new PgcPagePK( page ) );
        if ( merged != null )
            fields = ( List<PgcField> )getResultList( PgcField.findPageFields, merged );
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
    public List<PgcPageAttachment> getAttachments( PgcPage page ) throws ApplicationException
    {
        List<PgcPageAttachment> attachments;

        attachments = ( List<PgcPageAttachment> )getResultList( PgcPageAttachment.findByPage, page );
        return attachments;
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcAttachment> getAttachments( Pgc pgc ) throws ApplicationException
    {
        List<PgcAttachment> attachments;

        attachments = ( List<PgcAttachment> )getResultList( PgcAttachment.findAllPgc, pgc.getId() );
        if ( SysUtils.isEmpty( attachments ) )
            return Collections.emptyList();
        return attachments;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcProperty> getProperties( Pgc pgc ) throws ApplicationException
    {
        List<PgcProperty> attachments;

        attachments = ( List<PgcProperty> )getResultList( PgcProperty.getAllByPgc, pgc.getId() );
        if ( SysUtils.isEmpty( attachments ) )
            return Collections.emptyList();
        return attachments;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcProperty> getGPS( Pgc pgc ) throws ApplicationException
    {
        List<PgcProperty> attachments;

        attachments = ( List<PgcProperty> )getResultList( PgcProperty.getAllGPS, pgc.getId() );
        if ( SysUtils.isEmpty( attachments ) )
            return Collections.emptyList();
        return attachments;
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Boolean isEnabled( Pgc pgc, String pageAddress )
    {
        Query query = getEntityManager().createNamedQuery( AnotoPenPage.penPageAddressQueryName );
        query.setParameter( 1, pgc ).setParameter( 2, pageAddress );
        Object obj;
        try {
            obj = query.getSingleResult();
        }
        catch ( NoResultException e ) {
            obj = null;
        }
        return ( obj != null );
    }


    public void add( PgcAttachment attach ) throws ApplicationException
    {
        getEntityManager().persist( attach );
    }

}

