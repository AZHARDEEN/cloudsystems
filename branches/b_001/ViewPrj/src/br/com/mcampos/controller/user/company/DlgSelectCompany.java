package br.com.mcampos.controller.user.company;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.user.ListUserDTO;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public abstract class DlgSelectCompany extends LoggedBaseController
{
    private Label labelCode;
    private Intbox userId;
    private Label labelName;
    private Label labelUserName;
    private ListUserDTO dto;
    private Button cmdSave;
    private Button cmdCancel;
    private Window rootParent;

    protected abstract ListUserDTO getCompany( Integer id );

    protected abstract boolean persist( ListUserDTO dto );

    public DlgSelectCompany( char c )
    {
        super( c );
    }

    public DlgSelectCompany()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelCode );
        setLabel( labelName );
        setLabel( cmdSave );
        setLabel( cmdCancel );
        cmdSave.setDisabled( true );
    }

    public void onOK$userId()
    {
        dto = getCompany( userId.getValue() );
        cmdSave.setDisabled( dto == null );
        labelUserName.setValue( dto != null ? dto.getName() : null );
    }

    public void onClick$cmdSave()
    {
        if ( persist( dto ) == true )
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

    protected Window getRootParent()
    {
        return rootParent;
    }
}
