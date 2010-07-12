package br.com.mcampos.controller.user.collaborator;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.facade.CollaboratorFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;


public class CollaboratorController extends LoggedBaseController
{
    public static final String clientParamName = "client";
    private static final String addPage = "/private/user/collaborator/add_collaborator.zul";
    private static final String updatePage = "/private/user/collaborator/update_collaborator.zul";
    private static final String rolePage = "/private/user/collaborator/collaborator_role.zul";

    private CollaboratorFacade session;

    private Label labelCollaboratorTitle;
    private Listheader listHeaderCode;
    private Listheader listHeaderName;
    private Listheader headerId;

    private Button cmdManageRoles;

    private CompanyDTO company;

    private Listbox listboxRecord;

    /**
     * Mapeamento do botão cmdCreate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdCreate dispara o início do processo de inclusão em um formulário
     */
    private Button cmdCreate;

    /**
     * Mapeamento do botão cmdUpdate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdUpdate dispara o início do processo de alteração de um registro
     * O registro selecionado em um formulário.
     */
    private Button cmdUpdate;

    /**
     * Mapeamento do botão cmdDelete. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdDelete dispara o início do processo de exclusão de um registro em um formulário.
     */
    private Button cmdDelete;

    /**
     * Mapeamento do botão cmdRefresh. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdRefresh dispara o processo de atualização geral.
     */
    private Button cmdRefresh;


    public CollaboratorController( char c )
    {
        super( c );
    }

    public CollaboratorController()
    {
        super();
    }

    private void configureLabels()
    {
        setLabel( labelCollaboratorTitle );
        setLabel( listHeaderCode );
        setLabel( listHeaderName );

        setLabel( headerId );

        setLabel( cmdCreate );
        setLabel( cmdUpdate );
        setLabel( cmdDelete );
        setLabel( cmdRefresh );

        setLabel( cmdManageRoles );

    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        company = ( CompanyDTO )getParameter( clientParamName );
        if ( company == null )
            company = getSession().getCompany( getLoggedInUser() );
        getListbox().setItemRenderer( new CollaboratorListRenderer() );
        refresh();
        configureLabels();
    }

    public CollaboratorFacade getSession()
    {
        if ( session == null )
            session = ( CollaboratorFacade )getRemoteSession( CollaboratorFacade.class );
        return session;
    }


    public void onClick$cmdCreate()
    {
        if ( company == null )
            return;
        setParameter( "company", company.getId() );
        gotoPage( addPage, getRootParent().getParent() );
    }


    public void onClick$cmdUpdate()
    {
        if ( company == null )
            return;
        CollaboratorDTO dto = getCurrentRecord();
        if ( dto == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        setParameter( "collaborator", dto );
        gotoPage( updatePage, getRootParent().getParent() );
    }


    public void onClick$cmdDelete()
    {
        if ( company == null )
            return;
        CollaboratorDTO dto = getCurrentRecord();
        if ( dto == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        try {
            getSession().delete( getLoggedInUser(), dto );
            ListModelList model = getModel();
            model.remove( dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onClick$cmdRefresh() throws ApplicationException
    {
        refresh();
    }


    public void onClick$cmdManageRoles() throws ApplicationException
    {
        if ( getListbox().getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        CollaboratorDTO client = getCurrentRecord();
        if ( client != null ) {
            setParameter( "collaborator", client );
            gotoPage( rolePage, getRootParent().getParent() );
        }
    }

    private void refresh() throws ApplicationException
    {
        if ( company == null )
            return;
        List<CollaboratorDTO> list = getSession().getCollaborators( getLoggedInUser(), company.getId() );
        ListModelList model = getModel();
        model.clear();
        if ( SysUtils.isEmpty( list ) == false ) {
            model.addAll( list );
        }
    }

    private ListModelList getModel()
    {
        ListModelList model = ( ListModelList )listboxRecord.getModel();
        if ( model == null ) {
            model = new ListModelList( new ArrayList<CollaboratorDTO>(), true );
            listboxRecord.setModel( model );
        }
        return model;
    }

    private CollaboratorDTO getCurrentRecord()
    {
        Listitem currentItem = getListbox().getSelectedItem();
        Object item = null;

        if ( currentItem != null )
            item = currentItem.getValue();
        return ( CollaboratorDTO )item;
    }

    private Listbox getListbox()
    {
        return listboxRecord;
    }
}
