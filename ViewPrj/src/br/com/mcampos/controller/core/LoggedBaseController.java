package br.com.mcampos.controller.core;

import br.com.mcampos.controller.commom.user.PersonClientController;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.LoginLocator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Messagebox;

public class LoggedBaseController extends BaseController
{
    protected static String alternativePath = "/private/index.zul";
    protected LoginLocator loginLocator;

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
            redirect( "/login.zul" );
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
                    if ( path.endsWith( "person.zul" ) == false || ( this instanceof PersonClientController ) == false ) {
                        redirect( "/private/user/person.zul?who=myself" );
                    }
                    else
                        return super.doBeforeCompose( page, parent, compInfo );
                    break;
                case UserStatusDTO.statusExpiredPassword:
                    redirect( "/private/change_password.zul" );
                    break;
                default:
                    return super.doBeforeCompose( page, parent, compInfo );
                }
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Erro ao obter status do usuário" );
                redirect( "/login.zul" );
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

    protected void showErrorMessage( String description, String title )
    {
        try {
            Messagebox.show( description, title, Messagebox.OK, Messagebox.ERROR );
        }
        catch ( InterruptedException e ) {
            /*Just ignore it!!!*/
            e = null;
        }
    }
}
