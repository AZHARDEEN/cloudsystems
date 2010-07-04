package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.admin.security.roles.RoleListRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientRoleFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Column;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;


public class ClientRoleController extends LoggedBaseController
{
    private Column columnSetRole;
    private Column columnAvailableRole;
    private Label labelClientRoleTitle;
    private Listbox listRole;
    private Listbox listAvailableRole;

    private Listheader listHeaderParent;
    private Listheader headerDescription;
    private Listheader listHeaderCode;

    private Listheader listHeaderParent1;
    private Listheader headerDescription1;
    private Listheader listHeaderCode1;

    private Integer clientId;

    private ClientRoleFacade session;

    private Label labelClient;

    private Intbox editUserid;

    public ClientRoleController( char c )
    {
        super( c );
    }

    public ClientRoleController()
    {
        super();
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listRole.setItemRenderer( new RoleListRenderer() );
        listAvailableRole.setItemRenderer( new RoleListRenderer() );
        loadAvaiableRoles();
        loadRoles();
        setLabels();
    }

    private void setLabels()
    {
        setLabel( columnSetRole );
        setLabel( columnAvailableRole );
        setLabel( labelClientRoleTitle );
        setLabel( listHeaderParent );
        setLabel( headerDescription );
        setLabel( listHeaderCode );
        setLabel( listHeaderParent1 );
        setLabel( headerDescription1 );
        setLabel( listHeaderCode1 );

        setLabel( labelClient );

    }

    private ListModelList getModel( Listbox l )
    {
        ListModelList model = ( ListModelList )l.getModel();
        if ( model == null ) {
            model = new ListModelList( new ArrayList(), true );
            l.setModel( model );
        }
        return model;
    }

    @Override
    public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
    {
        return super.doBeforeCompose( page, parent, compInfo );
    }

    private void loadAvaiableRoles() throws ApplicationException
    {
        ListModelList model;
        List<RoleDTO> roles = getSession().getAvailableRoles( getLoggedInUser() );
        model = getModel( listAvailableRole );
        model.clear();
        model.addAll( roles );

    }

    private void loadRoles() throws ApplicationException
    {
        if ( clientId != null ) {
            ListModelList model;
            List<RoleDTO> roles = getSession().getRoles( getLoggedInUser(), clientId );
            model = getModel( listRole );
            model.clear();
            model.addAll( roles );
        }
    }

    public ClientRoleFacade getSession()
    {
        if ( session == null )
            session = ( ClientRoleFacade )getRemoteSession( ClientRoleFacade.class );
        return session;
    }

    public void onClick$btnAddRole() throws ApplicationException
    {
        ListModelList model = getModel( listAvailableRole );
        Set roles = model.getSelection();
        if ( roles == null || roles.size() < 1 ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        List<RoleDTO> r = new ArrayList<RoleDTO>( roles );
        getSession().add( getLoggedInUser(), clientId, r );
        model = getModel( listRole );
        model.addAll( roles );
    }

    public void onClick$btnRemoveRole() throws ApplicationException
    {
        ListModelList model = getModel( listRole );
        Set roles = model.getSelection();
        if ( roles == null || roles.size() < 1 ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        List<RoleDTO> r = new ArrayList<RoleDTO>( roles );
        getSession().delete( getLoggedInUser(), clientId, r );
        model.removeAll( roles );
    }


    public void onClick$btnSearch()
    {
        ClientDTO c = ( ClientDTO )ClientSearchBox.show( getLoggedInUser(), getRootParent() );
        if ( c != null ) {
            clientId = c.getClient().getId();
            try {
                loadRoles();
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }
}
