package br.com.mcampos.controller.core;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.util.business.BusinessDelegate;

import org.zkoss.zul.Button;
import org.zkoss.zul.Div;

public abstract class BasicCRUDController<T> extends LoggedBaseController
{
    protected Button cmdCreate;
    protected Button cmdUpdate;
    protected Button cmdDelete;
    protected Button cmdRefresh;
    protected Div recordEdit;
    protected Boolean isAddNewOperation;
    protected T currentRecord;


    protected abstract void refresh();

    protected abstract void delete( T currentRecord ) throws ApplicationException;

    protected abstract void afterDelete( T currentRecord );

    protected abstract void afterEdit( T record );

    /**
     * Prepara um dto para inserção no banco de dados.
     * Esta função tem como principal objetivo obter os dados dos campos da página
     * web e criar um DTO para inserir no banco de dados.
     *
     * @param locator - Service Locator que está servindo esta instancia.
     * @return Objeto a ser inserido no banco de dados.
     * @throws ApplicationException
     */
    protected abstract T saveRecord( T getCurrentRecord ) throws ApplicationException;

    protected abstract T createNewRecord() throws ApplicationException;


    /**
     * Prepara a página para iniciar um procedimento de criação de um novo registro.
     * Esta função tem como objetivo principal preparar a página web para possibilitar ao usuário
     * inserir um novo registro. Por exemplo: limpar todos os campos de entrada de dados.
     *
     * @param locator Service Locator qe está servindo esta instancia
     */
    protected abstract void prepareToInsert();

    /**
     * Prepara a página para iniciar um procedimento de atualização de um registro selecionado.
     * Assim como a função #prepareToInsert, esta função tem como objetivo principal preparar
     * a página web para possibilitar ao usuário alterar um registro selecionado.
     * Por exemplo: obter todos os dados necessários do registro selecionado e atualizar os campos de entrada de dados.
     *
     * @param locator Service Locator qe está servindo esta instancia
     */
    protected abstract void prepareToUpdate( T currentRecord );

    protected abstract T getCurrentRecord();

    protected abstract void insertItem( T e );

    protected abstract void updateItem( T e );

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
            prepareToInsert();
            showEditPanel( true );
            enableOperationsButtons( false );
            setAddNewOperation( true );
        }
        catch ( Exception e ) {
            showErrorMessage( "Ocorreu um erro ao processar a solicitação de inclusão. Não foi possível obter o valor do campo Chave", "Criar Novo Registro" );
            onClick$cmdCancel();
        }
    }

    public void onClick$cmdUpdate()
    {
        T currentRecord = getCurrentRecord();

        if ( currentRecord != null ) {
            showEditPanel( true );
            enableOperationsButtons( false );
            setAddNewOperation( false );
            try {
                prepareToUpdate( currentRecord );
            }
            catch ( Exception e ) {
                showErrorMessage( "Ocorreu um erro ao processar a solicitação de atualizar o registro", "Atualizar Registro Corrente" );
                onClick$cmdCancel();
            }
        }
    }

    public void onClick$cmdDelete()
    {
        T currentRecord;

        showEditPanel( false );
        currentRecord = getCurrentRecord();
        if ( currentRecord != null ) {
            try {
                delete( currentRecord );
                afterDelete( currentRecord );
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

    {

    }

    public void onClick$cmdSave()
    {
        T record;

        showEditPanel( false );
        enableOperationsButtons( true );
        try {
            if ( isAddNewOperation() ) {
                record = createNewRecord();
                insertItem( record );
            }
            else {
                record = saveRecord( getCurrentRecord() );
                updateItem( record );
            }
            afterEdit( record );
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
