package br.com.mcampos.controller.anoto;

import br.com.mcampos.controller.user.company.DlgSelectCompany;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormFacade;

public class AnotoFormSelectCoompany extends DlgSelectCompany
{
    private AnotoFormFacade session;

    public AnotoFormSelectCoompany()
    {
        super();
    }

    public AnotoFormSelectCoompany( char c )
    {
        super( c );
    }

    protected ListUserDTO getCompany( Integer id )
    {
        try {
            FormDTO form = ( FormDTO )getRootParent().getAttribute( "form" );
            ListUserDTO dto = getSession().getCompany( getLoggedInUser(), form, id );
            return dto;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
            return null;
        }
    }

    protected boolean persist( ListUserDTO dto )
    {
        try {
            FormDTO form = ( FormDTO )getRootParent().getAttribute( "form" );
            getSession().addCompany( getLoggedInUser(), form, dto );
            return true;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
            return false;
        }
    }

    private AnotoFormFacade getSession()
    {
        if ( session == null )
            session = ( AnotoFormFacade )getRemoteSession( AnotoFormFacade.class );
        return session;
    }
}
