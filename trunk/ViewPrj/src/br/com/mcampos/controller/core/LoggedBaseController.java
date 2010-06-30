package br.com.mcampos.controller.core;


import br.com.mcampos.controller.user.person.MyRecord;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.business.LoginLocator;
import br.com.mcampos.util.system.ImageUtil;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;


public class LoggedBaseController extends BaseController
{
    protected static String alternativePath = "/private/index.zul";
    protected static String errorTitleI3 = "ErrorTitle";
    protected LoginLocator loginLocator;

    private Image imageClienteLogo;
    private Image imageCompanyLogo;

    public LoggedBaseController()
    {
        super();
    }

    public LoggedBaseController( char c )
    {
        super( c );
    }


    @Override
    public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
    {
        if ( isUserLogged() == false ) {
            redirect( "/index.zul" );
            return null;
        }
        else {
            AuthenticationDTO user = getLoggedInUser();
            int status;

            try {
                status = getLoginLocator().getStatus( user );
                switch ( status ) {
                case UserStatusDTO.statusFullfillRecord:
                    String path = page.getRequestPath();
                    if ( path.endsWith( "myrecord.zul" ) == false && ( this instanceof MyRecord ) == false ) {
                        redirect( "/private/user/person/myrecord.zul" );
                        return null;
                    }
                    else
                        return super.doBeforeCompose( page, parent, compInfo );
                case UserStatusDTO.statusExpiredPassword:
                    redirect( "/private/change_password.zul" );
                    break;
                default:
                    return super.doBeforeCompose( page, parent, compInfo );
                }
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Erro ao obter status do usuário" );
                redirect( "/index.zul" );
            }
        }
        return null;
    }

    public LoginLocator getLoginLocator()
    {
        if ( loginLocator == null )
            loginLocator = new LoginLocator();
        return loginLocator;
    }

    protected void showErrorMessage( String description )
    {
        showErrorMessage( description, errorTitleI3 );
    }

    protected void showErrorMessage( String description, String titleId )
    {
        try {
            String title = getLabel( titleId );
            if ( SysUtils.isEmpty( title ) )
                title = getLabel( errorTitleI3 );
            Messagebox.show( description, SysUtils.isEmpty( title ) ? "Error" : title, Messagebox.OK, Messagebox.ERROR );
        }
        catch ( InterruptedException e ) {
            /*Just ignore it!!!*/
            e = null;
        }
    }

    public void onNotify( Event evt ) throws ApplicationException
    {
        if ( evt.getData() instanceof String ) {
            String message = ( String )evt.getData();
            if ( message.equals( "changeLogo" ) ) {
                showLogo();
            }
        }
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        showLogo();
    }

    private void showLogo()
    {
        if ( imageClienteLogo == null && imageCompanyLogo == null )
            return;

        MediaDTO[] medias = getLoginLocator().getLogo( getLoggedInUser() );
        if ( imageClienteLogo != null && medias[ 0 ] != null && medias[ 0 ].getObject() != null ) {
            ImageUtil.loadImage( imageClienteLogo, medias[ 0 ] );
        }
        if ( imageCompanyLogo != null && medias[ 1 ] != null && medias[ 1 ].getObject() != null ) {
            ImageUtil.loadImage( imageCompanyLogo, medias[ 1 ] );
        }
    }
}
