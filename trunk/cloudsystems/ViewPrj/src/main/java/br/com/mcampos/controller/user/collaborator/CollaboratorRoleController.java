package br.com.mcampos.controller.user.collaborator;


import br.com.mcampos.controller.admin.security.roles.RoleListRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.facade.CollaboratorRoleFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Column;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;


public class CollaboratorRoleController extends LoggedBaseController
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

    private CollaboratorDTO collaborator;

    private CollaboratorRoleFacade session;

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listRole.setItemRenderer( new RoleListRenderer() );
        listAvailableRole.setItemRenderer( new RoleListRenderer() );
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
        collaborator = ( CollaboratorDTO )getParameter( "collaborator" );
        if ( collaborator != null )
            return super.doBeforeCompose( page, parent, compInfo );
        else
            return null;
    }

    private void loadRoles() throws ApplicationException
    {
        ListModelList model;
        List<RoleDTO> roles = getSession().getAvailableRoles( getLoggedInUser() );
        model = getModel( listAvailableRole );
        model.clear();
        model.addAll( roles );

        roles = getSession().getRoles( getLoggedInUser(), collaborator );
        model = getModel( listRole );
        model.clear();
        model.addAll( roles );
    }

    public CollaboratorRoleFacade getSession()
    {
        if ( session == null )
            session = ( CollaboratorRoleFacade )getRemoteSession( CollaboratorRoleFacade.class );
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
        getSession().add( getLoggedInUser(), collaborator, r );
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
        getSession().delete( getLoggedInUser(), collaborator, r );
        model.removeAll( roles );
    }
}
