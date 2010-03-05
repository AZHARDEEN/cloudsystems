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

import com.anoto.api.Page;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;
import com.anoto.api.PenHome;
import com.anoto.api.Renderer;
import com.anoto.api.RendererFactory;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;


import java.util.List;

import javax.imageio.ImageIO;


import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Image;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;


public class AnotoViewController extends LoggedBaseController
{

    private Tree tree;
    private AnodeFacade session;
    private Image image;


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
    }

    protected void setSession( AnodeFacade session )
    {
        this.session = session;
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }

    public void onSelect$tree()
    {
        Treeitem item = tree.getSelectedItem();
        Object value = item.getValue();

        if ( value == null )
            return;
        if ( value instanceof PgcPenPageDTO )
            showPgc( item, ( ( PgcPenPageDTO )value ).getPgc() );
        if ( value instanceof MediaDTO )
            showPgc( item, ( MediaDTO )value );

    }

    protected void showPgc( Treeitem item, MediaDTO media )
    {
        byte[] pgc;

        try {
            /*Obter o pgc*/
            pgc = getPgc( item );
            AnotoPageDTO pageDto = getPageDTO( item );
            image.setContent( loadImage( getAppName( item ), pageDto, pgc, media ) );
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
            image.setContent( loadImage( getAppName( item ), pageDto, pgc, null ) );
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

        while ( parentItem != null && ( parentItem.getValue() instanceof PadDTO ) == false )
            parentItem = parentItem.getParentItem();
        if ( parentItem == null )
            return null;
        PadDTO dto = ( PadDTO )parentItem.getValue();
        return getSession().getObject( getLoggedInUser(), dto.getMedia() );
    }

    protected byte[] getPgc( Treeitem childItem ) throws ApplicationException
    {
        Treeitem parentItem = childItem.getParentItem();

        while ( parentItem != null && ( parentItem.getValue() instanceof PgcPenPageDTO ) == false )
            parentItem = parentItem.getParentItem();
        if ( parentItem == null )
            return null;
        PgcPenPageDTO dto = ( PgcPenPageDTO )parentItem.getValue();
        return getSession().getObject( getLoggedInUser(), dto.getPgc().getMedia() );
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
            try {
                page = pen.getPage( pageDTO.getPageAddress() );
                Renderer renderer = RendererFactory.create( page );
                if ( backGround != null ) {
                    backgroundImagePath = saveBackgroundImage( backGround );
                    renderer.setBackground( backgroundImagePath );
                }
                renderedImagePath = getRenderedImagePath( "rendered_image.png" );
                renderer.renderToFile( renderedImagePath );
                return loadImage( renderedImagePath );
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

}
