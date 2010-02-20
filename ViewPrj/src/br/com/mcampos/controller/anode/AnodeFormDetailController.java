package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.core.LoggedBaseController;

import org.zkoss.zul.Button;
import org.zkoss.zul.Toolbarbutton;

public class AnodeFormDetailController extends LoggedBaseController
{
    Button btnAddAttach;
    Button btnRemoveAttach;

    public AnodeFormDetailController( char c )
    {
        super( c );
    }

    public AnodeFormDetailController()
    {
        super();
    }

    public void onClick$cmdRefresh()
    {
        showErrorMessage( "Oh! Yes!!!", "Detail" );
    }

    public void onClick$cmdCancel()
    {
        /*Dummy!!!!*/
    }
}
