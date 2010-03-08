package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.model.AnotoViewModel;
import br.com.mcampos.controller.anoto.renderer.AnotoViewRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;

import com.anoto.api.Bounds;
import com.anoto.api.IllegalValueException;
import com.anoto.api.Page;
import com.anoto.api.PageArea;
import com.anoto.api.PageAreaException;
import com.anoto.api.PageException;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;
import com.anoto.api.PenHome;
import com.anoto.api.Renderer;
import com.anoto.api.RendererFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Area;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Imagemap;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;


public class AnotoViewController extends LoggedBaseController
{

    protected Tree tree;
    protected AnodeFacade session;
    protected Imagemap pgcImage;
    protected Checkbox showFields;
    protected Combobox fields;
    protected Row icrInfo;
    protected Row correctedInfo;
    protected Textbox correctedValue;
    protected Label icrValue;


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
        List<FormDTO> list = getSession().getForms( getLoggedInUser() );
        tree.setTreeitemRenderer( new AnotoViewRenderer() );
        tree.setModel( new AnotoViewModel( getSession(), getLoggedInUser(), list ) );
        pgcImage.addEventListener( Events.ON_CLICK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    pgcImageAreaClick( ( MouseEvent ) event );
                }
            } );
    }

    protected void setSession( AnodeFacade session )
    {
        this.session = session;
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade ) getRemoteSession( AnodeFacade.class );
        return session;
    }

    public void onSelect$tree()
    {
        Treeitem item = tree.getSelectedItem();
        Object value = item.getValue();

        if ( value == null )
            return;
        if ( value instanceof PgcPenPageDTO )
            showPgc( item, ( ( PgcPenPageDTO ) value ).getPgc() );
        if ( value instanceof MediaDTO )
            showPgc( item, ( MediaDTO ) value );

    }

    protected void showPgc( Treeitem item, MediaDTO media )
    {
        byte[] pgc;

        try {
            /*Obter o pgc*/
            pgc = getPgc( item );
            AnotoPageDTO pageDto = getPageDTO( item );
            pgcImage.setContent( loadImage( getAppName( item ), pageDto, pgc, media ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Mostrar Objeto" );
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Mostrar Objeto" );
        }

    }

    protected void showPgc( Treeitem item, PGCDTO pgcDto )
    {
        byte[] pgc;

        try {
            /*Obter o pgc*/
            pgc = getSession().getObject( getLoggedInUser(), pgcDto.getMedia() );
            AnotoPageDTO pageDto = getPageDTO( item );
            loadImage( getAppName( item ), pageDto, pgc, null );
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
        PadDTO dto = ( PadDTO ) parentItem.getValue();
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
        PgcPenPageDTO dto = ( PgcPenPageDTO ) parentItem.getValue();
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
        FormDTO dto = ( FormDTO ) parentItem.getValue();
        return dto;
    }


    protected AnotoPageDTO getPageDTO( Treeitem childItem ) throws ApplicationException
    {
        Treeitem parentItem = childItem.getParentItem();

        while ( parentItem != null && ( parentItem.getValue() instanceof AnotoPageDTO ) == false )
            parentItem = parentItem.getParentItem();
        if ( parentItem == null )
            return null;
        AnotoPageDTO dto = ( AnotoPageDTO ) parentItem.getValue();
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
        icrInfo.setVisible( false );
        correctedInfo.setVisible( false );
        int areaId = 1;
        try {
            while ( it.hasNext() ) {
                PageArea pageArea = ( PageArea ) it.next();

                if ( pageArea.getType() != PageArea.DRAWING_AREA ) {
                    Bounds bounds = pageArea.getBounds();
                    String coords = String.format( "%d,%d,%d,%d", ( ( int ) bounds.getX() ), ( ( int ) bounds.getY() ),
                                                   ( ( int ) ( bounds.getX() + bounds.getWidth() ) ),
                                                   ( ( int ) ( bounds.getY() + bounds.getHeight() ) ) );
                    Area area = new Area();
                    area.setTooltiptext( pageArea.getName() );
                    area.setAttribute( pageArea.getName(), pageArea );

                    area.setId( pgcImage.getId() + "_area_" + areaId++ );
                    fields.appendItem( pageArea.getName() ).setValue( bounds );
                    area.setShape( "rect" );
                    area.setCoords( coords );
                    Graphics gd = bufferedImage.getGraphics();
                    gd.setColor( Color.blue );
                    gd.drawRect( ( int ) pageArea.getBounds().getX(), ( int ) pageArea.getBounds().getY(),
                                 ( int ) pageArea.getBounds().getWidth(), ( int ) pageArea.getBounds().getHeight() );
                    gd.setColor( Color.red );
                    gd.drawRect( 0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1 );
                    pgcImage.appendChild( area );
                }
            }
            pgcImage.setContent( bufferedImage );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Logical Pages" );
        }
    }

    protected BufferedImage loadImage( FormDTO formDto, AnotoPageDTO pageDTO, byte[] pgc,
                                       MediaDTO backGround ) throws ApplicationException, IOException
    {
        if ( PenHome.padIsRegistered( formDto.getApplication() ) == false ) {
            registerApplication( formDto );
        }
        Pen pen;
        Page page;
        String backgroundImagePath, renderedImagePath;

        try {
            pen = PenHome.read( new ByteArrayInputStream( pgc ), formDto.getApplication() );
            //pen = PenHome.read( new ByteArrayInputStream( pgc ), "CloudSystems" );
            try {
                page = pen.getPage( pageDTO.getPageAddress() );
                Renderer renderer = RendererFactory.create( page );
                renderer.useForce( true );
                if ( backGround != null ) {
                    backgroundImagePath = saveBackgroundImage( backGround );
                    renderer.setBackground( backgroundImagePath );
                }
                renderedImagePath = getRenderedImagePath( "rendered_image.png" );
                renderer.renderToFile( renderedImagePath );
                System.out.println( "Rendering page: " + page.getPageName() + ". Address: " + page.getPageAddress() );
                BufferedImage img = loadImage( renderedImagePath );
                loadAreas( page, img );
                System.out.println( "Tamanho da imagem: (" + pgcImage.getContent().getWidth() + "," + pgcImage.getContent().getHeight() + ")" );
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

    protected void registerApplication( FormDTO dto ) throws ApplicationException, IOException
    {
        String path;

        for ( PadDTO pad : dto.getPads() ) {
            path = savePad( pad );
            try {
                PenHome.registerPad( dto.getApplication(), path );
                System.out.println( "Registrando pad " + path );
            }
            catch ( Exception e ) {
                showErrorMessage( e.getMessage(), "Register Pad" );
            }
        }
    }

    protected String savePad( PadDTO dto ) throws ApplicationException, IOException
    {
        byte[] pad = getSession().getObject( getLoggedInUser(), dto.getMedia() );
        String path = "t:/temp/anoto/registeredPads";
        File f = new File( path );
        if ( f.exists() == false )
            f.mkdirs();
        path += "/" + dto.getMedia().getName();
        f = new File( path );
        FileOutputStream writer = new FileOutputStream( f );
        writer.write( pad );
        writer.close();
        return path;
    }


    protected String saveBackgroundImage( MediaDTO dto ) throws ApplicationException, IOException
    {
        byte[] pad = getSession().getObject( getLoggedInUser(), dto );
        String path = "t:/temp/anoto/registeredPads";
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
        String path = "t:/temp/anoto/registeredPads";
        File f = new File( path );
        if ( f.exists() == false )
            f.mkdirs();
        path += "/" + szFilename;
        return path;
    }

    public void pgcImageAreaClick( MouseEvent event )
    {
        alert( event.getArea() );
        System.out.println( "onClick event fired" );
    }

    public void onSelect$fields ()
    {
        if ( fields.getSelectedItem() == null ){
            icrInfo.setVisible( false );
            correctedInfo.setVisible( false );
            return;
        }
        icrInfo.setVisible( true );
        correctedInfo.setVisible( true );
        Bounds area = (Bounds) fields.getSelectedItem().getValue();
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
        img = img.getSubimage( ( int )area.getX(), ( int )area.getY(), ( int )area.getWidth(), ( int )area.getHeight() );
        /*
         * TODO: OCR SHOULB BE HERE!!!!
         */
    }
}
