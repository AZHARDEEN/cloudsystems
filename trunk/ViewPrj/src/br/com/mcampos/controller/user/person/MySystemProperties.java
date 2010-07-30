package br.com.mcampos.controller.user.person;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.system.LoginPropertyDTO;
import br.com.mcampos.ejb.cloudsystem.system.loginproperty.facade.LoginPropertyFacade;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;


public class MySystemProperties extends LoggedBaseController
{
    private Grid gridProperties;
    private LoginPropertyFacade session;

    public MySystemProperties( char c )
    {
        super( c );
    }

    public MySystemProperties()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( gridProperties.getColumns() );
        gridProperties.setRowRenderer( new SystemUserPropertyGridRenderer( getLoggedInUser() ) );
        List<LoginPropertyDTO> properties = getSession().getAll( getLoggedInUser() );
        gridProperties.setModel( new ListModelList( properties ) );
    }

    public LoginPropertyFacade getSession()
    {
        if ( session == null )
            session = ( LoginPropertyFacade )getRemoteSession( LoginPropertyFacade.class );
        return session;
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "MySystemPropertiesTitle" );
    }

}
