package br.com.mcampos.ejb.cloudsystem.anoto.pad;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.BackgroundImage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.page.BackgroundImagePK;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.system.entity.FieldType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

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

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPage> getPages( Pad pad ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( pad );
        List<AnotoPage> pageList = ( List<AnotoPage> )getResultList( AnotoPage.padPagesGetAllNamedQuery, parameter );
        return pageList;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPage> getPages() throws ApplicationException
    {
        List<AnotoPage> pageList = ( List<AnotoPage> )getResultList( AnotoPage.anotoPagesGetAllNamedQuery );
        return pageList;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPage> getPages( AnotoForm param ) throws ApplicationException
    {
        List<AnotoPage> pageList = Collections.emptyList();

        if ( param != null )
            pageList = ( List<AnotoPage> )getResultList( AnotoPage.formPagesGetAllNamedQuery, param );
        return pageList;
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Pad get( PadPK key ) throws ApplicationException
    {
        return get( Pad.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public AnotoPage getPage( AnotoPagePK key )
    {
        return getEntityManager().find( AnotoPage.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
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
        BackgroundImagePK key = new BackgroundImagePK( pageEntity, image );
		BackgroundImage removeEntity = getEntityManager().find( BackgroundImage.class, key );
        //getEntityManager().remove( removeEntity.getMedia() );
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

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
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

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPen> getPens( AnotoPage page ) throws ApplicationException
    {
        List<AnotoPenPage> list = ( List<AnotoPenPage> )getResultList( AnotoPenPage.pagePensQueryName, page );
        return AnotoUtils.getPenListFromPenPage( list, true );
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

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPage> getPages( String address ) throws ApplicationException
    {
        List<AnotoPage> list = ( List<AnotoPage> )getResultList( AnotoPage.pagesGetAddressesNamedQuery, address );
        return list;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public AnotoPenPage getPenPage( AnotoPen pen, AnotoPage page ) throws ApplicationException
    {
        AnotoPenPagePK key = new AnotoPenPagePK( pen, page );
		AnotoPenPage penPage = getEntityManager().find( AnotoPenPage.class, key );
        return penPage;
    }

    public void add( AnotoPage page, List<AnotoPageFieldDTO> fields ) throws ApplicationException
    {
        for ( AnotoPageFieldDTO field : fields ) {
            FieldType type = getEntityManager().find( FieldType.class, field.getType().getId() );
			AnotoPageField entity = new AnotoPageField( page, field.getName(), type );
            entity.setIcr( field.getIcr() );
            entity.setLeft( field.getLeft() );
            entity.setTop( field.getTop() );
            entity.setWidth( field.getWidth() );
            entity.setHeight( field.getHeight() );
            getEntityManager().persist( entity );
        }
    }

    public void update( AnotoPage page )
    {
        AnotoPagePK key = new AnotoPagePK( page.getPageAddress(), page.getFormId(), page.getPadId() );

		AnotoPage entity = getEntityManager().find( AnotoPage.class, key );
        if ( entity != null ) {
            entity.setDescription( page.getDescription() );
            entity.setIcrTemplate( page.getIcrTemplate() );
            getEntityManager().merge( entity );
        }
    }
}