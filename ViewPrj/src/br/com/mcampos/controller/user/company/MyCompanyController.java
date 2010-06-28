package br.com.mcampos.controller.user.company;


import br.com.mcampos.controller.commom.user.CompanyController;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.user.company.facade.MyCompanyFacade;

import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;

public class MyCompanyController extends CompanyController
{
    private MyCompanyFacade session;

    public MyCompanyController( char c )
    {
        super( c );
    }

    public MyCompanyController()
    {
        super();
    }

    protected CompanyDTO searchByDocument( String document, Integer type ) throws Exception
    {
        return null;
    }

    private MyCompanyFacade getSession()
    {
        if ( session == null )
            session = ( MyCompanyFacade )getRemoteSession( MyCompanyFacade.class );
        return session;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        showInfo( getSession().get( getLoggedInUser() ) );
    }

    @Override
    protected Boolean persist() throws ApplicationException
    {
        if ( super.persist() == false )
            return false;
        CompanyDTO dto = getCurrentDTO();
        getSession().update( getLoggedInUser(), dto );
        return true;
    }

}
