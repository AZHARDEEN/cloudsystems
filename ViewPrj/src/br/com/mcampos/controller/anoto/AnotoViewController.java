package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.AttatchmentGridRenderer;
import br.com.mcampos.controller.anoto.renderer.ComboMediaRenderer;
import br.com.mcampos.controller.anoto.util.AnotoBook;
import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.controller.anoto.util.PgcFile;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.APIException;
import com.anoto.api.Attachment;
import com.anoto.api.Bounds;
import com.anoto.api.IllegalValueException;
import com.anoto.api.Page;
import com.anoto.api.PageArea;
import com.anoto.api.PageAreaException;
import com.anoto.api.PageException;
import com.anoto.api.PenCapabilities;
import com.anoto.api.PenData;
import com.anoto.api.PenStroke;
import com.anoto.api.PenStrokes;
import com.anoto.api.Renderer;
import com.anoto.api.RendererFactory;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Area;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Group;
import org.zkoss.zul.Image;
import org.zkoss.zul.Imagemap;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.event.PagingEvent;


public class AnotoViewController extends AnotoLoggedController
{
    public static final String paramName = "PgcPenPageParam";

    protected Imagemap pgcImage;
    protected Combobox fields;
    protected Row icrInfo;
    protected Row correctedInfo;
    protected Textbox correctedValue;
    protected Label icrValue;
    protected Image fieldImage;

    protected float factor = 1.0F;


    private PgcPenPageDTO dtoParam;
    private PadFile padFile;
    private PgcFile pgcFile;


    protected Grid gridProperties;
    private Paging pagingBooks;
    private Paging pagingPages;
    private Row rowBackGroundImages;
    private Combobox cmbBackgroundImages;
    private Div divCenterImageArea;

    private List<AnotoBook> books;
    private Grid gridAttach;


    public AnotoViewController( char c )
    {
        super( c );
    }

