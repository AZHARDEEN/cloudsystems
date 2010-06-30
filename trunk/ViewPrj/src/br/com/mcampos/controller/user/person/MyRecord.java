package br.com.mcampos.controller.user.person;


import br.com.mcampos.controller.commom.user.PersonController;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.user.person.facade.PersonFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;

public class MyRecord extends PersonController
{
    private PersonFacade session;

    public MyRecord()
    {
        super();
    }

    public MyRecord( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        PersonDTO dto = getSession().get( getLoggedInUser() );
        showInfo( dto );
    }

    private PersonFacade getSession()
    {
        if ( session == null )
            session = ( PersonFacade )getRemoteSession( PersonFacade.class );
        return session;
    }

    @Override
    protected Boolean persist() throws ApplicationException
    {
        if ( super.persist() == false )
            return false;
        PersonDTO dto = getCurrentDTO();
        Integer oldStatus = getSession().getLoginStatus( getLoggedInUser() );
        getSession().updateMyRecord( getLoggedInUser(), dto );
        if ( oldStatus.equals( 5 ) )
            redirect( "/private/index.zul" );
        return true;
    }

}
