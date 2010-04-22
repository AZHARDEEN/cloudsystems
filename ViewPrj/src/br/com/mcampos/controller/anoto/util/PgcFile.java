package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.anoto.PgcStatusDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;
import br.com.mcampos.util.system.UploadMedia;

import com.anoto.api.Attachment;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.UploadEvent;


public class PgcFile
{
    private static final short KEY_LOCATION_COORDINATES = 16386;

    protected String imageFileTypeExtension = "png";

    protected List<MediaDTO> pgcs;
    protected String penId;
    protected AnodeFacade session;
    protected PGCDTO currentPgc;
    protected Pen currentPen;
    protected PadFile pad;
    protected byte[] bytePgc;


    public PgcFile()
    {
        super();
    }

    protected void setPenId( String id )
    {
        penId = id;
    }

    public String getPenId()
    {
        return penId;
    }

    /*
     * Without PAD file information
     */

    public static String getPageAddress( byte[] pgc )
    {
        Pen pen = PadFile.getPen( pgc );
        Iterator it = pen.getPageAddresses();
        if ( it != null && it.hasNext() )
            return ( String )it.next();
        else
            return null;
    }

    public void uploadPgc( UploadEvent evt ) throws IOException, NoSuchPermissionException
    {
        MediaDTO dto;
        dto = UploadMedia.getMedia( evt.getMedia() );
        uploadPgc( dto );
    }


    public void uploadPgc( MediaDTO dto ) throws IOException, NoSuchPermissionException
    {
        if ( dto == null )
            return;
        setCurrentPen( PadFile.getPen( dto.getObject() ) );
        String strPen;
        strPen = getCurrentPen().getPenData().getPenSerial();
        setPenId( strPen );
        setCurrentPgc( new PGCDTO( dto ) );
    }

    protected MediaDTO createMedia( MediaDTO base, byte[] object, String prefix )
    {
        MediaDTO dto = new MediaDTO();
        dto.setFormat( "pgc" );
        dto.setMimeType( base.getMimeType() );
        dto.setName( prefix + "_" + base.getName() );
        dto.setObject( object );
        return dto;
    }

    protected MediaDTO createMedia( String media )
    {
        MediaDTO dto = new MediaDTO();
        dto.setFormat( getImageFileTypeExtension() );
        dto.setMimeType( "image" );
        dto.setName( "renderedImage." + getImageFileTypeExtension() );
        File file = new File( media );
        InputStream is;
        int length = ( int )file.length();
        byte[] bytes = new byte[ length ];
        int offset = 0;
        int numRead = 0;
        try {
            is = new FileInputStream( file );
            while ( offset < bytes.length && ( numRead = is.read( bytes, offset, bytes.length - offset ) ) >= 0 ) {
                offset += numRead;
            }
            if ( offset < bytes.length ) {
                throw new IOException( "Could not completely read file " + file.getName() );
            }

            // Close the input stream and return bytes
            is.close();
            dto.setObject( bytes );
            return dto;
        }
        catch ( Exception e ) {
            return null;
        }
    }


    /*
    protected void split( Pen pen, MediaDTO base ) throws IOException
    {
        Iterator it = pen.split();
        int nIndex = 0;
        while ( it != null && it.hasNext() ) {
            Pen splittedPen = ( Pen )it.next();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            splittedPen.write( out );
            nIndex++;
            MediaDTO dto = createMedia( base, out.toByteArray(), "part_" + nIndex );
            getPgcs().add( dto );
            out.close();
        }
    }
    */

    public List<MediaDTO> getPgcs()
    {
        if ( pgcs == null )
            pgcs = new ArrayList<MediaDTO>();
        return pgcs;
    }


    protected AnodeFacade getRemoteSession()
    {
        try {
            return ( AnodeFacade )ServiceLocator.getInstance().getRemoteSession( AnodeFacade.class );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }


    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = getRemoteSession();
        return session;
    }


    public void persist() throws ApplicationException
    {
        FormDTO form;
        PGCDTO pgc = getCurrentPgc();
        List<String> addresses = getPageAddresess();
        PGCDTO insertedPgc = getSession().add( getCurrentPgc(), getPenId(), addresses );
        String locCoord[];

        setCurrentPgc( insertedPgc );
        if ( insertedPgc.getPgcStatus().getId() != PgcStatusDTO.statusOk )
            return;
        List<PgcPenPageDTO> pgcsPenPage = getSession().getPgcPenPages( insertedPgc );
        if ( SysUtils.isEmpty( pgcsPenPage ) )
            return;
        /*
         * All pgcpenpage MUST belong to one and one only form
         */
        form = pgcsPenPage.get( 0 ).getForm();
        if ( getPad() == null )
            setPad( new PadFile( form ) );
        if ( getPad().isRegistered( form ) == false )
            getPad().register( form );
        setCurrentPen( PadFile.getPen( pgc.getMedia().getObject(), form.getApplication() ) );
        try {
            setObject( getPad(), pgc.getMedia().getObject() );
            processProperties();
            processPGC( pgcsPenPage.get( 0 ) );
        }
        catch ( PenCreationException e ) {
            return;
        }
        catch ( NoSuchPropertyException e ) {
            return;
        }
    }


