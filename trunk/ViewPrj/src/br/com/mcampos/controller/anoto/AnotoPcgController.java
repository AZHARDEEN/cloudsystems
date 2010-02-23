package br.com.mcampos.controller.anoto;

import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.ejb.cloudsystem.media.facade.MediaFacade;
import br.com.mcampos.exception.ApplicationException;

import com.anoto.api.Page;
import com.anoto.api.Pen;
import com.anoto.api.PenData;
import com.anoto.api.PenHome;

import com.anoto.api.Renderer;

import com.anoto.api.RendererFactory;

import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.RenderingHints;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.zkoss.image.AImage;
import org.zkoss.image.Images;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
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
            byte[] pad = getSession().getObject( getLoggedInUser(), 1 );
            byte[] bkg = getSession().getObject( getLoggedInUser(), 2 );
            byte[] pcg = getSession().getObject( getLoggedInUser(), media.getId() );
            ByteArrayInputStream pad_is = new ByteArrayInputStream( pad );
            PenHome.loadPad( "CloudSystems", pad_is, true );
            ByteArrayInputStream pcg_is = new ByteArrayInputStream( pcg );
            Pen pen = PenHome.read( pcg_is );
            pen.setApplicationName( "CloudSystems" );
            String address = pen.getMagicBoxPage();
            // ...and then get that page.
            Page page = pen.getPage( address );
            PenData penData = pen.getPenData();
            InputStream in = new ByteArrayInputStream( bkg );
            /*Obtendo as duas imagems*/
            BufferedImage fgImage = ( BufferedImage )page.render();
            BufferedImage bgImage = ImageIO.read( in );

            RenderedImage;

            imgTest.setContent( ( BufferedImage )fgImage );
            imgTest.invalidate();
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
}
