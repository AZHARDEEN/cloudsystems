package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import com.anoto.api.FormatException;
import com.anoto.api.IllegalValueException;
import com.anoto.api.NotAllowedException;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;
import com.anoto.api.PenHome;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.List;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import org.zkoss.zk.ui.Executions;


public class PadFile
{
    protected static final String licenceChildName = "license";
    protected static final String spaceDefinitionChildName = "spacedefinition";
    protected static final String pagesChildName = "pages";
    protected static final String addressAttributeName = "address";
    protected static final String drawingAreaName = "drawing_area";
    protected static final String userAreaName = "user_area";
    protected static final String anotoServerPath = "/anoto_res";

    protected Document document;
    protected AnodeFacade session;
    protected FormDTO currentApplication;


    public PadFile( FormDTO application )
    {
        super();
        init( application );
    }

    public PadFile( FormDTO application, byte[] pad ) throws JDOMException, IOException
    {
        super();
        init( application );
        ByteArrayInputStream is = new ByteArrayInputStream( pad );
        InputStreamReader reader = new InputStreamReader( is );
        load( reader );
    }


    protected void init( FormDTO application )
    {
        this.currentApplication = application;
        if ( isRegistered( application ) == false )
            register( application );
    }


    public boolean isPadFile( FormDTO form, MediaDTO media )
    {
        String fileExtension;

        if ( media == null || SysUtils.isEmpty( media.getName() ) )
            return false;
        fileExtension = media.getName().toLowerCase();
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
        SAXBuilder builder = new SAXBuilder();
        document = builder.build( is );
    }

    public List<Element> getPages()
    {
        Element pages;
        pages = getRoot() == null ? null : getRoot().getChild( pagesChildName );
        return pages.getChildren( "page" );
    }

    public String getPageAddress( Element pageElement )
    {
        return pageElement.getAttributeValue( addressAttributeName );
    }

    public int getNumberOfPages()
    {
        return getPages() == null ? 0 : getPages().size();
    }

    public Element getRoot()
    {
        return ( document == null ) ? null : document.getRootElement();
    }

    public int getVersion()
    {
        Element root = getRoot();
        if ( root == null )
            return 0;
        try {
            return root.getAttribute( "version" ).getIntValue();
        }
        catch ( DataConversionException e ) {
            e = null;
            return 0;
        }
    }

    public boolean isRegistered( FormDTO form )
    {
        return PenHome.padIsRegistered( form.getApplication() );
    }

    public void unregister( FormDTO form, MediaDTO media )
    {
        String path = getPadPath( form, media );
        PenHome.unregisterPad( form.getApplication(), path );

        File file = new File( path );
        if ( file.exists() )
            file.delete();
    }

    public String savePad( FormDTO form, MediaDTO media ) throws ApplicationException, IOException
    {
        String path = getPadPath( form, media );
        File f = new File( path );
        if ( f.exists() ) {
            f.delete();
        }
        FileOutputStream writer = new FileOutputStream( f );
        writer.write( media.getObject() );
        writer.close();
        return path;
    }

    protected String getPadPath( FormDTO form, MediaDTO media )
    {
        String path = getRegisteredPadPath( form.getApplication() );
        File f = new File( path );
        if ( f.exists() == false )
            f.mkdirs();
        path += "/" + media.getName();
        return path;
    }


    protected static String getPath( String path )
    {
        return Executions.getCurrent().getDesktop().getWebApp().getRealPath( anotoServerPath + "/" + path );
    }

    protected static String getRegisteredPadPath( String appName )
    {
        return getPath( "registeredPads" ) + "/" + appName;
    }

    public String saveBackgroundImage( String path, String name, byte[] object )
    {
        File f = new File( path );
        if ( f.exists() == false )
            f.mkdirs();
        path += "/" + name;
        f = new File( path );
        FileOutputStream writer;
        try {
            writer = new FileOutputStream( f );
            writer.write( object );
            writer.close();
            return path;
        }
        catch ( Exception e ) {
            return null;
        }
    }


    protected boolean register( FormDTO form, String padFileName ) throws IllegalValueException, FormatException,
                                                                          NotAllowedException
    {
        String appName = form.getApplication();

        if ( isRegistered( form ) == false )
            register( form );
        PenHome.registerPad( appName, padFileName );
        return true;
    }

    public void register( FormDTO form )
    {
        try {
            List<PadDTO> pads = getSession().getPads( form );
            if ( SysUtils.isEmpty( pads ) )
                return;
            for ( PadDTO pad : pads ) {
                pad.getMedia().setObject( getSession().getObject( pad.getMedia() ) );
                PenHome.registerPad( form.getApplication(), savePad( form, pad.getMedia() ) );
            }
        }
        catch ( Exception e ) {
            return;
        }
    }


    public void unregister( FormDTO form )
    {
        if ( isRegistered( form ) )
            PenHome.unregisterPads( form.getApplication() );
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }


    protected Object getRemoteSession( Class remoteClass )
    {
        try {
            return ServiceLocator.getInstance().getRemoteSession( remoteClass );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }

    public static Pen getPen( InputStream is, String appName ) throws PenCreationException
    {
        Pen pen = PenHome.read( is, appName );
        try {
            is.close();
        }
        catch ( IOException e ) {
            e = null;
        }
        return pen;
    }

    public Pen getPen( InputStream is ) throws PenCreationException
    {
        return getPen( is, currentApplication.getApplication() );
    }

    public static String getAnotoUserPath( int hash )
    {
        String path = "user_" + hash;
        return getPath( path );
    }

    public static com.anoto.api.Pen getPen( byte[] pgcByteArray, String appName )
    {
        ByteArrayInputStream is = new ByteArrayInputStream( pgcByteArray );
        com.anoto.api.Pen pen = null;
        try {
            pen = getPen( is, appName );
        }
        catch ( PenCreationException e ) {
            System.err.println( e.getMessage() );
            pen = null;
        }
        return pen;
    }

    public static com.anoto.api.Pen getPen( byte[] pgcByteArray )
    {
        ByteArrayInputStream is = new ByteArrayInputStream( pgcByteArray );
        com.anoto.api.Pen pen = null;
        try {
            pen = PenHome.read( is );
        }
        catch ( PenCreationException e ) {
            System.err.println( e.getMessage() );
            pen = null;
        }
        return pen;
    }


}