    protected void processProperties() throws NoSuchPropertyException
    {
        Iterator it;

        it = getCurrentPen().getPropertyIds();
        while ( it != null && it.hasNext() ) {
            Short obj = ( ( Short )it.next() );
            String[] values = getCurrentPen().getProperty( obj );
            if ( values != null && values.length > 0 ) {

            }
        }
    }

    protected void processPGC( PgcPenPageDTO pgcPenPage )
    {
        List<AnotoBook> books = null;
        List<Page> pages = null;
        int nPageIndex;
        int nBookIndex = 0;

        books = getBooks();
        pages = null;
        if ( SysUtils.isEmpty( books ) ) {
            pages = getPages();
            if ( SysUtils.isEmpty( pages ) )
                return;
            nPageIndex = 0;
            for ( Page page : pages ) {
                processPage( pgcPenPage, 0, nPageIndex, page );
                nPageIndex++;
            }
        }
        else {
            for ( AnotoBook book : books ) {
                nPageIndex = 0;
                for ( Page page : book.getPages() ) {
                    processPage( pgcPenPage, nBookIndex, nPageIndex, page );
                    nPageIndex++;
                }
                nBookIndex++;
            }
        }
    }


    protected void processPage( PgcPenPageDTO pgcPenPage, int nBookIndex, int nPageIndex, Page page )
    {
        String basePath;


        basePath =
                String.format( "%s/%s/%s/%d", pgcPenPage.getForm().getApplication(), pgcPenPage.getPenPage().getPage().getPad().getMedia()
                               .getName(), page.getPageAddress(), pgcPenPage.getPgc().getId() );
        basePath = PadFile.getPath( basePath );
        File file = new File( basePath );
        if ( file.exists() == false )
            file.mkdirs();
        try {
            PgcPageDTO dto = new PgcPageDTO( pgcPenPage.getPgc(), nBookIndex, nPageIndex );
            getSession().add( dto );
            addAnotoImages( dto, pgcPenPage, page, basePath, nBookIndex, nPageIndex );
            addFields( dto, page, basePath );
            addAttachments( dto, page );
        }
        catch ( Exception e ) {
            return;
        }
    }

    protected void addAnotoImages( PgcPageDTO pgcPage, PgcPenPageDTO pgcPenPage, Page page, String basePath, int nBookIndex,
                                   int nPageIndex ) throws RenderException, NotAllowedException, ApplicationException
    {
        Renderer renderer;
        List<MediaDTO> backgroundImages = loadBackgroundImages( pgcPenPage, page );
        String renderedImage = basePath + "/renderedImage." + getImageFileTypeExtension();

        renderer = RendererFactory.create( page );
        if ( SysUtils.isEmpty( backgroundImages ) ) {
            renderer.renderToFile( renderedImage, 200 );
        }
        else {
            for ( MediaDTO image : backgroundImages ) {
                String imagePath = basePath + "/background";
                byte[] object = getSession().getObject( image );
                renderer.setBackground( getPad().saveBackgroundImage( imagePath, image.getName(), object ) );
                renderer.renderToFile( renderedImage, 200 );
                MediaDTO media = createMedia( renderedImage );
                if ( media != null )
                    getSession().addProcessedImage( pgcPenPage.getPgc(), media, nBookIndex, nPageIndex );
            }
        }
        File file = new File ( renderedImage );
        if ( file.exists() )
            file.delete ();
    }

    protected void addAttachments( PgcPageDTO pgcPage, Page page ) throws PageException, ApplicationException
    {
        PgcAttachmentDTO dto;

        if ( page.hasAttachments() ) {
            Iterator it = page.getAttachments();
            while ( it != null && it.hasNext() ) {
                Attachment obj = ( Attachment )it.next();
                dto = new PgcAttachmentDTO( pgcPage );
                if ( obj.getType() == Attachment.ATTACHMENT_TYPE_BARCODE ) {
                    dto.setType( 1 );
                    byte[] barCodeData = obj.getData();
                    dto.setBarcodeType( ( int )barCodeData[ 0 ] );
                    String sValue = "";
                    Byte caracter;
                    for ( int nCount = 1; nCount < barCodeData.length; nCount++ ) {
                        caracter = barCodeData[ nCount ];
                        sValue += caracter.toString();
                    }
                    if ( dto.getBarcodeType() == 2 ) {
                        if ( sValue.length() > 12 )
                            sValue = sValue.substring( 0, 12 );
                    }
                    dto.setValue( sValue );
                }
                else
                    dto.setType( 2 );
                getSession().addPgcAttachment( dto );
            }
        }
    }

