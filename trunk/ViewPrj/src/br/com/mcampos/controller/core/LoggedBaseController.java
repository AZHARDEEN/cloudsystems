package br.com.mcampos.controller.core;

import br.com.mcampos.controller.commom.user.PersonClientController;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;

import br.com.mcampos.dto.user.login.LoginDTO;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

public class LoggedBaseController extends BaseController
{
    protected static String alternativePath = "/private/index.zul";


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
            LoginDTO user = getLoggedInUser();

            switch ( user.getUserStatus().getId() ) {
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
        return null;
    }
}