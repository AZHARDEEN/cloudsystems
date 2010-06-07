package br.com.mcampos.controller.user.person;


import br.com.mcampos.controller.commom.user.PersonController;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.user.person.facade.PersonFacade;

import org.zkoss.zk.ui.Component;

public class MyRecord extends PersonController
{
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

}