    protected void addFields( PgcPageDTO pgcPage, Page page, String basePath ) throws ApplicationException, PageAreaException
    {
        Iterator it = page.getPageAreas();
        PageArea pageArea = null;
        PgcFieldDTO fieldDTO;
        int fieldIndex = 0;

        while ( it != null && it.hasNext() ) {
            pageArea = ( PageArea )it.next();
            if ( pageArea.getType() == PageArea.DRAWING_AREA )
                continue;
            if ( pageArea.getType() != PageArea.USER_AREA )
                continue;
            fieldDTO = new PgcFieldDTO( pgcPage );
            fieldDTO.setName( pageArea.getName() );
            fieldDTO.setType( pageArea.getType() );
            fieldDTO.setHasPenstrokes( pageArea.hasPenStrokes() );
            pageArea.getType();
            if ( fieldDTO.getHasPenstrokes() ) {
                Long minTime = 0L, maxTime = 0L;
                PenStrokes pss = pageArea.getPenStrokes();
                Iterator strokesIt = pss.getIterator();
                while ( strokesIt != null && strokesIt.hasNext() ) {
                    PenStroke ps = ( PenStroke )strokesIt.next();
                    if ( maxTime < ps.getEndTime() )
                        maxTime = ps.getEndTime();
                    if ( minTime == 0 || minTime > ps.getStartTime() )
                        minTime = ps.getStartTime();
                }
                fieldDTO.setStartTime( minTime );
                fieldDTO.setEndTime( maxTime );
                try {
                    getFieldImage( basePath, pageArea, fieldDTO );
                    //getFieldIcrText( basePath, pageArea, fieldDTO, fieldIndex );
                    fieldIndex ++;
                }
                catch ( Exception e ) {
                    System.out.println ( e.getMessage() );
                }
            }
            getSession().addPgcField( fieldDTO );
        }
    }

    protected void getFieldImage ( String basePath, PageArea pageArea, PgcFieldDTO fieldDTO ) throws RenderException,
                                                                NotAllowedException
    {
        Renderer renderer;
        String path;

        renderer = RendererFactory.create( pageArea );
        path = basePath + "/" + "field." + getImageFileTypeExtension();
        renderer.renderToFile( path, 200 );
        MediaDTO media = createMedia( path );
        fieldDTO.setMedia( media );
        File file = new File ( path );
        if ( file.exists() )
            file.delete();
    }

    protected void getFieldIcrText ( String basePath, PageArea pageArea, PgcFieldDTO fieldDTO, int fieldIndex ) throws RenderException,
                                                            NotAllowedException
    {
        Renderer renderer;
        renderer = RendererFactory.create( pageArea );
        renderer.useForce( false );
        String filename = String.format ( "%s\\%03d_%03d_%03d_%03d.%s" ,
                    basePath,
                    fieldDTO.getPgcPage().getPgc().getId(),
                    fieldDTO.getPgcPage().getBookId(),
                    fieldDTO.getPgcPage().getPageId(),
                    fieldIndex,
                    "jpg");
        renderer.renderToFile( filename, 300 );
        String fieldValue = getFieldValue ( filename );
    }

    protected String getFieldValue ( String filename )
    {
        String value = null;
        return null;
    }


    protected List<MediaDTO> loadBackgroundImages( PgcPenPageDTO pgcPenPage, Page page )
    {
        List<MediaDTO> medias = null;


        if ( page == null )
            return medias;
        try {
            AnotoPageDTO dto = new AnotoPageDTO( pgcPenPage.getPenPage().getPage().getPad(), page.getPageAddress() );
            medias = getSession().getImages( dto );
            return medias;
        }
        catch ( ApplicationException e ) {
            return medias;
        }
    }

    protected List<String> getPageAddresess()
    {
        Iterator it = getCurrentPen().getPageAddresses();
        List<String> addresses = null;
        while ( it != null & it.hasNext() ) {
            if ( addresses == null )
                addresses = new ArrayList<String>();
            addresses.add( ( String )it.next() );
        }
        return addresses;
    }