    public AnotoViewController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        cmbBackgroundImages.setItemRenderer( new ComboMediaRenderer() );
        gridAttach.setRowRenderer( new AttatchmentGridRenderer() );
        pgcImage.addEventListener( Events.ON_CLICK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    pgcImageAreaClick( ( MouseEvent )event );
                }
            } );
        if ( dtoParam != null ) {
            getBooks();
        }
    }

    @Override
    public ComponentInfo doBeforeCompose( org.zkoss.zk.ui.Page page, Component parent, ComponentInfo compInfo )
    {
        Object param = getParameter();

        if ( param != null )
            return super.doBeforeCompose( page, parent, compInfo );
        else
            return null;
    }

    protected Object getParameter()
    {
        Object param;

        Map args = Executions.getCurrent().getArg();
        if ( args == null || args.size() == 0 )
            args = Executions.getCurrent().getParameterMap();
        param = args.get( paramName );
        if ( param instanceof PgcPenPageDTO ) {
            dtoParam = ( PgcPenPageDTO )param;
            return dtoParam;
        }
        else
            return null;
    }

    protected void loadProperties( PageArea area )
    {
        if ( gridProperties == null || pagingBooks == null )
            return;
        if ( gridProperties.getRows() == null )
            gridProperties.appendChild( new Rows() );
        if ( gridProperties.getRows().getChildren() != null )
            gridProperties.getRows().getChildren().clear();
        try {
            showPenCapabilities( getPgcFile().getCurrentPen().getPenCapabilities() );
            PenData data = getPgcFile().getCurrentPen().getPenData();

            Date date = new Date( data.getTimeDiff() );
            SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

            addValue( gridProperties.getRows(), "Caneta", data.getPenSerial() );
            addValue( gridProperties.getRows(), "Timediff", "" + df.format( date ) );
            addValue( gridProperties.getRows(), "Software Version", "" + data.getSoftwareVersion() );

            PenStrokes pss = area.getPenStrokes();
            Iterator it = pss.getIterator();
            while ( it != null && it.hasNext() ) {
                PenStroke obj = ( PenStroke )it.next();
                if ( obj != null ) {
                }
            }

        }
        catch ( APIException e ) {
            e = null;
        }
    }

    protected void addValue( Rows target, String label, boolean bValue )
    {
        Row row = new Row();
        row.appendChild( new Label( label ) );
        row.appendChild( new Label( bValue ? "SIM" : "NÃO" ) );
        target.appendChild( row );

    }

    protected void addValue( Rows target, String label, String value )
    {
        Row row = new Row();
        row.appendChild( new Label( label ) );
        row.appendChild( new Label( value ) );
        target.appendChild( row );

    }


    protected void showPenCapabilities( PenCapabilities c )
    {
        addValue( gridProperties.getRows(), "canAuthenticate", c.canAuthenticate() );
        addValue( gridProperties.getRows(), "canConfirm", c.canConfirm() );
        addValue( gridProperties.getRows(), "canIcr", c.canIcr() );
        addValue( gridProperties.getRows(), "canRedirectAnywhere", c.canRedirectAnywhere() );
        addValue( gridProperties.getRows(), "canShowHtml", c.canShowHtml() );
        addValue( gridProperties.getRows(), "canShowText", c.canShowText() );
        addValue( gridProperties.getRows(), "canShowWml", c.canShowWml() );
    }

    protected void getBooks()
    {
        books = getPgcFile().getBooks();

        pagingBooks.setTotalSize( SysUtils.isEmpty( books ) == false ? books.size() : 0 );
        pagingBooks.setActivePage( 0 );
        pagingBooks.setPageSize( 1 );
        pagingBooks.setAutohide( true );
        configurePages( books == null ? null : books.get( 0 ) );

    }

    protected void loadBackgroundImages( Page page )
    {
        List<MediaDTO> medias;


        cmbBackgroundImages.getChildren().clear();
        if ( page == null )
            return;
        try {
            AnotoPageDTO dto = new AnotoPageDTO( dtoParam.getPenPage().getPage().getPad(), page.getPageAddress() );
            medias = getSession().getImages( getLoggedInUser(), dto );
            rowBackGroundImages.setVisible( !SysUtils.isEmpty( medias ) );
            for ( MediaDTO media : medias ) {
                Comboitem item = new Comboitem( media.toString() );
                item.setValue( media );
                cmbBackgroundImages.appendChild( item );
            }
            if ( cmbBackgroundImages.getItemCount() > 0 ) {
                cmbBackgroundImages.setSelectedIndex( 0 );
                onSelect$cmbBackgroundImages();
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Imagens de Fundo" );
        }
    }

    /*
    protected void showPgc( )
    {
        if ( getPgcFile() == null )
            return;
        getBooks ();
    }
    */

    protected BufferedImage loadImage( byte[] byteImage )
    {
        try {
            return ImageIO.read( new ByteArrayInputStream( byteImage ) );
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Carregar Imagem de Fundo" );
            return null;
        }
    }

    protected BufferedImage loadImage( String szName )
    {
        try {
            return ImageIO.read( new File( szName ) );
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Carregar Imagem de Fundo" );
            return null;
        }
    }


    protected void loadAreas( Page page ) throws PageException, PageAreaException, IllegalValueException
    {
        Iterator it = page.getPageAreas();
        pgcImage.getChildren().clear();
        fields.getChildren().clear();
        fields.setSelectedItem( null );
        int areaId = 1;
        try {
            while ( it.hasNext() ) {
                PageArea pageArea = ( PageArea )it.next();
                if ( pageArea.getType() != PageArea.DRAWING_AREA ) {

                    if ( pageArea.getType() != PageArea.USER_AREA )
                        continue;
                    Bounds bounds = pageArea.getBounds();
                    String coords =
                        String.format( "%d,%d,%d,%d", ( ( int )bounds.getX() ), ( ( int )bounds.getY() ), ( ( int )( bounds.getX() +
                                                                                                                     bounds.getWidth() ) ),
                                       ( ( int )( bounds.getY() + bounds.getHeight() ) ) );
                    Area area = new Area();
                    area.setTooltiptext( pageArea.getName() );
                    area.setId( pgcImage.getId() + "_area_" + areaId++ );

                    Comboitem item = new Comboitem( pageArea.getName() );
                    item.setValue( pageArea );
                    area.setAttribute( "field", item );
                    fields.appendChild( item );

                    area.setAttribute( pageArea.getName(), pageArea );
                    area.setShape( "rect" );
                    area.setCoords( coords );
                    /*
                    if ( showFields.isChecked() ) {
                        Graphics gd = bufferedImage.getGraphics();
                        gd.setColor( Color.blue );
                        gd.drawRect( ( int )pageArea.getBounds().getX(), ( int )pageArea.getBounds().getY(),
                                     ( int )pageArea.getBounds().getWidth(), ( int )pageArea.getBounds().getHeight() );
                        gd.setColor( Color.red );
                        gd.drawRect( 0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1 );
                    }*/
                    pgcImage.appendChild( area );
                }
            }
            if ( fields.getChildren().size() > 0 ) {
                fields.setSelectedIndex( 0 );
                onSelect$fields();
            }
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Logical Pages" );
        }
    }


    protected String saveBackgroundImage( MediaDTO dto ) throws ApplicationException, IOException
    {
        byte[] pad = getSession().getObject( getLoggedInUser(), dto );
        String path = PadFile.getAnotoUserPath( getLoggedInUser().getSessionId().hashCode() );
        File f = new File( path );
        if ( f.exists() == false )
            f.mkdirs();
        path += "/" + "background.png";
        f = new File( path );
        FileOutputStream writer = new FileOutputStream( f );
        writer.write( pad );
        writer.close();
        return path;
    }

    protected String getRenderedImagePath( String szFilename ) throws ApplicationException, IOException
    {
        String path = PadFile.getAnotoUserPath( getLoggedInUser().getSessionId().hashCode() );
        File f = new File( path );
        if ( f.exists() == false )
            f.mkdirs();
        path += "/" + szFilename;
        return path;
    }

    public void pgcImageAreaClick( MouseEvent event )
    {
        if ( event == null )
            return;

        List<Area> areas = ( List<Area> )pgcImage.getChildren();
        for ( Area area : areas ) {
            if ( area.getId().equals( event.getArea() ) == true ) {
                Object obj = area.getAttribute( "field" );
                if ( obj != null ) {
                    fields.setSelectedItem( ( Comboitem )obj );
                    onSelect$fields();
                }
                break;
            }
        }

    }

    public void onSelect$fields()
    {
        if ( fields.getSelectedItem() == null ) {
            return;
        }
        PageArea area = ( PageArea )fields.getSelectedItem().getValue();
        if ( area == null )
            return;
        Page page = getPage();
        try {

            if ( page.hasPenStrokes() ) {
                fieldImage.setVisible( true );
                fieldImage.setContent( ( BufferedImage )area.render() );
            }
            else {
                fieldImage.setVisible( false );
            }
        }
        catch ( Exception e ) {
            fieldImage.setVisible( false );
        }
        loadProperties( area );
        /*
        AImage aImage = ( AImage )pgcImage.getContent();
        BufferedImage img;
        try {
            img = ImageIO.read( aImage.getStreamData() );
        }
        catch ( IOException e ) {
            e = null;
            img = null;
        }
        if ( img == null )
            return;
        Bounds bounds = area.getBounds();
        fieldImage.setWidth( "" + ( int )bounds.getWidth() );
        fieldImage.setWidth( "" + ( int )bounds.getHeight() );
        img = img.getSubimage( ( int )bounds.getX(), ( int )bounds.getY(), ( int )bounds.getWidth(), ( int )bounds.getHeight() );
        fieldImage.setContent( img );
        */
        /*
         * TODO: OCR SHOULD BE HERE!!!!
         */
    }

    public void onOK$correctedValue()
    {
        int nIndex = fields.getSelectedIndex();

        nIndex++;
        if ( nIndex >= fields.getItemCount() )
            nIndex = 0;
        fields.setSelectedIndex( nIndex );
        onSelect$fields();
        correctedValue.setFocus( true );
    }

    public void onClick$btnZoomIn()
    {
        factor += .1F;
        if ( dtoParam != null ) {
            showPage();
        }
    }


    public void onClick$btnZoomOut()
    {
        factor -= .1F;
        if ( dtoParam != null ) {
            showPage();
        }
    }

    protected PadFile getPadFile()
    {
        if ( padFile == null )
            padFile = new PadFile( getLoggedInUser(), dtoParam.getPenPage().getPage().getPad().getForm() );
        return padFile;
    }

    protected void setPgcFile( PgcFile pgcFile )
    {
        this.pgcFile = pgcFile;
    }

    protected PgcFile getPgcFile()
    {
        if ( pgcFile == null ) {
            pgcFile = new PgcFile();
            try {
                pgcFile.setObject( getPadFile(), getSession().getObject( getLoggedInUser(), dtoParam.getPgc().getMedia() ) );
            }
            catch ( Exception e ) {
                showErrorMessage( e.getMessage(), "Erro lendo dados do PGC" );
                pgcFile = null;
            }
        }
        return pgcFile;
    }

    public void onPaging$pagingBooks( ForwardEvent evt )
    {
        PagingEvent pageEvent = ( PagingEvent )evt.getOrigin();

        configurePages( books.get( pageEvent.getActivePage() ) );
    }


    protected void configurePages( AnotoBook book )
    {
        if ( book != null ) {
            pagingPages.setTotalSize( book.getPageCount() );
            pagingPages.setActivePage( 0 );
            pagingPages.setAutohide( true );
            pagingPages.setPageSize( 1 );
        }
        else {
            pagingPages.setTotalSize( getPgcFile().getPageCount( 0 ) );
            pagingPages.setActivePage( 0 );
            pagingPages.setAutohide( true );
            pagingPages.setPageSize( 1 );
        }
        loadBackgroundImages( getPage() );
        showPage();
    }


    public void onPaging$pagingPages( ForwardEvent evt )
    {
        PagingEvent pgEvt = ( PagingEvent )evt.getOrigin();

        pagingPages.setActivePage( pgEvt.getActivePage() );
        loadBackgroundImages( getPage() );
        showPage();
    }


    protected Page getPage()
    {
        int book = pagingBooks.getActivePage();
        int page = pagingPages.getActivePage();
        Page pageObject;

        if ( books != null )
            pageObject = books.get( book ).getPages().get( page );
        else
            pageObject = getPgcFile().getPage( page );
        return pageObject;
    }


    protected void showPage()
    {
        Page page = getPage();

        try {
            if ( page != null && page.hasPenStrokes() ) {
                divCenterImageArea.setVisible( true );
                rowBackGroundImages.setVisible( true );
                render( page );
            }
            else {
                divCenterImageArea.setVisible( false );
                rowBackGroundImages.setVisible( false );
            }
        }
        catch ( PageException e ) {
            divCenterImageArea.setVisible( false );
            rowBackGroundImages.setVisible( false );
        }
    }


    protected MediaDTO getBackgroundMedia()
    {
        if ( rowBackGroundImages.isVisible() == false )
            return null;
        Comboitem item = cmbBackgroundImages.getSelectedItem();
        if ( item == null ) {
            if ( cmbBackgroundImages.getItemCount() > 1 )
                cmbBackgroundImages.setSelectedIndex( 0 );
            item = cmbBackgroundImages.getSelectedItem();
            if ( item == null ) {
                if ( cmbBackgroundImages.getModel() == null )
                    return null;
                ListModelList list = ( ListModelList )cmbBackgroundImages.getModel();
                if ( list.getSize() < 1 )
                    return null;
                return ( MediaDTO )list.get( 0 );
            }
        }
        return ( MediaDTO )item.getValue();
    }

    protected void render( Page page )
    {
        Renderer renderer;
        String backgroundImagePath, renderedImagePath;

        MediaDTO backGround = getBackgroundMedia();
        try {
            renderer = RendererFactory.create( page );
            //renderer.useForce( true );
            if ( backGround != null ) {
                backgroundImagePath = saveBackgroundImage( backGround );
                renderer.setBackground( backgroundImagePath );
            }
            renderedImagePath = getRenderedImagePath( "rendered_image.png" );
            renderer.renderToFile( renderedImagePath, ( ( int )( page.getBounds().getWidth() * factor ) ),
                                   ( ( int )( page.getBounds().getHeight() * factor ) ) );
            BufferedImage img = loadImage( renderedImagePath );
            pgcImage.setContent( img );
            loadAreas( page );
            loadAttachments( page );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Erro ao renderizar Imagem" );
        }

    }

    protected void loadAttachments( Page page ) throws PageException
    {
        if ( gridAttach.getRows() != null )
            gridAttach.getRows().getChildren().clear();
        if ( page == null )
            return;
        if ( page.hasAttachments() ) {
            Iterator it = page.getAttachments();
            List<Attachment> attachments = null;
            while ( it != null && it.hasNext() ) {
                Attachment obj = ( Attachment )it.next();
                if ( obj != null ) {
                    if ( attachments == null )
                        attachments = new ArrayList<Attachment>();
                    attachments.add( obj );
                }
            }
            if ( attachments != null )
                gridAttach.setModel( new ListModelList( attachments ) );
        }
    }

    public void onSelect$cmbBackgroundImages()
    {
        showPage();
    }
}
