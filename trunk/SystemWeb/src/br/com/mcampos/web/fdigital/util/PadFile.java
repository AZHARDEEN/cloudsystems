package br.com.mcampos.web.fdigital.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.fdigital.form.AnotoForm;
import br.com.mcampos.ejb.fdigital.form.AnotoFormSession;
import br.com.mcampos.ejb.fdigital.form.pad.Pad;
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

	private static String httpPath;
	private AnotoForm form;
	private Document document;
	private final Boolean unique = false;
	private AnotoFormSession session;

	public PadFile( AnotoFormSession session, AnotoForm form )
	{
		setForm( form );
		setSession( session );
	}

	public PadFile( AnotoFormSession session, AnotoForm form, byte[ ] pad ) throws JDOMException, IOException
	{
		setForm( form );
		setSession( session );
		ByteArrayInputStream is = new ByteArrayInputStream( pad );
		InputStreamReader reader = new InputStreamReader( is );
		load( reader );
	}

	private AnotoForm getForm( )
	{
		return this.form;
	}

	private void setForm( AnotoForm form )
	{
		this.form = form;
		if ( getForm( ) != null ) {
			if ( isRegistered( ) == false ) {
				register( );
			}
		}
	}

	public boolean isPadFile( MediaDTO media )
	{
		String fileExtension;

		if ( media == null || SysUtils.isEmpty( media.getName( ) ) ) {
			return false;
		}
		fileExtension = media.getName( ).toLowerCase( );
		if ( fileExtension.endsWith( ".pad" ) == false ) {
			return false;
		}
		try {
			return register( savePad( media.getName( ), media.getObject( ) ) );
		}
		catch ( Exception e ) {
			return false;
		}
	}

	public void load( Reader is ) throws JDOMException, IOException
	{
		SAXBuilder builder = new SAXBuilder( );
		this.document = builder.build( is );
	}

	public String getPageAddress( Element pageElement )
	{
		return pageElement.getAttributeValue( addressAttributeName );
	}

	public List<Element> getPages( )
	{
		Element pages;
		pages = getRoot( ) == null ? null : getRoot( ).getChild( pagesChildName );
		if ( pages != null ) {
			return pages.getChildren( "page" );
		}
		else {
			return Collections.emptyList( );
		}
	}

	public Element getRoot( )
	{
		return ( this.document == null ) ? null : this.document.getRootElement( );
	}

	public int getVersion( )
	{
		Element root = getRoot( );
		if ( root == null ) {
			return 0;
		}
		try {
			return root.getAttribute( "version" ).getIntValue( );
		}
		catch ( DataConversionException e ) {
			e = null;
			return 0;
		}
	}

	protected String getPadPath( String name )
	{
		String path = getRegisteredPadPath( getForm( ).getApplication( ) );
		File f = new File( path );
		if ( f.exists( ) == false && f.mkdirs( ) == false ) {
			return null;
		}
		path += "/" + name;
		return path;
	}

	protected static String getPath( String path )
	{
		if ( SysUtils.isEmpty( getHttpRealPath( ) ) ) {
			return ( AnotoDir.anotoServerPath + "/" + path );
		}
		else {
			return getHttpRealPath( ) + "/" + path;
		}
	}

	public static String getHttpRealPath( )
	{
		return httpPath;
	}

	public static void setHttpRealPath( String path )
	{
		if ( SysUtils.isEmpty( httpPath ) ) {
			httpPath = path;
		}
	}

	public String savePad( String name, byte[ ] media ) throws IOException
	{
		String path = getPadPath( name );
		File f = new File( path );
		if ( f.delete( ) == false ) {
			return null;
		}
		FileOutputStream writer = new FileOutputStream( f );
		writer.write( media );
		writer.close( );
		return path;
	}

	public boolean isRegistered( )
	{
		return PenHome.padIsRegistered( getForm( ).getApplication( ) );
	}

	public void unregister( MediaDTO media )
	{
		String path = getPadPath( media.getName( ) );
		PenHome.unregisterPad( getForm( ).getApplication( ), path );

		File file = new File( path );
		if ( file.exists( ) ) {
			if ( file.delete( ) == false ) {
				return;
			}
		}
	}

	protected static String getRegisteredPadPath( String appName )
	{
		return getPath( "registeredPads" ) + "/" + appName;
	}

	public String saveBackgroundImage( String path, String name, byte[ ] object )
	{
		File f = new File( path );
		if ( f.exists( ) == false && f.mkdirs( ) == false ) {
			return null;
		}
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

	protected boolean register( String padFileName ) throws IllegalValueException, FormatException,
			NotAllowedException
	{
		String appName = getForm( ).getApplication( );

		if ( isRegistered( ) == false ) {
			register( );
		}
		PenHome.registerPad( appName, padFileName );
		return true;
	}

	public void register( )
	{
		try {
			for ( Pad pad : getForm( ).getPads( ) ) {
				pad.getMedia( ).setObject( getSession( ).getObject( pad.getMedia( ) ) );
				PenHome.registerPad( getForm( ).getApplication( ),
						savePad( pad.getMedia( ).getName( ), pad.getMedia( ).getObject( ) ) );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace( );
			return;
		}
	}

	public void unregister( )
	{
		if ( isRegistered( ) ) {
			PenHome.unregisterPads( getForm( ).getApplication( ) );
		}
	}

	public static String getAnotoUserPath( int hash )
	{
		String path = "user_" + hash;
		return getPath( path );
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
		return getPen( is, getForm( ).getApplication( ) );
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
		return this.unique;
	}

	private AnotoFormSession getSession( )
	{
		return this.session;
	}

	private void setSession( AnotoFormSession session )
	{
		this.session = session;
	}

}
