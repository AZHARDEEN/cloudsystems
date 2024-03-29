package br.com.mcampos.controller.anoto.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.UploadEvent;

import br.com.mcampos.controller.anoto.util.icr.IcrField;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.dto.anoto.PgcStatusDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.upload.UploadFacade;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.UploadMedia;

import com.anoto.api.Attachment;
import com.anoto.api.FormatException;
import com.anoto.api.IllegalValueException;
import com.anoto.api.NoSuchPageException;
import com.anoto.api.NoSuchPermissionException;
import com.anoto.api.NoSuchPropertyException;
import com.anoto.api.NotAllowedException;
import com.anoto.api.Page;
import com.anoto.api.PageArea;
import com.anoto.api.PageAreaException;
import com.anoto.api.PageException;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;
import com.anoto.api.PenStroke;
import com.anoto.api.PenStrokes;
import com.anoto.api.RenderException;
import com.anoto.api.Renderer;
import com.anoto.api.RendererFactory;

public class PgcFile implements Serializable, Runnable
{
	private static final long serialVersionUID = 6976380697956677391L;
	private static final Logger LOGGER = LoggerFactory.getLogger( PgcFile.class.getSimpleName( ) );
	private static final short KEY_LOCATION_COORDINATES = 16386;

	private String imageFileTypeExtension = "jpg";

	private List<MediaDTO> pgcs;
	private String penId;
	private UploadFacade session;
	private PGCDTO currentPgc;
	private Pen currentPen;
	private PadFile pad;
	private byte[ ] bytePgc;

	public PgcFile( )
	{
		super( );
		LOGGER.info( "PgcFile Created with simple constructor" );
	}

	public PgcFile( MediaDTO pgc, ArrayList<MediaDTO> medias ) throws IOException, NoSuchPermissionException, ApplicationException
	{
		this.uploadPgc( pgc );
		this.persist( medias );
		LOGGER.info( "PgcFile Created with simple constructor to upload pgc" );
	}

	private void setPenId( String id )
	{
		this.penId = id;
	}

	public String getPenId( )
	{
		return this.penId;
	}

	/*
	 * Without PAD file information
	 */

	public static String getPageAddress( byte[ ] pgc )
	{
		Pen pen = PadFile.getPen( pgc );
		Iterator it = pen.getPageAddresses( );
		if( it != null && it.hasNext( ) ) {
			return (String) it.next( );
		}
		else {
			return null;
		}
	}

	public void uploadPgc( UploadEvent evt ) throws IOException, NoSuchPermissionException
	{
		MediaDTO dto;
		dto = UploadMedia.getMedia( evt.getMedia( ) );
		this.uploadPgc( dto );
	}

	public void uploadPgc( MediaDTO dto ) throws IOException, NoSuchPermissionException
	{
		if( dto == null ) {
			return;
		}
		this.setCurrentPen( PadFile.getPen( dto.getObject( ) ) );
		String strPen;
		strPen = this.getCurrentPen( ).getPenData( ).getPenSerial( );
		this.setPenId( strPen );
		this.setCurrentPgc( new PGCDTO( dto ) );
	}

	private MediaDTO createMedia( String media )
	{
		MediaDTO dto = new MediaDTO( );
		dto.setFormat( this.getImageFileTypeExtension( ) );
		dto.setMimeType( "image" );
		dto.setName( "renderedImage." + this.getImageFileTypeExtension( ) );
		File file = new File( media );
		InputStream is;
		int length = (int) file.length( );
		byte[ ] bytes = new byte[length];
		int offset = 0;
		int numRead = 0;
		try {
			is = new FileInputStream( file );
			while( offset < bytes.length && (numRead = is.read( bytes, offset, bytes.length - offset )) >= 0 ) {
				offset += numRead;
			}
			if( offset < bytes.length ) {
				throw new IOException( "Could not completely read file " + file.getName( ) );
			}

			// Close the input stream and return bytes
			is.close( );
			dto.setObject( bytes );
			return dto;
		}
		catch( Exception e ) {
			LOGGER.error( "Create Media" + media, e );
			return null;
		}
	}

