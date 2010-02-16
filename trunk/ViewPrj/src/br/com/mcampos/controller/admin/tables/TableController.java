package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.IntegerComparator;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

public abstract class TableController extends LoggedBaseController
{

    protected Div recordEdit;
    protected Listbox listboxRecord;
    protected SimpleTableLoaderLocator locator;
    protected Button cmdCreate;
    protected Button cmdUpdate;
    protected Button cmdDelete;
    protected Button cmdRefresh;
    protected Boolean isAddNewOperation;
    protected Listitem updatableItem;
    protected List recordList;
    protected Listheader recordListIdSort;
    protected Window recordWindow;


    protected abstract List getList();

    protected abstract void insertIntoListbox( Listbox listbox, Object e );

    protected abstract void updateListboxItem( Listitem item, Object e, Boolean bNew );

    protected abstract Object getSingleRecord( Object id ) throws ApplicationException;

    protected abstract void showRecord( Object record );

    protected abstract Object saveRecord( SimpleTableLoaderLocator loc ) throws ApplicationException;

    protected abstract void updateEditableRecords( SimpleTableLoaderLocator locator, Listitem item );

    protected abstract void deleteRecord( SimpleTableLoaderLocator locator, Listitem item ) throws ApplicationException;

    public TableController( char c )
    {
        super( c );
    }

    public TableController()
    {
        super();
    }

    public void doAfterCompose( Component c ) throws Exception
    {
        super.doAfterCompose( c );
        isAddNewOperation = false;
        locator = new SimpleTableLoaderLocator();
        loadList( listboxRecord );
    }

    protected void loadList( Listbox listBox )
    {
        List list;
        Iterator it;

        if ( listBox == null )
            return;
        try {
            list = getList();
            listBox.getItems().clear();
            if ( list != null ) {
                it = list.iterator();
                while ( it.hasNext() ) {
                    insertIntoListbox( listBox, it.next() );
                }
                if ( listBox.getItems().size() > 0 ) {
                    listBox.setSelectedIndex( 0 );
                    onSelect$listboxRecord();
                }
            }
        }
        catch ( Exception e ) {
            showErrorMessage( "Ocorreu um erro ao carregar a lista do banco de dados.", "Carregar a Lista" );
        }
    }

    public void onClick$cmdCreate()
    {
        showEditPanel( true );
        enableOperationsButtons( false );
        setAddNewOperation( true );
        try {
            updateEditableRecords( getTableLocator(), null );
        }
        catch ( Exception e ) {
            showErrorMessage( "Ocorreu um erro ao processar a solicitação de inclusão. Não foi possível obter o valor do campo Chave", "Criar Novo Registro" );
            onClick$cmdCancel();
        }
    }

    public void onClick$cmdUpdate()
    {
        updatableItem = listboxRecord.getSelectedItem();

        if ( updatableItem != null ) {
            showEditPanel( true );
            enableOperationsButtons( false );
            setAddNewOperation( false );
            try {
                updateEditableRecords( getTableLocator(), updatableItem );
            }
            catch ( Exception e ) {
                showErrorMessage( "Ocorreu um erro ao processar a solicitação de atualizar o registro", "Atualizar Registro Corrente" );
                onClick$cmdCancel();
            }
        }
    }

    public void onClick$cmdDelete()
    {
        Listitem deleteItem;


        showEditPanel( false );
        deleteItem = listboxRecord.getSelectedItem();
        if ( deleteItem != null ) {
            try {
                deleteRecord( getTableLocator(), deleteItem );
                listboxRecord.removeChild( deleteItem );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( "Ocorreu um erro ao tentar excluir o registro. A transação foi desfeita.", "Excluir Registro" );
            }
        }
    }

    public void onClick$cmdRefresh()
    {
        showEditPanel( false );
        loadList( listboxRecord );
    }

    protected void showEditPanel( Boolean bShow )
    {
        if ( bShow ) {
            if ( recordEdit.isVisible() == false )
                recordEdit.setVisible( true );
        }
        else {
            if ( recordEdit.isVisible() == true )
                recordEdit.setVisible( false );
        }
    }

    protected void enableOperationsButtons( Boolean bEnable )
    {
        if ( bEnable ) {
            if ( cmdCreate.isDisabled() )
                cmdCreate.setDisabled( false );
            if ( cmdUpdate.isDisabled() )
                cmdUpdate.setDisabled( false );
            if ( cmdDelete.isDisabled() )
                cmdDelete.setDisabled( false );
            if ( cmdRefresh.isDisabled() )
                cmdRefresh.setDisabled( false );
            if ( listboxRecord.isDisabled() )
                listboxRecord.setDisabled( false );
        }
        else {
            if ( cmdCreate.isDisabled() == false )
                cmdCreate.setDisabled( true );
            if ( cmdUpdate.isDisabled() == false )
                cmdUpdate.setDisabled( true );
            if ( cmdDelete.isDisabled() == false )
                cmdDelete.setDisabled( true );
            if ( cmdRefresh.isDisabled() == false )
                cmdRefresh.setDisabled( true );
            if ( listboxRecord.isDisabled() == false )
                listboxRecord.setDisabled( true );
        }
    }

    public void onClick$cmdCancel()
    {
        showEditPanel( false );
        enableOperationsButtons( true );
        setAddNewOperation( false );
    }

    public void onClick$cmdSave()
    {
        Object record;

        showEditPanel( false );
        enableOperationsButtons( true );
        try {
            record = saveRecord( getTableLocator() );
            if ( isAddNewOperation() )
                insertIntoListbox( listboxRecord, record );
            else
                updateListboxItem( updatableItem, record, false );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Salvar atualizações" );
        }
        setAddNewOperation( false );
    }

    protected SimpleTableLoaderLocator getTableLocator()
    {
        return locator;
    }

    public void onSelect$listboxRecord()
    {
        Object record;

        try {
            if ( listboxRecord.getSelectedIndex() < 0 )
                return;
            record = getSingleRecord( listboxRecord.getSelectedItem().getValue() );
            if ( record != null )
                showRecord( record );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Obter Informações do Registro" );
        }
    }

    protected void setAddNewOperation( Boolean isAddNewOperation )
    {
        this.isAddNewOperation = isAddNewOperation;
    }

    public Boolean isAddNewOperation()
    {
        return isAddNewOperation;
    }

}
