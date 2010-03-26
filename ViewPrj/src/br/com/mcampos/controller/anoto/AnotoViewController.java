package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;

import com.anoto.api.Bounds;
import com.anoto.api.IllegalValueException;
import com.anoto.api.Page;
import com.anoto.api.PageArea;
import com.anoto.api.PageAreaException;
import com.anoto.api.PageException;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;
import com.anoto.api.Renderer;
import com.anoto.api.RendererFactory;

import java.awt.Label;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Area;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Imagemap;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treeitem;


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
        pgcImage.addEventListener( Events.ON_CLICK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    pgcImageAreaClick( ( MouseEvent )event );
                }
            } );
        if ( dtoParam != null ) {
            showPgc( dtoParam );
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

    protected void showPgc( PgcPenPageDTO dto )
    {
        byte[] pgc;

        try {
            /*Obter o pgc*/
            pgc = getSession().getObject( getLoggedInUser(), dto.getPgc().getMedia() );
            List<MediaDTO> medias = getSession().getImages( getLoggedInUser(), dto.getPenPage().getPage() );
            MediaDTO background = null;
            if ( medias.size() > 0 ) {
                background = medias.get( 0 );
            }
            loadImage( dto.getPenPage().getPage().getPad().getForm(), dto.getPenPage().getPage(), pgc, background, factor );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Mostrar Objeto" );
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Mostrar Objeto" );
        }
    }

    protected byte[] getPad( Treeitem childItem ) throws ApplicationException
    {
        Treeitem parentItem = childItem.getParentItem();
        byte[] bRet = null;

        while ( parentItem != null && ( parentItem.getValue() instanceof PadDTO ) == false )
            parentItem = parentItem.getParentItem();
        if ( parentItem == null )
            return bRet;
        PadDTO dto = ( PadDTO )parentItem.getValue();
        bRet = getSession().getObject( getLoggedInUser(), dto.getMedia() );
        return bRet;
    }

    protected byte[] getPgc( Treeitem childItem ) throws ApplicationException
    {
        Treeitem parentItem = childItem.getParentItem();
        byte[] bRet = null;

        while ( parentItem != null && ( parentItem.getValue() instanceof PgcPenPageDTO ) == false )
            parentItem = parentItem.getParentItem();
        if ( parentItem == null )
            return bRet;
        PgcPenPageDTO dto = ( PgcPenPageDTO )parentItem.getValue();
        bRet = getSession().getObject( getLoggedInUser(), dto.getPgc().getMedia() );
        return bRet;
    }


    protected FormDTO getAppName( Treeitem childItem ) throws ApplicationException
    {
        Treeitem parentItem = childItem.getParentItem();

        while ( parentItem != null && ( parentItem.getValue() instanceof FormDTO ) == false )
            parentItem = parentItem.getParentItem();
        if ( parentItem == null )
            return null;
        FormDTO dto = ( FormDTO )parentItem.getValue();
        return dto;
    }


    protected AnotoPageDTO getPageDTO( Treeitem childItem ) throws ApplicationException
    {
        Treeitem parentItem = childItem.getParentItem();

        while ( parentItem != null && ( parentItem.getValue() instanceof AnotoPageDTO ) == false )
            parentItem = parentItem.getParentItem();
        if ( parentItem == null )
            return null;
        AnotoPageDTO dto = ( AnotoPageDTO )parentItem.getValue();
        return dto;
    }


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


    protected void loadAreas( Page page, BufferedImage bufferedImage ) throws PageException, PageAreaException,
                                                                              IllegalValueException
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
            pgcImage.setContent( bufferedImage );
            if ( fields.getChildren().size() > 0 )
                fields.setSelectedIndex( 0 );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Logical Pages" );
        }
    }

    protected BufferedImage loadImage( FormDTO formDto, AnotoPageDTO pageDTO, byte[] pgc, MediaDTO backGround,
                                       float factor ) throws ApplicationException, IOException
    {
        PadFile file = new PadFile( getLoggedInUser() );
        if ( file.isRegistered( formDto ) == false ) {
            file.register( formDto );
        }
        Pen pen;
        Page page;
        String backgroundImagePath, renderedImagePath;

        try {
            pen = file.getPen( new ByteArrayInputStream( pgc ), formDto.getApplication() );
            try {
                page = pen.getPage( pageDTO.getPageAddress() );
                Renderer renderer = RendererFactory.create( page );
                renderer.useForce( true );
                if ( backGround != null ) {
                    backgroundImagePath = saveBackgroundImage( backGround );
                    renderer.setBackground( backgroundImagePath );
                }
                renderedImagePath = getRenderedImagePath( "rendered_image.png" );
                renderer.renderToFile( renderedImagePath, ( ( int )( page.getBounds().getWidth() * factor ) ),
                                       ( ( int )( page.getBounds().getHeight() * factor ) ) );
                BufferedImage img = loadImage( renderedImagePath );
                loadAreas( page, img );
                return img;
            }
            catch ( Exception e ) {
                showErrorMessage( e.getMessage(), "Criar Anoto Pen" );
            }
        }
        catch ( PenCreationException e ) {
            showErrorMessage( e.getMessage(), "Criar Anoto Pen" );
        }
        return null;
    }

    /*
    protected void registerApplication( FormDTO dto ) throws ApplicationException, IOException
    {
        String path;

        dto.setPads( getSession().getPads( getLoggedInUser(), dto ) );
        for ( PadDTO pad : dto.getPads() ) {
            path = savePad( pad );
            try {
                PenHome.registerPad( dto.getApplication(), path );
            }
            catch ( Exception e ) {
                showErrorMessage( e.getMessage(), "Register Pad" );
            }
        }
    }
    */


    /*
    protected String savePad( PadDTO dto ) throws ApplicationException, IOException
    {
        byte[] pad = getSession().getObject( getLoggedInUser(), dto.getMedia() );
        String path = PadFile.getRegisteredPadPath( );
        File f = new File( path );
        if ( f.exists() == false )
            f.mkdirs();
        path += "/" + dto.getMedia().getName();
        f = new File( path );
        if ( f.exists() == false ) {
            FileOutputStream writer = new FileOutputStream( f );
            writer.write( pad );
            writer.close();
        }
        return path;
    }
    */


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
        /*
         * TODO: OCR SHOULD BE HERE!!!!
         */
        fieldImage.setContent( img );
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
            showPgc( dtoParam );
        }
    }


    public void onClick$btnZoomOut()
    {
        factor -= .1F;
        if ( dtoParam != null ) {
            showPgc( dtoParam );
        }
    }
}
