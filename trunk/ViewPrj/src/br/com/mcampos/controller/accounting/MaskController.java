package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.account.mask.facade.AccountingMaskFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.api.Textbox;

public class MaskController extends LoggedBaseController
{
    private Label labelEditMask;
    private Textbox editMask;
    private AccountingMaskFacade session;

    public MaskController( char c )
    {
        super( c );
    }

    public MaskController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelEditMask );
        editMask.setValue( getSession().get( getLoggedInUser() ) );
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "accountingMaskTitle" );
    }

    public AccountingMaskFacade getSession()
    {
        if ( session == null )
            session = ( AccountingMaskFacade )getRemoteSession( AccountingMaskFacade.class );
        return session;
    }

    public void onClick$cmdSave()
    {
        try {
            getSession().set( getLoggedInUser(), editMask.getValue() );
            removeMe();
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }
}
