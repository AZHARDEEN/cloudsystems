package br.com.mcampos.controller.core;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.util.business.BusinessDelegate;

import org.zkoss.zul.Button;
import org.zkoss.zul.Div;

public abstract class BasicCRUDController extends LoggedBaseController
{
    protected Button cmdCreate;
    protected Button cmdUpdate;
    protected Button cmdDelete;
    protected Button cmdRefresh;
    protected Div recordEdit;
    protected Boolean isAddNewOperation;
    protected Object currentRecord;


    protected abstract BusinessDelegate getLocator();

    protected abstract void refresh();

    protected abstract void delete( BusinessDelegate locator, Object currentRecord ) throws ApplicationException;

    protected abstract Object saveRecord( BusinessDelegate locator ) throws ApplicationException;

    protected abstract void prepareToInsert( BusinessDelegate locator );

    protected abstract void prepareToUpdate( BusinessDelegate locator, Object currentRecord );

    protected abstract Object getCurrentRecord();

    protected abstract void insertItem( Object e );

    protected abstract void updateItem( Object e, Boolean bNew );

    public BasicCRUDController( char c )
    {
        super( c );
    }

    public BasicCRUDController()
    {
        super();
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
        }
    }

    public void onClick$cmdCreate()
    {
        try {
            prepareToInsert( getLocator() );
            showEditPanel( true );
            enableOperationsButtons( false );
            setAddNewOperation( true );
        }
        catch ( Exception e ) {
            showErrorMessage( "Ocorreu um erro ao processar a solicitação de inclusão. Não foi possível obter o valor do campo Chave",
                              "Criar Novo Registro" );
            onClick$cmdCancel();
        }
    }

    public void onClick$cmdUpdate()
    {
        Object currentRecord = getCurrentRecord();

        if ( currentRecord != null ) {
            showEditPanel( true );
            enableOperationsButtons( false );
            setAddNewOperation( false );
            try {
                prepareToUpdate( getLocator(), currentRecord );
            }
            catch ( Exception e ) {
                showErrorMessage( "Ocorreu um erro ao processar a solicitação de atualizar o registro",
                                  "Atualizar Registro Corrente" );
                onClick$cmdCancel();
            }
        }
    }

    public void onClick$cmdDelete()
    {
        Object currentRecord;

        showEditPanel( false );
        currentRecord = getCurrentRecord();
        if ( currentRecord != null ) {
            try {
                delete( getLocator(), currentRecord );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( "Ocorreu um erro ao tentar excluir o registro. A transação foi desfeita.", "Excluir Registro" );
            }
        }
    }

    public void onClick$cmdRefresh()
    {
        showEditPanel( false );
        refresh();
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

    protected void setAddNewOperation( Boolean isAddNewOperation )
    {
        this.isAddNewOperation = isAddNewOperation;
    }

    public Boolean isAddNewOperation()
    {
        return isAddNewOperation;
    }

    public void onClick$cmdSave()
    {
        Object record;

        showEditPanel( false );
        enableOperationsButtons( true );
        try {
            record = saveRecord( getLocator() );
            if ( isAddNewOperation() )
                insertItem( record );
            else
                updateItem( record, false );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Salvar atualizações" );
        }
        setAddNewOperation( false );
    }

    public void onClick$cmdCancel()
    {
        showEditPanel( false );
        enableOperationsButtons( true );
        setAddNewOperation( false );
    }

}
