package br.com.mcampos.ejb.cloudsystem.anoto.pad;

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

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.anoto.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.page.BackgroundImage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.BackgroundImagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.session.PenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "PadSession", mappedName = "PadSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
@SuppressWarnings( "unchecked" )
public class PadSessionBean extends Crud<PadPK, Pad> implements PadSessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2794372349758450177L;
	@EJB
	private PenPageSessionLocal penPageSession;

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPage> getPages( Pad pad ) throws ApplicationException
	{
		List<Object> parameter = new ArrayList<Object>( 1 );
		parameter.add( pad );
		List<AnotoPage> pageList = (List<AnotoPage>) this.getResultList( AnotoPage.padPagesGetAllNamedQuery, parameter );
		return pageList;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPage> getPages( ) throws ApplicationException
	{
		List<AnotoPage> pageList = (List<AnotoPage>) this.getResultList( AnotoPage.anotoPagesGetAllNamedQuery );
		return pageList;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPage> getPages( AnotoForm param ) throws ApplicationException
	{
		List<AnotoPage> pageList = Collections.emptyList( );

		if( param != null ) {
			pageList = (List<AnotoPage>) this.getResultList( AnotoPage.formPagesGetAllNamedQuery, param );
		}
		return pageList;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Pad get( PadPK key ) throws ApplicationException
	{
		return this.get( Pad.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public AnotoPage getPage( AnotoPagePK key )
	{
		return this.getEntityManager( ).find( AnotoPage.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<Media> getImages( AnotoPage page ) throws ApplicationException
	{
		List<Object> parameter = new ArrayList<Object>( 1 );
		parameter.add( page );
		List<BackgroundImage> images = (List<BackgroundImage>) this.getResultList( BackgroundImage.pageImagesNamesQuery, parameter );
		List<Media> medias = Collections.emptyList( );
		if( images != null && images.size( ) > 0 ) {
			medias = new ArrayList<Media>( images.size( ) );
			for( BackgroundImage image : images ) {
				medias.add( image.getMedia( ) );
			}
		}
		return medias;
	}

	@Override
	public Media removeImage( AnotoPage pageEntity, Media image ) throws ApplicationException
	{
		BackgroundImagePK key = new BackgroundImagePK( pageEntity, image );
		BackgroundImage removeEntity = this.getEntityManager( ).find( BackgroundImage.class, key );
		// getEntityManager().remove( removeEntity.getMedia() );
		this.getEntityManager( ).remove( removeEntity );
		return image;
	}

	@Override
	public Media addImage( AnotoPage page, Media image ) throws ApplicationException
	{
		page = this.getEntityManager( ).merge( page );
		image = this.getEntityManager( ).merge( image );

		BackgroundImage newEntity = new BackgroundImage( page, image );
		newEntity.setInsertDate( new Date( ) );
		this.getEntityManager( ).persist( newEntity );
		return image;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPen> getAvailablePens( AnotoPage page ) throws ApplicationException
	{
		String sqlQuery;
		Query query;
		List<AnotoPen> list;

		sqlQuery = "SELECT *  FROM anoto_pen WHERE PEN_ID_CH NOT IN ( SELECT PEN_ID_CH FROM ANOTO_PEN_PAGE WHERE FRM_ID_IN = ?1 AND 	PAD_ID_IN = ?2 AND 	APG_ID_CH = ?3 )";
		query = this.getEntityManager( ).createNativeQuery( sqlQuery, AnotoPen.class );
		query.setParameter( 1, page.getFormId( ) );
		query.setParameter( 2, page.getPadId( ) );
		query.setParameter( 3, page.getPageAddress( ) );
		try {
			list = query.getResultList( );
		}
		catch( NoResultException e ) {
			e = null;
			list = Collections.emptyList( );
		}
		return list;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPen> getPens( AnotoPage page ) throws ApplicationException
	{
		List<AnotoPenPage> list = (List<AnotoPenPage>) this.getResultList( AnotoPenPage.pagePensQueryName, page );
		return AnotoUtils.getPenListFromPenPage( list, true );
	}

	@Override
	public void addPens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException
	{
		AnotoPenPage entity;

		for( AnotoPen pen : pens ) {
			entity = new AnotoPenPage( pen, page );
			this.getEntityManager( ).persist( entity );
		}
	}

	@Override
	public void removePens( AnotoPage page, List<AnotoPen> pens ) throws ApplicationException
	{
		for( AnotoPen pen : pens ) {
			this.penPageSession.delete( page, pen );
		}
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPage> getPages( String address ) throws ApplicationException
	{
		List<AnotoPage> list = (List<AnotoPage>) this.getResultList( AnotoPage.pagesGetAddressesNamedQuery, address );
		return list;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public AnotoPenPage getPenPage( AnotoPen pen, AnotoPage page ) throws ApplicationException
	{
		return this.penPageSession.get( page, pen );
	}

	@Override
	public void add( AnotoPage page, List<AnotoPageFieldDTO> fields ) throws ApplicationException
	{
		Integer sequence = 1;
		for( AnotoPageFieldDTO field : fields ) {
			FieldType type = this.getEntityManager( ).find( FieldType.class, field.getType( ).getId( ) );
			AnotoPageField entity = new AnotoPageField( page, field.getName( ), type );
			entity.setIcr( field.getIcr( ) );
			entity.setLeft( field.getLeft( ) );
			entity.setTop( field.getTop( ) );
			entity.setWidth( field.getWidth( ) );
			entity.setHeight( field.getHeight( ) );
			if( SysUtils.isZero( field.getSequence( ) ) ) {
				entity.setSequence( sequence );
			}
			else {
				entity.setSequence( field.getSequence( ) );
			}
			this.getEntityManager( ).persist( entity );
			sequence++;
		}
	}

	@Override
	public void update( AnotoPage page )
	{
		AnotoPagePK key = new AnotoPagePK( page.getPageAddress( ), page.getFormId( ), page.getPadId( ) );

		AnotoPage entity = this.getEntityManager( ).find( AnotoPage.class, key );
		if( entity != null ) {
			entity.setDescription( page.getDescription( ) );
			entity.setIcrTemplate( page.getIcrTemplate( ) );
			this.getEntityManager( ).merge( entity );
		}
	}
}
