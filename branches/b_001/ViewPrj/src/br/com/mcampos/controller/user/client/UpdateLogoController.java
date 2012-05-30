package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.system.ImageUtil;
import br.com.mcampos.util.system.UploadMedia;

import java.io.IOException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;


public class UpdateLogoController extends LoggedBaseController
{
    private Button cmdSave;
    private Button cmdCancel;
    private Window rootParent;
    private Toolbarbutton cmdUpload;
    private MediaDTO uploadedMedia = null;
    private ClientDTO client;
    private Image imageClienteLogo;
    private ClientFacade clientSession;


    public UpdateLogoController( char c )
    {
        super( c );
    }

    public UpdateLogoController()
    {
        super();
    }

    public void onClick$cmdSave() throws ApplicationException
    {
        if ( uploadedMedia != null ) {
            getSession().setLogo( getLoggedInUser(), client, uploadedMedia );
        }
        unloadMe();
    }

    public void onClick$cmdCancel()
    {
        unloadMe();
    }

    private void unloadMe()
    {
        rootParent.setVisible( false );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        client = ( ClientDTO )rootParent.getAttribute( "client" );
        setLabel( cmdSave );
        setLabel( cmdCancel );
        setLabel( cmdUpload );
    }

    public void onUpload$cmdUpload( UploadEvent evt )
    {
        try {
            uploadedMedia = UploadMedia.getMedia( evt.getMedia() );
            if ( uploadedMedia != null ) {
                ImageUtil.loadImage( imageClienteLogo, uploadedMedia );
            }
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "UploadMedia" );
        }
    }

    public ClientFacade getClientSession()
    {
        return clientSession;
    }

    public ClientFacade getSession()
    {
        if ( clientSession == null )
            clientSession = ( ClientFacade )getRemoteSession( ClientFacade.class );
        return clientSession;
    }

}
