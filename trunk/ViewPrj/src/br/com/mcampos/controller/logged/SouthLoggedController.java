package br.com.mcampos.controller.logged;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.facade.CollaboratorFacade;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;


public class SouthLoggedController extends LoggedBaseController
{
    private Label labelCopyright;
    private Label labelVersion;
    private Combobox companies;

    private CollaboratorFacade session;


    public SouthLoggedController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabels();
        List<CompanyDTO> list = getSession().getCompanies( getLoggedInUser() );
        loadCombobox( companies, list );
        if ( list.size() > 0 ) {
            companies.setSelectedIndex( 0 );
            onSelect$companies();
        }
        labelVersion.setValue( desktop.getWebApp().getVersion() );
    }


    private void setLabels()
    {
        setLabel( labelCopyright );
    }

    public CollaboratorFacade getSession()
    {
        if ( session == null )
            session = ( CollaboratorFacade )getRemoteSession( CollaboratorFacade.class );
        return session;
    }

    public void onSelect$companies()
    {
        Comboitem comboItem = companies.getSelectedItem();
        if ( comboItem != null ) {
            CompanyDTO dto = ( CompanyDTO )comboItem.getValue();
            if ( dto != null ) {
                getLoggedInUser().setCurrentCompany( dto.getId() );
                setLoggedInUser( getLoggedInUser() );
                Events.postEvent( new Event( Events.ON_NOTIFY, null, dto ) );
            }
        }
    }
}
