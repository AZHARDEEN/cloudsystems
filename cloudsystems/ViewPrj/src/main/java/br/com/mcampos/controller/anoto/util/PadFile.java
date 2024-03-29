package br.com.mcampos.controller.anoto.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.AnodeFacade;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.FormatException;
import com.anoto.api.IllegalValueException;
import com.anoto.api.NotAllowedException;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;
import com.anoto.api.PenHome;

public class PadFile
{
	private static final String pagesChildName = "pages";
	private static final String addressAttributeName = "address";
	private static final String drawingAreaName = "drawing_area";
	private static final String userAreaName = "user_area";

	private Document document;
	private AnodeFacade session;
	private FormDTO currentApplication;
	private static String httpPath;
	private final Boolean unique = false;

	public PadFile( FormDTO application )
	{
		super( );
		init( application );
	}

	public PadFile( FormDTO application, byte[ ] pad ) throws JDOMException, IOException
	{
		super( );
		init( application );
		ByteArrayInputStream is = new ByteArrayInputStream( pad );
		InputStreamReader reader = new InputStreamReader( is );
		load( reader );
	}

	protected void init( FormDTO application )
	{
		currentApplication = application;
		if ( isRegistered( application ) == false )
			register( application );
	}

	public boolean isPadFile( FormDTO form, MediaDTO media )
	{
		String fileExtension;

		if ( media == null || SysUtils.isEmpty( media.getName( ) ) )
			return false;
		fileExtension = media.getName( ).toLowerCase( );
		if ( fileExtension.endsWith( ".pad" ) == false )
			return false;
		try {
			return register( form, savePad( form, media ) );
		}
		catch ( Exception e ) {
			return false;
		}
	}

	public void load( Reader is ) throws JDOMException, IOException
	{
		SAXBuilder builder = new SAXBuilder( );
		document = builder.build( is );
	}

	public List<Element> getPages( )
	{
		Element pages;
		pages = getRoot( ) == null ? null : getRoot( ).getChild( pagesChildName );
		return pages.getChildren( "page" );
	}

	public String getPageAddress( Element pageElement )
	{
		return pageElement.getAttributeValue( addressAttributeName );
	}

	public Element getPage( String pageAddress )
	{
		for ( Element page : getPages( ) )
			if ( getPageAddress( page ).equals( pageAddress ) )
				return page;
		return null;
	}

	public List<AnotoPageFieldDTO> getFields( AnotoPageDTO anotoPage )
	{
		Element page = getPage( anotoPage.getPageAddress( ) );
		return getFields( anotoPage, page );
	}

	public List<AnotoPageFieldDTO> getFields( AnotoPageDTO anotoPage, Element page )
	{
		if ( page == null )
			return Collections.emptyList( );
		Element d = page.getChild( drawingAreaName );
		List<Element> elements = null;
		if ( d != null )
			elements = d.getChildren( userAreaName );
		if ( SysUtils.isEmpty( elements ) )
			return Collections.emptyList( );
		List<AnotoPageFieldDTO> fields = new ArrayList<AnotoPageFieldDTO>( elements.size( ) );
		Integer nSequence = 1;
		for ( Element field : elements ) {
			AnotoPageFieldDTO dto = new AnotoPageFieldDTO( );
			dto.setPage( anotoPage );
			dto.setName( field.getAttributeValue( "name" ) );
			dto.setTop( Integer.parseInt( field.getAttributeValue( "top" ) ) );
			dto.setLeft( Integer.parseInt( field.getAttributeValue( "left" ) ) );
			dto.setWidth( Integer.parseInt( field.getAttributeValue( "width" ) ) );
			dto.setHeight( Integer.parseInt( field.getAttributeValue( "height" ) ) );
			dto.setType( new FieldTypeDTO( FieldTypeDTO.typeString ) );
			dto.setSequence( nSequence );
			nSequence++;
			dto.setIcr( true );
			fields.add( dto );
		}
		return fields;
	}

	public int getNumberOfPages( )
	{
		return getPages( ) == null ? 0 : getPages( ).size( );
	}

	public Element getRoot( )
	{
		return ( document == null ) ? null : document.getRootElement( );
	}

	public int getVersion( )
	{
		Element root = getRoot( );
		if ( root == null )
			return 0;
		try {
			return root.getAttribute( "version" ).getIntValue( );
		}
		catch ( DataConversionException e ) {
			e = null;
			return 0;
		}
	}

	public boolean isRegistered( FormDTO form )
	{
		return PenHome.padIsRegistered( form.getApplication( ) );
	}

