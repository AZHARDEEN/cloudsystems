package br.com.mcampos.controller.anoto;

import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.media.facade.MediaFacade;

import com.anoto.api.Page;
import com.anoto.api.Pen;
import com.anoto.api.PenHome;


import com.anoto.api.Renderer;

import com.anoto.api.RendererFactory;


import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.awt.image.renderable.ParameterBlock;

import java.awt.image.renderable.RenderableImage;

import java.io.ByteArrayInputStream;

import java.io.File;

import java.io.FileOutputStream;

import java.util.List;

import javax.imageio.ImageIO;


import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

public class AnotoPcgController extends LoggedBaseController
{
    private MediaFacade session;
    protected Button cmdCreate;
    protected org.zkoss.zul.Image imgTest;
    protected Listbox listboxRecord;

    public AnotoPcgController()
    {
        super();
    }

    protected MediaFacade getSession()
    {
        if ( session == null )
            session = ( MediaFacade )getRemoteSession( MediaFacade.class );
        return session;
    }


    public void onSelect$listboxRecord()
    {
        MediaDTO media = ( MediaDTO )listboxRecord.getSelectedItem().getValue();
        try {
            //byte[] pad = getSession().getObject( getLoggedInUser(), 15 );
            //byte[] bkg = getSession().getObject( getLoggedInUser(), 19 );
            byte[] pad = getSession().getObject( getLoggedInUser(), 1 );
            byte[] bkg = getSession().getObject( getLoggedInUser(), 3 );
            byte[] pcg = getSession().getObject( getLoggedInUser(), media.getId() );

            ByteArrayInputStream pad_is = new ByteArrayInputStream( pad );
            PenHome.loadPad( "CloudSystems", pad_is, true );
            ByteArrayInputStream pcg_is = new ByteArrayInputStream( pcg );
            Pen pen = PenHome.read( pcg_is );
            pen.setApplicationName( "CloudSystems" );
            String address = pen.getMagicBoxPage();
            // ...and then get that page.
            Page page = pen.getPage( address );
            /*Obtendo as duas imagems*/
            /*Get a planar image*/
            /*Imagem de fundo - formulário*/
            BufferedImage image = ( BufferedImage )page.render();

            HttpServletResponse sr = ( HttpServletResponse )Executions.getCurrent().getNativeResponse();
            WebApp webApp = Executions.getCurrent().getDesktop().getWebApp();
            String rootPath = webApp.getRealPath( "/tmp/anoto/1/background" );
            File file = new File( rootPath );
            checkAndCreateDir( file );
            String path = rootPath + "/anoto.png";
            FileOutputStream out = new FileOutputStream( new File( path ) );
            out.write( bkg );
            out.close();
            Renderer pageRenderer = RendererFactory.create( page );
            pageRenderer.setBackground( path );
            pageRenderer.useForce( true );
            //pageRenderer.setInterpolation( Renderer.INTERPOL_STYLE_KOCHANEK_BARTELS );
            //pageRenderer.setJoin( Renderer.JOIN_STYLE_POLYGON_BEVEL );
            pageRenderer.renderToFile( rootPath + "/rendered.png", 150 );
            //ByteArraySeekableStream bgSource = new ByteArraySeekableStream( bkg );
            //RenderedOp bgRendered = JAI.create( "stream", bgSource );

            /*Imagem da escrita - caneta*/
            //PlanarImage fgRendered = PlanarImage.wrapRenderedImage( image );


            imgTest.setContent( ImageIO.read( new File( rootPath + "/rendered.png" ) ) );
        }
        catch ( Exception e ) {
            try {
                Messagebox.show( e.getMessage() );
            }
            catch ( InterruptedException f ) {
                f = null;
            }
        }
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        List<MediaDTO> medias = getSession().getAllPgc( getLoggedInUser() );

        listboxRecord.setItemRenderer( new MediaListRenderer() );
        listboxRecord.setModel( new ListModelList( medias, true ) );
    }

    private File checkAndCreateDir( File directory ) throws Exception
    {
        if ( !directory.exists() ) {
            directory.mkdirs();
        }
        return directory;
    }

}
