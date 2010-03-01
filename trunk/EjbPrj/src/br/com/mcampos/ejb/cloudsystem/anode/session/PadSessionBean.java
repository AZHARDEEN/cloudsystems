package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.BackgroundImage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.BackgroundImagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PadPK;
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

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Stateless( name = "PadSession", mappedName = "CloudSystems-EjbPrj-PadSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PadSessionBean extends Crud<PadPK, Pad> implements PadSessionLocal
{
    public PadSessionBean()
    {
    }

    public List<AnotoPage> getPages( Pad pad ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( pad );
        List<AnotoPage> pageList = ( List<AnotoPage> )getResultList( AnotoPage.padPagesGetAllNamedQuery, parameter );
        return pageList;
    }

    public Pad get( PadPK key )
    {
        return get( Pad.class, key );
    }

    public AnotoPage getPage( AnotoPagePK key )
    {
        return getEntityManager().find( AnotoPage.class, key );
    }

    public List<Media> getImages( AnotoPage page ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( page );
        List<BackgroundImage> images = ( List<BackgroundImage> )getResultList( BackgroundImage.pageImagesNamesQuery, parameter );
        List<Media> medias = Collections.emptyList();
        if ( images != null && images.size() > 0 ) {
            medias = new ArrayList<Media>( images.size() );
            for ( BackgroundImage image : images ) {
                medias.add( image.getMedia() );
            }
        }
        return medias;
    }

    public Media removeImage( AnotoPage pageEntity, Media image ) throws ApplicationException
    {
        BackgroundImage removeEntity =
            getEntityManager().find( BackgroundImage.class, new BackgroundImagePK( pageEntity, image ) );
        getEntityManager().remove( removeEntity.getMedia() );
        getEntityManager().remove( removeEntity );
        return image;
    }


    public Media addImage( AnotoPage page, Media image ) throws ApplicationException
    {
        page = getEntityManager().merge( page );
        image = getEntityManager().merge( image );

        BackgroundImage newEntity = new BackgroundImage( page, image );
        newEntity.setInsertDate( new Date() );
        getEntityManager().persist( newEntity );
        return image;
    }

    protected List<AnotoPen> makePenList( List<AnotoPenPage> list )
    {
        List<AnotoPen> pens = Collections.emptyList();
        if ( SysUtils.isEmpty( list ) == false ) {
            pens = new ArrayList<AnotoPen>( list.size() );
            for ( AnotoPenPage item : list ) {
                pens.add( item.getPen() );
            }
        }
        return pens;
    }

    public List<AnotoPen> getAvailablePens( AnotoPage page ) throws ApplicationException
    {
        String sqlQuery;
        Query query;
        List<AnotoPen> list;

        sqlQuery =
                "SELECT pen_id_ch , pen_insert_dt  FROM anoto_pen WHERE PEN_ID_CH NOT IN ( SELECT PEN_ID_CH FROM ANOTO_PEN_PAGE WHERE FRM_ID_IN = ?1 AND 	PAD_ID_IN = ?2 AND 	APG_ID_CH = ?3 )";
        query = getEntityManager().createNativeQuery( sqlQuery, AnotoPen.class );
        query.setParameter( 1, page.getFormId() );
        query.setParameter( 2, page.getPadId() );
        query.setParameter( 3, page.getPageAddress() );
        try {
            list = ( List<AnotoPen> )query.getResultList();
        }
        catch ( NoResultException e ) {
            e = null;
            list = Collections.emptyList();
        }
        return list;
    }

    public List<AnotoPen> getPens( AnotoPage page ) throws ApplicationException
    {
        List<AnotoPenPage> list = ( List<AnotoPenPage> )getResultList( AnotoPenPage.pagePensQueryName, page );
        return makePenList( list );
    }

    public void addPens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException
    {
        AnotoPenPage entity;

        for ( AnotoPen pen : pens ) {
            entity = new AnotoPenPage( pen, page );
            getEntityManager().persist( entity );
        }
    }

    public void removePens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException
    {
        AnotoPenPage entity;
        AnotoPenPagePK key = new AnotoPenPagePK();

        key.setPage( page );
        for ( AnotoPen pen : pens ) {
            key.setPen( pen );
            entity = getEntityManager().find( AnotoPenPage.class, key );
            if ( entity != null )
                getEntityManager().remove( entity );
        }
    }

}
