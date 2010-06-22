package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.core.LoggedBaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

public class UpdateLogoController extends LoggedBaseController
{
    private Button cmdSave;
    private Button cmdCancel;
    private Window rootParent;

    public UpdateLogoController( char c )
    {
        super( c );
    }

    public UpdateLogoController()
    {
        super();
    }

    public void onClick$cmdSave()
    {
        unloadMe();
    }

    public void onClick$cmdCancel()
    {
        unloadMe();
    }

    private void unloadMe()
    {
        rootParent.setVisible( false );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( cmdSave );
        setLabel( cmdCancel );
    }
}