	public List<MediaDTO> getPgcs( )
	{
		if( this.pgcs == null ) {
			this.pgcs = new ArrayList<MediaDTO>( );
		}
		return this.pgcs;
	}

	private UploadFacade getRemoteSession( )
	{
		try {
			return (UploadFacade) ServiceLocator.getInstance( ).getRemoteSession( UploadFacade.class, ServiceLocator.EJB_NAME[1] );
		}
		catch( Exception e ) {
			LOGGER.error( "getRemoteSession", e );
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

	private UploadFacade getSession( )
	{
		if( this.session == null ) {
			this.session = this.getRemoteSession( );
		}
		return this.session;
	}

	private List<PgcPropertyDTO> getProperties( )
	{
		List<PgcPropertyDTO> properties = new ArrayList<PgcPropertyDTO>( );
		try {
			Iterator it = this.getCurrentPen( ).getPropertyIds( );
			while( it != null && it.hasNext( ) ) {
				Short obj = (Short) it.next( );
				if( obj != null ) {
					PgcPropertyDTO p = new PgcPropertyDTO( );
					p.setId( obj );
					LOGGER.info( "Propriedade: " + obj );
					String[ ] values = this.getCurrentPen( ).getProperty( obj );
					if( values != null && values.length > 0 ) {
						for( String v : values ) {
							v = v.replaceAll( "'", "" );
							if( SysUtils.isEmpty( v ) ) {
								continue;
							}
							p.add( v );
						}
					}
					properties.add( p );
				}
			}
		}
		catch( Exception e ) {
			LOGGER.error( "getProperties", e );
		}
		return properties;
	}

	public PGCDTO persist( ArrayList<MediaDTO> medias ) throws ApplicationException
	{
		FormDTO form;
		PGCDTO pgc = this.getCurrentPgc( );

		List<String> addresses = this.getPageAddresess( );
		List<PgcPropertyDTO> properties = this.getProperties( );
		PGCDTO insertedPgc = this.getSession( ).add( this.getCurrentPgc( ), addresses, medias, properties );
		this.setCurrentPgc( insertedPgc );
		if( insertedPgc.getPgcStatus( ).getId( ) != PgcStatusDTO.statusOk ) {
			return insertedPgc;
		}
		List<PgcPenPageDTO> pgcsPenPage = this.getSession( ).getPgcPenPages( insertedPgc );
		if( SysUtils.isEmpty( pgcsPenPage ) ) {
			return insertedPgc;
		}
		/*
		 * All pgcpenpage MUST belong to one and one only form
		 */
		form = pgcsPenPage.get( 0 ).getForm( );
		if( this.getPad( ) == null ) {
			this.setPad( new PadFile( form ) );
		}
		if( this.getPad( ).isRegistered( form ) == false ) {
			this.getPad( ).register( form );
		}
		this.setCurrentPen( PadFile.getPen( pgc.getMedia( ).getObject( ), form.getApplication( ) ) );
		try {
			this.setObject( this.getPad( ), pgc.getMedia( ).getObject( ) );
			this.processProperties( );
			this.processPGC( pgcsPenPage.get( 0 ) );
		}
		catch( Exception e ) {
			LOGGER.error( "persist", e );
			try {
				this.getSession( ).setPgcStatus( insertedPgc, 5 );
			}
			catch( Exception ex ) {
				LOGGER.error( "persist->setPgcStatus", e );
			}
		}
		return insertedPgc;
	}

	private void processProperties( ) throws NoSuchPropertyException
	{
		Iterator it;

		it = this.getCurrentPen( ).getPropertyIds( );
		while( it != null && it.hasNext( ) ) {
			Short obj = ((Short) it.next( ));
			String[ ] values = this.getCurrentPen( ).getProperty( obj );
			if( values != null && values.length > 0 ) {

			}
		}
	}

	private void processPGC( PgcPenPageDTO pgcPenPage ) throws ApplicationException
	{
		List<AnotoBook> books = null;
		List<Page> pages = null;
		int nPageIndex;
		int nBookIndex = 0;

		books = this.getBooks( );
		pages = null;
		if( SysUtils.isEmpty( books ) ) {
			pages = this.getPages( );
			if( SysUtils.isEmpty( pages ) ) {
				this.getSession( ).setPgcStatus( pgcPenPage.getPgc( ), 4 );
				return;
			}
			nPageIndex = 0;
			for( Page page : pages ) {
				this.processPage( pgcPenPage, 0, nPageIndex, page );
				nPageIndex++;
			}
		}
		else {
			for( AnotoBook book : books ) {
				nPageIndex = 0;
				for( Page page : book.getPages( ) ) {
					this.processPage( pgcPenPage, nBookIndex, nPageIndex, page );
					nPageIndex++;
				}
				nBookIndex++;
			}
		}
	}

	private void processPage( PgcPenPageDTO pgcPenPage, int nBookIndex, int nPageIndex, Page page )
	{
		String basePath;
		Map<String, IcrField> icrFields = null;

		basePath =
				String.format( "%s/%s/%s/%d", pgcPenPage.getForm( ).getApplication( ),
						pgcPenPage.getPenPage( ).getPage( ).getPad( ).getMedia( ).getName( ),
						page.getPageAddress( ), pgcPenPage.getPgc( ).getId( ) );
		basePath = PadFile.getPath( basePath );
		File file = new File( basePath );
		if( file.exists( ) == false ) {
			file.mkdirs( );
		}
		try {
			PgcPageDTO dto = new PgcPageDTO( pgcPenPage.getPgc( ), nBookIndex, nPageIndex );
			dto.setAnotoPage( new AnotoPageDTO( pgcPenPage.getPenPage( ).getPage( ).getPad( ), page.getPageAddress( ) ) );
			this.getSession( ).add( dto );
			icrFields = this.processIcr( pgcPenPage, page, basePath );
			this.addFields( dto, page, basePath, icrFields );
			List<PgcAttachmentDTO> attachs = this.addAttachments( dto, page );
			this.addAnotoImages( pgcPenPage, page, basePath, nBookIndex, nPageIndex, attachs );
		}
		catch( Exception e ) {
			LOGGER.error( "processPage. PgcPenPage: " + pgcPenPage.toString( ) + " nBookIndex: " + nBookIndex + " nPageIndex: " + nPageIndex
					+ " page:" + page.toString( ), e );
			return;
		}
		if( pgcPenPage.getForm( ).getIcrImage( ) == false ) {
			if( file.exists( ) ) {
				file.delete( );
			}
			file.deleteOnExit( );
		}
	}

	private List<PgcAttachmentDTO> getSameBarCodes( Page page, List<PgcAttachmentDTO> attachs, Integer padId,
			int currentPGC ) throws ApplicationException
	{
		List<PgcAttachmentDTO> barCodes = new ArrayList<PgcAttachmentDTO>( );
		for( PgcAttachmentDTO attach : attachs ) {
			if( attach.getType( ).equals( PgcAttachmentDTO.typeBarCode ) ) {
				List<PgcAttachmentDTO> list;
				list = this.getSession( ).getBarCodes( attach.getValue( ), page.getPageAddress( ), padId, currentPGC );
				if( SysUtils.isEmpty( list ) == false ) {
					for( PgcAttachmentDTO pa : list ) {
						if( barCodes.contains( pa ) == false ) {
							barCodes.add( pa );
						}
					}
				}
			}
		}
		return barCodes;
	}

	private void verifySameBarcode( Page page, List<PgcAttachmentDTO> attachs, Integer padId,
			int currentPGC ) throws ApplicationException, PenCreationException, PageException,
			NoSuchPageException, FormatException, IllegalValueException
	{
		if( page == null || SysUtils.isEmpty( attachs ) ) {
			return;
		}
		List<PgcAttachmentDTO> barCodes = this.getSameBarCodes( page, attachs, padId, currentPGC );
		if( SysUtils.isEmpty( barCodes ) ) {
			return;
		}
		for( PgcAttachmentDTO barCode : barCodes ) {
			byte[ ] pgcObject = this.getSession( ).getObject( barCode.getPgcPage( ).getPgc( ).getMedia( ) );
			if( pgcObject == null ) {
				continue;
			}
			PgcFile file = new PgcFile( );
			file.setObject( this.getPad( ), pgcObject );
			Page otherPage = file.getCurrentPen( ).getPage( page.getPageAddress( ) );
			if( otherPage != null ) {
				page.getPenStrokes( ).addPenStrokes( otherPage.getPenStrokes( ) );
			}
		}
	}

	private List<MediaDTO> addAnotoImages( PgcPenPageDTO pgcPenPage, Page page, String basePath, int nBookIndex, int nPageIndex,
			List<PgcAttachmentDTO> attachs ) throws RenderException, NotAllowedException,
			ApplicationException, PenCreationException
	{
		Renderer renderer;
		MediaDTO media;
		List<MediaDTO> backgroundImages = this.loadBackgroundImages( pgcPenPage, page );
		String renderedImage = basePath + "/renderedImage." + this.getImageFileTypeExtension( );
		try {
			if( pgcPenPage.getForm( ).getConcatenatePgc( ) ) {
				this.verifySameBarcode( page, attachs, pgcPenPage.getPadId( ), pgcPenPage.getPgcId( ) );
			}
		}
		catch( Exception e ) {
			LOGGER.error( "Exception on addAnotoImages basePath" + basePath + " nBookIndex: " + nBookIndex + " nPageIndex: " + nPageIndex, e );
			e = null;
		}

		renderer = RendererFactory.create( page );
		if( SysUtils.isEmpty( backgroundImages ) ) {
			renderer.renderToFile( renderedImage );
			media = this.createMedia( renderedImage );
			if( media != null ) {
				this.getSession( ).addProcessedImage( pgcPenPage.getPgc( ), media, nBookIndex, nPageIndex );
			}
		}
		else {
			for( MediaDTO image : backgroundImages ) {
				String imagePath = basePath;
				byte[ ] object = this.getSession( ).getObject( image );
				String backImage = this.getPad( ).saveBackgroundImage( imagePath, image.getName( ), object );
				renderer.setBackground( backImage );
				renderer.renderToFile( renderedImage );
				media = this.createMedia( renderedImage );
				if( media != null ) {
					this.getSession( ).addProcessedImage( pgcPenPage.getPgc( ), media, nBookIndex, nPageIndex );
				}
				File backFile = new File( backImage );
				if( backFile.exists( ) ) {
					backFile.delete( );
				}
				backFile.deleteOnExit( );
			}
		}
		File file = new File( renderedImage );
		if( SysUtils.isEmpty( pgcPenPage.getForm( ).getImagePath( ) ) == false && SysUtils.isEmpty( attachs ) == false ) {
			for( PgcAttachmentDTO attach : attachs ) {
				if( attach.getType( ).equals( PgcAttachmentDTO.typeBarCode ) && SysUtils.isEmpty( attach.getValue( ) ) == false ) {
					String targetFilename = pgcPenPage.getForm( ).getImagePath( );

					if( targetFilename.endsWith( "/" ) == false && targetFilename.endsWith( "\\" ) == false ) {
						targetFilename += "/";
					}
					File targetDir = new File( targetFilename );
					if( targetDir.exists( ) == false ) {
						targetDir.mkdirs( );
					}
					targetFilename += attach.getValue( ).trim( ) + "." + this.getImageFileTypeExtension( );
					File dest = new File( targetFilename );
					if( dest.exists( ) ) {
						dest.delete( );
					}
					file.renameTo( dest );
				}
			}
		}
		if( file.exists( ) ) {
			file.delete( );
		}
		return backgroundImages;
	}

	private boolean hasTemplate( PgcPenPageDTO pgcPenPage )
	{
		if( SysUtils.isEmpty( pgcPenPage.getPenPage( ).getPage( ).getIcrTemplate( ) ) ) {
			return false;
		}
		File icrTemplateFile = new File( pgcPenPage.getPenPage( ).getPage( ).getIcrTemplate( ) );
		if( icrTemplateFile.exists( ) == false || icrTemplateFile.isFile( ) == false ) {
			return false;
		}
		return true;
	}

	private Map<String, IcrField> processIcr( PgcPenPageDTO pgcPenPage, Page page, String basePath ) throws RenderException,
			NotAllowedException,
			ApplicationException
	{
		Renderer renderer;
		String icrImage = null;
		Map<String, IcrField> icrFields = null;

		if( pgcPenPage.getForm( ).getIcrImage( ) == false ) {
			if( this.hasTemplate( pgcPenPage ) == false ) {
				return icrFields;
			}
		}
		try {
			renderer = RendererFactory.create( page );
			icrImage = String.format( "%s/%s_%s.%s", basePath, pgcPenPage.getPageAddress( ), "icr", "JPG" );
			renderer.renderToFile( icrImage, 300 );
			// if ( hasTemplate( pgcPenPage ) )
			// String template =
			// pgcPenPage.getPenPage().getPage().getIcrTemplate();
			// icrFields = ICRObject.processImage( template, icrImage,
			// A2iaDocument.typeJPEG );
		}
		catch( Exception e ) {
			LOGGER.info( "processIcr", e );
			icrFields = null;
		}
		if( pgcPenPage.getForm( ).getIcrImage( ) == false ) {
			File file = new File( icrImage );
			if( file.exists( ) ) {
				file.delete( );
			}
		}
		return icrFields;
	}

	private List<PgcAttachmentDTO> addAttachments( PgcPageDTO pgcPage, Page page ) throws PageException, ApplicationException
	{
		PgcAttachmentDTO dto;
		int barcodeType;
		List<PgcAttachmentDTO> attachs = new ArrayList<PgcAttachmentDTO>( );

		if( page.hasAttachments( ) ) {
			Iterator it = page.getAttachments( );
			while( it != null && it.hasNext( ) ) {
				Attachment obj = (Attachment) it.next( );
				dto = new PgcAttachmentDTO( pgcPage );
				LOGGER.info( "O pgc recebido possui anexos. Tipo " + obj.getType( ) );
				if( obj.getType( ) == Attachment.ATTACHMENT_TYPE_BARCODE ) {
					dto.setType( 1 );
					byte[ ] barCodeData = obj.getData( );
					barcodeType = barCodeData[0];
					dto.setBarcodeType( barcodeType );
					String sValue = "";
					Byte caracter;
					for( int nCount = 1; nCount < barCodeData.length; nCount++ ) {
						caracter = barCodeData[nCount];
						if( barcodeType == 4 ) {
							sValue += (char) (byte) caracter;
						}
						else {
							sValue += caracter.toString( );
						}

					}
					if( dto.getBarcodeType( ) == 2 ) {
						if( sValue.length( ) > 12 ) {
							sValue = sValue.substring( 0, 12 );
						}
					}
					dto.setValue( sValue );
				}
				else {
					dto.setType( 2 );
					dto.setValue( obj.getData( ).toString( ) );
				}
				this.getSession( ).addPgcAttachment( dto );
				attachs.add( dto );
			}
		}
		return attachs;
	}

	private void addFields( PgcPageDTO pgcPage, Page page, String basePath,
			Map<String, IcrField> icrFields ) throws ApplicationException, PageAreaException
	{
		Iterator it = page.getPageAreas( );
		PageArea pageArea = null;
		PgcFieldDTO fieldDTO;
		int fieldIndex = 0;

		while( it != null && it.hasNext( ) ) {
			pageArea = (PageArea) it.next( );
			if( pageArea.getType( ) == PageArea.DRAWING_AREA ) {
				continue;
			}
			if( pageArea.getType( ) != PageArea.USER_AREA ) {
				continue;
			}
			fieldDTO = new PgcFieldDTO( pgcPage );
			fieldDTO.setName( pageArea.getName( ) );
			fieldDTO.setType( new FieldTypeDTO( 1 ) );
			fieldDTO.setHasPenstrokes( pageArea.hasPenStrokes( ) );
			pageArea.getType( );
			if( fieldDTO.getHasPenstrokes( ) ) {
				Long minTime = 0L, maxTime = 0L;
				PenStrokes pss = pageArea.getPenStrokes( );
				Iterator strokesIt = pss.getIterator( );
				while( strokesIt != null && strokesIt.hasNext( ) ) {
					PenStroke ps = (PenStroke) strokesIt.next( );
					if( maxTime < ps.getEndTime( ) ) {
						maxTime = ps.getEndTime( );
					}
					if( minTime == 0 || minTime > ps.getStartTime( ) ) {
						minTime = ps.getStartTime( );
					}
				}
				fieldDTO.setStartTime( minTime );
				fieldDTO.setEndTime( maxTime );
				try {
					this.getFieldImage( basePath, pageArea, fieldDTO );
					fieldIndex++;
				}
				catch( Exception e ) {
					LOGGER.error( "Erro em update Fields", e );
				}
			}
			if( icrFields != null ) {
				IcrField field = icrFields.get( fieldDTO.getName( ) );
				if( field != null && field.getValue( ) != null ) {
					String icrValue = field.getValue( ).toString( );
					if( SysUtils.isEmpty( icrValue ) == false ) {
						icrValue = icrValue.replaceAll( "_", " " );
						if( SysUtils.isEmpty( icrValue ) == false ) {
							fieldDTO.setIrcText( icrValue.trim( ) );
						}
					}
				}
			}
			this.getSession( ).addPgcField( fieldDTO );
		}
	}

	private void getFieldImage( String basePath, PageArea pageArea, PgcFieldDTO fieldDTO ) throws RenderException,
			NotAllowedException
	{
		Renderer renderer = null;
		String path;

		try {
			LOGGER.info( "Rendering" );
			renderer = RendererFactory.create( pageArea );
		}
		catch( Exception e ) {
			LOGGER.error( "RendererFactory.create( pageArea );", e );
		}
		path = basePath + "/" + "field." + this.getImageFileTypeExtension( );
		renderer.renderToFile( path, 200 );
		MediaDTO media = this.createMedia( path );
		fieldDTO.setMedia( media );
		File file = new File( path );
		if( file.exists( ) ) {
			file.delete( );
		}
	}

	private List<MediaDTO> loadBackgroundImages( PgcPenPageDTO pgcPenPage, Page page )
	{
		List<MediaDTO> medias = null;

		if( page == null ) {
			return medias;
		}
		try {
			AnotoPageDTO dto = new AnotoPageDTO( pgcPenPage.getPenPage( ).getPage( ).getPad( ), page.getPageAddress( ) );
			medias = this.getSession( ).getImages( dto );
			return medias;
		}
		catch( ApplicationException e ) {
			LOGGER.error( "loadBackgroundImages", e );
			return medias;
		}
	}

	private List<String> getPageAddresess( )
	{
		Iterator it = this.getCurrentPen( ).getPageAddresses( );
		List<String> addresses = null;
		while( it != null & it.hasNext( ) ) {
			if( addresses == null ) {
				addresses = new ArrayList<String>( );
			}
			addresses.add( (String) it.next( ) );
		}
		return addresses;
	}

	public void setCurrentPgc( PGCDTO currentPgc )
	{
		this.currentPgc = currentPgc;
		try {
			this.getCurrentPgc( ).setPenId( this.getCurrentPen( ).getPenData( ).getPenSerial( ) );
			this.getCurrentPgc( ).setTimeDiff( this.getCurrentPen( ).getPenData( ).getTimeDiff( ) );
		}
		catch( Exception e ) {
			LOGGER.error( "setCurrentPgc", e );
		}
	}

	public PGCDTO getCurrentPgc( )
	{
		return this.currentPgc;
	}

	private void setCurrentPen( Pen currentPen )
	{
		this.currentPen = currentPen;
	}

	public Pen getCurrentPen( )
	{
		return this.currentPen;
	}

	public void setObject( PadFile pad, byte[ ] object ) throws PenCreationException
	{
		this.setPad( pad );
		this.bytePgc = object.clone( );
		this.setCurrentPen( pad.getPen( new ByteArrayInputStream( this.bytePgc ) ) );
	}

	private void setPad( PadFile pad )
	{
		this.pad = pad;
	}

	private PadFile getPad( )
	{
		return this.pad;
	}

	public int getBookCount( )
	{
		try {
			Iterator<?> it = this.getCurrentPen( ).getLogicalBooks( );
			int nCount = 0;
			while( it != null && it.hasNext( ) ) {
				it.next( );
				nCount++;
			}
			return nCount;
		}
		catch( Exception e ) {
			LOGGER.error( "getBookCount", e );
			return 0;
		}
	}

	public List<AnotoBook> getBooks( )
	{
		List<AnotoBook> books = null;

		Iterator<?> bookIterator;
		try {
			bookIterator = this.getCurrentPen( ).getLogicalBooks( );
			while( bookIterator != null && bookIterator.hasNext( ) ) {
				if( books == null ) {
					books = new ArrayList<AnotoBook>( );
				}
				AnotoBook ab = new AnotoBook( (Iterator<?>) bookIterator.next( ) );
				books.add( ab );
			}
			return books;
		}
		catch( Exception e ) {
			LOGGER.error( "getBooks", e );
			return books;
		}
	}

	public List<Page> getPages( )
	{
		Iterator it;
		List<Page> list = null;

		try {
			it = this.getCurrentPen( ).getPages( );
			while( it != null && it.hasNext( ) ) {
				if( list == null ) {
					list = new ArrayList<Page>( );
				}
				list.add( (Page) it.next( ) );
			}
		}
		catch( PageException e ) {
			LOGGER.error( "getPages", e );
			list = null;
		}
		return list;
	}

	private Iterator getBook( int nIndex )
	{
		if( nIndex < 0 ) {
			return null;
		}
		Iterator book = null;
		Iterator it;
		try {
			it = this.getCurrentPen( ).getLogicalBooks( );
		}
		catch( Exception e ) {
			LOGGER.error( "getBook", e );
			return null;
		}
		while( it != null && it.hasNext( ) && nIndex >= 0 ) {
			book = (Iterator) it.next( );
			nIndex--;
		}
		if( nIndex >= 0 ) {
			return null;
		}
		return book;
	}

	public Page getPage( int book, int nIndex )
	{
		Iterator bookIt = this.getBook( book );
		Page page = null;
		page = this.getPage( bookIt, nIndex );
		return page;
	}

	public Page getPage( Iterator book, int nIndex )
	{
		Page page = null;
		if( book == null ) {
			return this.getPage( nIndex );
		}
		while( book.hasNext( ) && nIndex >= 0 ) {
			page = (Page) book.next( );
			nIndex--;
		}
		if( nIndex >= 0 ) {
			return null;
		}
		return page;
	}

	public Page getPage( int nIndex )
	{
		Page page = null;
		Iterator it;
		try {
			it = this.getCurrentPen( ).getPages( );
		}
		catch( PageException e ) {
			LOGGER.error( "getPage", e );
			return null;
		}
		while( it != null && it.hasNext( ) && nIndex >= 0 ) {
			page = (Page) it.next( );
			nIndex--;
		}
		return page;
	}

	public int getPageCount( int nBook )
	{
		int nIndex = 0;

		Iterator book = this.getBook( nBook );
		if( book != null ) {
			while( book.hasNext( ) ) {
				book.next( );
				nIndex++;
			}
		}
		else {
			try {
				Iterator it = this.getCurrentPen( ).getPages( );
				while( it != null && it.hasNext( ) ) {
					it.next( );
					nIndex++;
				}
			}
			catch( PageException e ) {
				LOGGER.error( "getPageCount", e );
				nIndex = 0;
			}
		}
		return nIndex;
	}

	public boolean hasLogicalBooks( )
	{
		Iterator books;
		try {
			books = this.getCurrentPen( ).getLogicalBooks( );
			return(books != null && books.hasNext( ));
		}
		catch( Exception e ) {
			LOGGER.error( "hasLogicalBooks", e );
			return false;
		}
	}

	public void setImageFileTypeExtension( String imageFileType )
	{
		this.imageFileTypeExtension = imageFileType;
	}

	public String getImageFileTypeExtension( )
	{
		return this.imageFileTypeExtension;
	}

	@Override
	public void run( )
	{
	}
}
