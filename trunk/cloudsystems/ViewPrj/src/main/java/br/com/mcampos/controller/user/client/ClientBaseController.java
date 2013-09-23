package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacade;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;


public abstract class ClientBaseController extends LoggedBaseController
{
    @SuppressWarnings( "compatibility:5923228962476106190" )
    private static final long serialVersionUID = 2966143738677016514L;
    private ClientFacade clientSession;


    private Listheader listHeaderCode;
    private Listheader listHeaderName;
    private Listheader headerId;
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


    protected abstract List getList() throws Exception;

    protected abstract void update();

    public ClientBaseController( char c )
    {
        super( c );
    }

    public ClientBaseController()
    {
        super();
    }


    private void configureLabels()
    {
        setLabel( listHeaderCode );
        setLabel( listHeaderName );
        setLabel( headerId );
        setLabel( cmdCreate );
        setLabel( cmdUpdate );
        setLabel( cmdDelete );
        setLabel( cmdRefresh );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        getListboxRecord().setItemRenderer( new ClientListRenderer() );
        configureLabels();
        refresh();
    }

    protected Listbox getListboxRecord()
    {
        return listboxRecord;
    }

    private void refresh()
    {
        if ( getListboxRecord() == null )
            return;
        ListModelList model = ( ListModelList )getListboxRecord().getModel();
        if ( model == null ) {
            model = new ListModelList();
            getListboxRecord().setModel( model );
        }
        model.clear();
        try {
            model.addAll( getList() );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onClick$cmdRefresh()
    {
        refresh();
    }


    public void onClick$cmdDelete() throws Exception
    {
        Object item = getCurrentRecord();

        if ( item == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        delete( item );
        ListModelList model = ( ListModelList )getListboxRecord().getModel();
        if ( model != null )
            model.remove( item );
    }


    protected Object getCurrentRecord()
    {
        Listitem currentItem = getListboxRecord().getSelectedItem();
        Object item = null;
        if ( currentItem != null )
            item = currentItem.getValue();
        return item;
    }

    protected ClientFacade getSession()
    {
        if ( clientSession == null )
            clientSession = ( ClientFacade )getRemoteSession( ClientFacade.class );
        return clientSession;
    }

    protected void delete( Object obj ) throws Exception
    {
        getSession().delete( getLoggedInUser(), ( ClientDTO )obj );
    }

    public void onDoubleClick$listboxRecord()
    {
        onClick$cmdUpdate();
    }

    public void onClick$cmdUpdate()
    {
        Object item = getCurrentRecord();
        if ( item == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        setParameter( "client", item );
        update();
    }

}