	public void unregister( FormDTO form, MediaDTO media )
	{
		String path = getPadPath( form, media );
		PenHome.unregisterPad( form.getApplication( ), path );

		File file = new File( path );
		if ( file.exists( ) )
			file.delete( );
	}

	public String savePad( FormDTO form, MediaDTO media ) throws ApplicationException, IOException
	{
		String path = getPadPath( form, media );
		File f = new File( path );
		if ( f.exists( ) )
			f.delete( );
		FileOutputStream writer = new FileOutputStream( f );
		writer.write( media.getObject( ) );
		writer.close( );
		return path;
	}

	protected String getPadPath( FormDTO form, MediaDTO media )
	{
		String path = getRegisteredPadPath( form.getApplication( ) );
		File f = new File( path );
		if ( f.exists( ) == false )
			f.mkdirs( );
		path += "/" + media.getName( );
		return path;
	}

	protected static String getPath( String path )
	{
		if ( SysUtils.isEmpty( getHttpRealPath( ) ) )
			return ( AnotoDir.anotoServerPath + "/" + path );
		else
			return getHttpRealPath( ) + "/" + path;
	}

	public static String getHttpRealPath( )
	{
		return httpPath;
	}

	public static void setHttpRealPath( String path )
	{
		if ( SysUtils.isEmpty( httpPath ) )
			httpPath = path;
	}

	protected static String getRegisteredPadPath( String appName )
	{
		return getPath( "registeredPads" ) + "/" + appName;
	}

	public String saveBackgroundImage( String path, String name, byte[ ] object )
	{
		File f = new File( path );
		if ( f.exists( ) == false )
			f.mkdirs( );
		path += "/" + name;
		f = new File( path );
		FileOutputStream writer;
		try {
			writer = new FileOutputStream( f );
			writer.write( object );
			writer.close( );
			return path;
		}
		catch ( Exception e ) {
			return null;
		}
	}

	protected boolean register( FormDTO form, String padFileName ) throws IllegalValueException, FormatException,
			NotAllowedException
	{
		String appName = form.getApplication( );

		if ( isRegistered( form ) == false )
			register( form );
		PenHome.registerPad( appName, padFileName );
		return true;
	}

	public void register( FormDTO form )
	{
		try {
			List<PadDTO> pads = getSession( ).getPads( form );
			if ( SysUtils.isEmpty( pads ) )
				return;
			for ( PadDTO pad : pads ) {
				pad.getMedia( ).setObject( getSession( ).getObject( pad.getMedia( ) ) );
				PenHome.registerPad( form.getApplication( ), savePad( form, pad.getMedia( ) ) );
			}
		}
		catch ( Exception e ) {
			return;
		}
	}

	public void unregister( FormDTO form )
	{
		if ( isRegistered( form ) )
			PenHome.unregisterPads( form.getApplication( ) );
	}

	protected AnodeFacade getSession( )
	{
		if ( session == null )
			session = (AnodeFacade) getRemoteSession( AnodeFacade.class );
		return session;
	}

	protected Object getRemoteSession( Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( remoteClass, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

	public static Pen getPen( InputStream is, String appName ) throws PenCreationException
	{
		Pen pen = PenHome.read( is, appName );
		try {
			is.close( );
		}
		catch ( IOException e ) {
			e = null;
		}
		return pen;
	}

	public Pen getPen( InputStream is ) throws PenCreationException
	{
		return getPen( is, currentApplication.getApplication( ) );
	}

	public static String getAnotoUserPath( int hash )
	{
		String path = "user_" + hash;
		return getPath( path );
	}

	public static com.anoto.api.Pen getPen( byte[ ] pgcByteArray, String appName )
	{
		ByteArrayInputStream is = new ByteArrayInputStream( pgcByteArray );
		com.anoto.api.Pen pen = null;
		try {
			pen = getPen( is, appName );
		}
		catch ( PenCreationException e ) {
			System.err.println( e.getMessage( ) );
			pen = null;
		}
		return pen;
	}

	public static com.anoto.api.Pen getPen( byte[ ] pgcByteArray )
	{
		ByteArrayInputStream is = new ByteArrayInputStream( pgcByteArray );
		com.anoto.api.Pen pen = null;
		try {
			pen = PenHome.read( is );
		}
		catch ( PenCreationException e ) {
			System.err.println( e.getMessage( ) );
			pen = null;
		}
		return pen;
	}

	public Boolean getUnique( )
	{
		return unique;
	}
}