    public void setCurrentPgc( PGCDTO currentPgc )
    {
        this.currentPgc = currentPgc;
        try {
            getCurrentPgc().setPenId( getCurrentPen().getPenData().getPenSerial() );
            getCurrentPgc().setTimeDiff( getCurrentPen().getPenData().getTimeDiff() );
        }
        catch ( Exception e ) {
            e = null;
        }
    }

    public PGCDTO getCurrentPgc()
    {
        return currentPgc;
    }

    protected void setCurrentPen( Pen currentPen )
    {
        this.currentPen = currentPen;
    }

    public Pen getCurrentPen()
    {
        return currentPen;
    }

    public void setObject( PadFile pad, byte[] object ) throws PenCreationException
    {
        setPad( pad );
        bytePgc = object;
        setCurrentPen( pad.getPen( new ByteArrayInputStream( bytePgc ) ) );
    }

    protected void setPad( PadFile pad )
    {
        this.pad = pad;
    }

    protected PadFile getPad()
    {
        return pad;
    }


    public int getBookCount()
    {
        try {
            Iterator it = getCurrentPen().getLogicalBooks();
            Iterator obj;
            int nCount = 0;
            while ( it != null && it.hasNext() ) {
                obj = ( Iterator )it.next();
                nCount++;
            }
            return nCount;
        }
        catch ( Exception e ) {
            e = null;
            return 0;
        }
    }

    public List<AnotoBook> getBooks()
    {
        List<AnotoBook> books = null;

        Iterator bookIterator;
        try {
            bookIterator = getCurrentPen().getLogicalBooks();
            while ( bookIterator != null && bookIterator.hasNext() ) {
                if ( books == null )
                    books = new ArrayList<AnotoBook>();
                AnotoBook ab = new AnotoBook( ( Iterator )bookIterator.next() );
                books.add( ab );
            }
            return books;
        }
        catch ( Exception e ) {
            return books;
        }
    }

    public List<Page> getPages()
    {
        Iterator it;
        List<Page> list = null;

        try {
            it = getCurrentPen().getPages();
            while ( it != null && it.hasNext() ) {
                if ( list == null )
                    list = new ArrayList<Page>();
                list.add( ( Page )it.next() );
            }
        }
        catch ( PageException e ) {
            list = null;
        }
        return list;
    }

    protected Iterator getBook( int nIndex )
    {
        if ( nIndex < 0 )
            return null;
        Iterator book = null;
        Iterator it;
        try {
            it = getCurrentPen().getLogicalBooks();
        }
        catch ( Exception e ) {
            return null;
        }
        while ( it != null && it.hasNext() && nIndex >= 0 ) {
            book = ( Iterator )it.next();
            nIndex--;
        }
        if ( nIndex >= 0 )
            return null;
        return book;
    }

    public Page getPage( int book, int nIndex )
    {
        Iterator bookIt = getBook( book );
        Page page = null;
        page = getPage( bookIt, nIndex );
        return page;
    }


    public Page getPage( Iterator book, int nIndex )
    {
        Page page = null;
        if ( book == null )
            return getPage( nIndex );
        while ( book.hasNext() && nIndex >= 0 ) {
            page = ( Page )book.next();
            nIndex--;
        }
        if ( nIndex >= 0 )
            return null;
        return page;
    }

    public Page getPage( int nIndex )
    {
        Page page = null;
        Iterator it;
        try {
            it = getCurrentPen().getPages();
        }
        catch ( PageException e ) {
            return null;
        }
        while ( it != null && it.hasNext() && nIndex >= 0 ) {
            page = ( Page )it.next();
            nIndex--;
        }
        return page;
    }

    public int getPageCount( int nBook )
    {
        int nIndex = 0;

        Iterator book = getBook( nBook );
        if ( book != null ) {
            while ( book.hasNext() ) {
                book.next();
                nIndex++;
            }
        }
        else {
            try {
                Iterator it = getCurrentPen().getPages();
                while ( it != null && it.hasNext() ) {
                    it.next();
                    nIndex++;
                }
            }
            catch ( PageException e ) {
                nIndex = 0;
            }
        }
        return nIndex;
    }

    public boolean hasLogicalBooks()
    {
        Iterator books;
        try {
            books = getCurrentPen().getLogicalBooks();
            return ( books != null && books.hasNext() );
        }
        catch ( Exception e ) {
            return false;
        }
    }

    public void setImageFileTypeExtension( String imageFileType )
    {
        this.imageFileTypeExtension = imageFileType;
    }

    public String getImageFileTypeExtension()
    {
        return imageFileTypeExtension;
    }
}
