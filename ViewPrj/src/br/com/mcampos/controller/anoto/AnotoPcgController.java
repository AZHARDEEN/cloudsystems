package br.com.mcampos.controller.anoto;

import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.media.facade.MediaFacade;

import com.anoto.api.Page;
import com.anoto.api.Pen;
import com.anoto.api.PenData;
import com.anoto.api.PenHome;


import com.sun.media.jai.codec.ByteArraySeekableStream;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.awt.image.renderable.ParameterBlock;

import java.awt.image.renderable.RenderableImage;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import java.util.List;

import javax.media.jai.JAI;

import javax.media.jai.PlanarImage;
import javax.media.jai.RenderableOp;
import javax.media.jai.RenderedOp;

import org.zkoss.zk.ui.Component;
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
            byte[] pad = getSession().getObject( getLoggedInUser(), 15 );
            byte[] bkg = getSession().getObject( getLoggedInUser(), 19 );
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
            ByteArraySeekableStream bgSource = new ByteArraySeekableStream( bkg );
            RenderedOp bgRendered = JAI.create( "stream", bgSource );

            /*Imagem da escrita - caneta*/
            PlanarImage fgRendered = PlanarImage.wrapRenderedImage( ( BufferedImage )page.render() );


            imgTest.setContent( mergeImages( createRenderable( bgRendered ), createRenderable( fgRendered ) ) );
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


    protected RenderableImage createRenderable( PlanarImage image )
    {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource( image );
        pb.add( null ).add( null ).add( null ).add( null ).add( null );
        return JAI.createRenderable( "renderable", pb );
    }

    protected RenderedImage mergeImages( RenderableImage img1, RenderableImage img2 )
    {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource( img1 );
        pb.addSource( img2 );
        RenderableOp result = JAI.createRenderable( "and", pb );
        return result.createDefaultRendering();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        List<MediaDTO> medias = getSession().getAllPgc( getLoggedInUser() );

        listboxRecord.setItemRenderer( new MediaListRenderer() );
        listboxRecord.setModel( new ListModelList( medias, true ) );
    }
}
