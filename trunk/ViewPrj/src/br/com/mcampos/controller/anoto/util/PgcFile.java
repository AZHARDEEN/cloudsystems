package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.controller.anoto.util.icr.A2iaDocument;
import br.com.mcampos.controller.anoto.util.icr.ICRObject;
import br.com.mcampos.controller.anoto.util.icr.IcrField;
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
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.UploadEvent;


public class PgcFile
{
    private static final short KEY_LOCATION_COORDINATES = 16386;

    private String imageFileTypeExtension = "jpg";

    private List<MediaDTO> pgcs;
    private String penId;
    private UploadFacade session;
    private PGCDTO currentPgc;
    private Pen currentPen;
    private PadFile pad;
    private byte[] bytePgc;


    public PgcFile()
    {
        super();
    }

    private void setPenId( String id )
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

    private MediaDTO createMedia( String media )
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
            System.out.println( e.getMessage() );
            return null;
        }
    }


    public List<MediaDTO> getPgcs()
    {
        if ( pgcs == null )
            pgcs = new ArrayList<MediaDTO>();
        return pgcs;
    }


    private UploadFacade getRemoteSession()
    {
        try {
            return ( UploadFacade )ServiceLocator.getInstance().getRemoteSession( UploadFacade.class );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }


    private UploadFacade getSession()
    {
        if ( session == null )
            session = getRemoteSession();
        return session;
    }

    private List<PgcPropertyDTO> getProperties()
    {
        List<PgcPropertyDTO> properties = new ArrayList<PgcPropertyDTO>();
        try {
            Iterator it = getCurrentPen().getPropertyIds();
            while ( it != null && it.hasNext() ) {
                Short obj = ( Short )it.next();
                if ( obj != null ) {
                    PgcPropertyDTO p = new PgcPropertyDTO();
                    p.setId( obj );
                    System.out.println( "Propriedade: " + obj );
                    String[] values = getCurrentPen().getProperty( obj );
                    if ( values != null && values.length > 0 ) {
                        for ( String v : values ) {
                            v = v.replaceAll( "'", "" );
                            if ( SysUtils.isEmpty( v ) )
                                continue;
                            p.add( v );
                        }
                    }
                    properties.add( p );
                }
            }
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            e = null;
        }
        return properties;
    }

    public PGCDTO persist( ArrayList<MediaDTO> medias ) throws ApplicationException
    {
        FormDTO form;
        PGCDTO pgc = getCurrentPgc();

        List<String> addresses = getPageAddresess();
        List<PgcPropertyDTO> properties = getProperties();
        PGCDTO insertedPgc = getSession().add( getCurrentPgc(), addresses, medias, properties );
        setCurrentPgc( insertedPgc );
        if ( insertedPgc.getPgcStatus().getId() != PgcStatusDTO.statusOk )
            return insertedPgc;
        List<PgcPenPageDTO> pgcsPenPage = getSession().getPgcPenPages( insertedPgc );
        if ( SysUtils.isEmpty( pgcsPenPage ) )
            return insertedPgc;
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
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            try {
                getSession().setPgcStatus( insertedPgc, 5 );
            }
            catch ( Exception ex ) {
                System.out.println( ex.getMessage() );
            }
        }
        return insertedPgc;
    }


    private void processProperties() throws NoSuchPropertyException
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

    private void processPGC( PgcPenPageDTO pgcPenPage ) throws ApplicationException
    {
        List<AnotoBook> books = null;
        List<Page> pages = null;
        int nPageIndex;
        int nBookIndex = 0;

        books = getBooks();
        pages = null;
        if ( SysUtils.isEmpty( books ) ) {
            pages = getPages();
            if ( SysUtils.isEmpty( pages ) ) {
                getSession().setPgcStatus( pgcPenPage.getPgc(), 4 );
                return;
            }
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


    private void processPage( PgcPenPageDTO pgcPenPage, int nBookIndex, int nPageIndex, Page page )
    {
        String basePath;
        Map<String, IcrField> icrFields = null;

        basePath =
                String.format( "%s/%s/%s/%d", pgcPenPage.getForm().getApplication(), pgcPenPage.getPenPage().getPage().getPad().getMedia().getName(),
                               page.getPageAddress(), pgcPenPage.getPgc().getId() );
        basePath = PadFile.getPath( basePath );
        File file = new File( basePath );
        if ( file.exists() == false )
            file.mkdirs();
        try {
            PgcPageDTO dto = new PgcPageDTO( pgcPenPage.getPgc(), nBookIndex, nPageIndex );
            dto.setAnotoPage( new AnotoPageDTO( pgcPenPage.getPenPage().getPage().getPad(), page.getPageAddress() ) );
            getSession().add( dto );
            icrFields = processIcr( pgcPenPage, page, basePath );
            addFields( dto, page, basePath, icrFields );
            List<PgcAttachmentDTO> attachs = addAttachments( dto, page );
            addAnotoImages( pgcPenPage, page, basePath, nBookIndex, nPageIndex, attachs );
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            e.printStackTrace();
            return;
        }
        if ( pgcPenPage.getForm().getIcrImage() == false ) {
            if ( file.exists() )
                file.delete();
            file.deleteOnExit();
        }
    }


    private List<PgcAttachmentDTO> getSameBarCodes( Page page, List<PgcAttachmentDTO> attachs, Integer padId,
                                                    int currentPGC ) throws ApplicationException
    {
        List<PgcAttachmentDTO> barCodes = new ArrayList<PgcAttachmentDTO>();
        for ( PgcAttachmentDTO attach : attachs ) {
            if ( attach.getType().equals( PgcAttachmentDTO.typeBarCode ) ) {
                List<PgcAttachmentDTO> list;
                list = getSession().getBarCodes( attach.getValue(), page.getPageAddress(), padId, currentPGC );
                if ( SysUtils.isEmpty( list ) == false ) {
                    for ( PgcAttachmentDTO pa : list ) {
                        if ( barCodes.contains( pa ) == false )
                            barCodes.add( pa );
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
        if ( page == null || SysUtils.isEmpty( attachs ) )
            return;
        List<PgcAttachmentDTO> barCodes = getSameBarCodes( page, attachs, padId, currentPGC );
        if ( SysUtils.isEmpty( barCodes ) )
            return;
        for ( PgcAttachmentDTO barCode : barCodes ) {
            byte[] pgcObject = getSession().getObject( barCode.getPgcPage().getPgc().getMedia() );
            if ( pgcObject == null )
                continue;
            PgcFile file = new PgcFile();
            file.setObject( getPad(), pgcObject );
            Page otherPage = file.getCurrentPen().getPage( page.getPageAddress() );
            if ( otherPage != null )
                page.getPenStrokes().addPenStrokes( otherPage.getPenStrokes() );
        }
    }

    private List<MediaDTO> addAnotoImages( PgcPenPageDTO pgcPenPage, Page page, String basePath, int nBookIndex, int nPageIndex,
                                           List<PgcAttachmentDTO> attachs ) throws RenderException, NotAllowedException,
                                                                                   ApplicationException, PenCreationException
    {
        Renderer renderer;
        MediaDTO media;
        List<MediaDTO> backgroundImages = loadBackgroundImages( pgcPenPage, page );
        String renderedImage = basePath + "/renderedImage." + getImageFileTypeExtension();
        try {
            if ( pgcPenPage.getForm().getConcatenatePgc() )
                verifySameBarcode( page, attachs, pgcPenPage.getPadId(), pgcPenPage.getPgcId() );
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            e = null;
        }

        renderer = RendererFactory.create( page );
        if ( SysUtils.isEmpty( backgroundImages ) ) {
            renderer.renderToFile( renderedImage );
            media = createMedia( renderedImage );
            if ( media != null )
                getSession().addProcessedImage( pgcPenPage.getPgc(), media, nBookIndex, nPageIndex );
        }
        else {
            for ( MediaDTO image : backgroundImages ) {
                String imagePath = basePath;
                byte[] object = getSession().getObject( image );
                String backImage = getPad().saveBackgroundImage( imagePath, image.getName(), object );
                renderer.setBackground( backImage );
                renderer.renderToFile( renderedImage );
                media = createMedia( renderedImage );
                if ( media != null )
                    getSession().addProcessedImage( pgcPenPage.getPgc(), media, nBookIndex, nPageIndex );
                File backFile = new File( backImage );
                if ( backFile.exists() )
                    backFile.delete();
                backFile.deleteOnExit();
            }
        }
        File file = new File( renderedImage );
        if ( SysUtils.isEmpty( pgcPenPage.getForm().getImagePath() ) == false && SysUtils.isEmpty( attachs ) == false ) {
            for ( PgcAttachmentDTO attach : attachs ) {
                if ( attach.getType().equals( PgcAttachmentDTO.typeBarCode ) && SysUtils.isEmpty( attach.getValue() ) == false ) {
                    String targetFilename = pgcPenPage.getForm().getImagePath();

                    if ( targetFilename.endsWith( "/" ) == false && targetFilename.endsWith( "\\" ) == false )
                        targetFilename += "/";
                    File targetDir = new File( targetFilename );
                    if ( targetDir.exists() == false )
                        targetDir.mkdirs();
                    targetFilename += attach.getValue().trim() + "." + getImageFileTypeExtension();
                    File dest = new File( targetFilename );
                    if ( dest.exists() )
                        dest.delete();
                    file.renameTo( dest );
                }
            }
        }
        if ( file.exists() )
            file.delete();
        return backgroundImages;
    }

    private boolean hasTemplate( PgcPenPageDTO pgcPenPage )
    {
        if ( SysUtils.isEmpty( pgcPenPage.getPenPage().getPage().getIcrTemplate() ) )
            return false;
        File icrTemplateFile = new File( pgcPenPage.getPenPage().getPage().getIcrTemplate() );
        if ( icrTemplateFile.exists() == false || icrTemplateFile.isFile() == false )
            return false;
        return true;
    }

    private Map<String, IcrField> processIcr( PgcPenPageDTO pgcPenPage, Page page, String basePath ) throws RenderException,
                                                                                                            NotAllowedException,
                                                                                                            ApplicationException
    {
        Renderer renderer;
        String icrImage = null;
        Map<String, IcrField> icrFields = null;

        if ( pgcPenPage.getForm().getIcrImage() == false ) {
            if ( hasTemplate( pgcPenPage ) == false )
                return icrFields;
        }
        try {
            renderer = RendererFactory.create( page );
            icrImage = String.format( "%s/%s_%s.%s", basePath, pgcPenPage.getPageAddress(), "icr", "JPG" );
            renderer.renderToFile( icrImage, 300 );
            if ( hasTemplate( pgcPenPage ) ) {
                String template = pgcPenPage.getPenPage().getPage().getIcrTemplate();
                icrFields = ICRObject.processImage( template, icrImage, A2iaDocument.typeJPEG );
            }
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            icrFields = null;
        }
        if ( pgcPenPage.getForm().getIcrImage() == false ) {
            File file = new File( icrImage );
            if ( file.exists() )
                file.delete();
        }
        return icrFields;
    }


    private List<PgcAttachmentDTO> addAttachments( PgcPageDTO pgcPage, Page page ) throws PageException, ApplicationException
    {
        PgcAttachmentDTO dto;
        int barcodeType;
        List<PgcAttachmentDTO> attachs = new ArrayList<PgcAttachmentDTO>();

        if ( page.hasAttachments() ) {
            Iterator it = page.getAttachments();
            while ( it != null && it.hasNext() ) {
                Attachment obj = ( Attachment )it.next();
                dto = new PgcAttachmentDTO( pgcPage );
                System.out.println( "O pgc recebido possui anexos. Tipo " + obj.getType() );
                if ( obj.getType() == Attachment.ATTACHMENT_TYPE_BARCODE ) {
                    dto.setType( 1 );
                    byte[] barCodeData = obj.getData();
                    barcodeType = barCodeData[ 0 ];
                    dto.setBarcodeType( barcodeType );
                    String sValue = "";
                    Byte caracter;
                    for ( int nCount = 1; nCount < barCodeData.length; nCount++ ) {
                        caracter = barCodeData[ nCount ];
                        if ( barcodeType == 4 )
                            sValue += ( char )( byte )caracter;
                        else
                            sValue += caracter.toString();

                    }
                    if ( dto.getBarcodeType() == 2 ) {
                        if ( sValue.length() > 12 )
                            sValue = sValue.substring( 0, 12 );
                    }
                    dto.setValue( sValue );
                }
                else {
                    dto.setType( 2 );
                    dto.setValue( obj.getData().toString() );
                }
                getSession().addPgcAttachment( dto );
                attachs.add( dto );
            }
        }
        return attachs;
    }

    private void addFields( PgcPageDTO pgcPage, Page page, String basePath,
                            Map<String, IcrField> icrFields ) throws ApplicationException, PageAreaException
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
            fieldDTO.setType( new FieldTypeDTO( 1 ) );
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
                    fieldIndex++;
                }
                catch ( Exception e ) {
                    System.out.println( "Erro em update Fields" );
                    System.out.println( e.getMessage() );
                    e.printStackTrace();
                }
            }
            if ( icrFields != null ) {
                IcrField field = icrFields.get( fieldDTO.getName() );
                if ( field != null && field.getValue() != null ) {
                    String icrValue = field.getValue().toString();
                    if ( SysUtils.isEmpty( icrValue ) == false ) {
                        icrValue = icrValue.replaceAll( "_", " " );
                        if ( SysUtils.isEmpty( icrValue ) == false ) {
                            fieldDTO.setIrcText( icrValue.trim() );
                        }
                    }
                }
            }
            getSession().addPgcField( fieldDTO );
        }
    }

    private void getFieldImage( String basePath, PageArea pageArea, PgcFieldDTO fieldDTO ) throws RenderException,
                                                                                                  NotAllowedException
    {
        Renderer renderer;
        String path;

        renderer = RendererFactory.create( pageArea );
        path = basePath + "/" + "field." + getImageFileTypeExtension();
        renderer.renderToFile( path, 200 );
        MediaDTO media = createMedia( path );
        fieldDTO.setMedia( media );
        File file = new File( path );
        if ( file.exists() )
            file.delete();
    }

    private List<MediaDTO> loadBackgroundImages( PgcPenPageDTO pgcPenPage, Page page )
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
            System.out.println( e.getMessage() );
            return medias;
        }
    }

    private List<String> getPageAddresess()
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
            System.out.println( e.getMessage() );
            e = null;
        }
    }

    public PGCDTO getCurrentPgc()
    {
        return currentPgc;
    }

    private void setCurrentPen( Pen currentPen )
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

    private void setPad( PadFile pad )
    {
        this.pad = pad;
    }

    private PadFile getPad()
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
            System.out.println( e.getMessage() );
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
            System.out.println( e.getMessage() );
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
            System.out.println( e.getMessage() );
            list = null;
        }
        return list;
    }

    private Iterator getBook( int nIndex )
    {
        if ( nIndex < 0 )
            return null;
        Iterator book = null;
        Iterator it;
        try {
            it = getCurrentPen().getLogicalBooks();
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
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
            System.out.println( e.getMessage() );
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
                System.out.println( e.getMessage() );
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
            System.out.println( e.getMessage() );
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
