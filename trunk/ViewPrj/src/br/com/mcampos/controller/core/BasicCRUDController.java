package br.com.mcampos.controller.core;

import br.com.mcampos.exception.ApplicationException;


import org.zkoss.zul.Button;
import org.zkoss.zul.Div;

public abstract class BasicCRUDController<T> extends LoggedBaseController
{

    /**
     * Mapeamento do botão cmdCreate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdCreate dispara o início do processo de inclusão em um formulário
     */
    protected Button cmdCreate;

    /**
     * Mapeamento do botão cmdUpdate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdUpdate dispara o início do processo de alteração de um registro
     * O registro selecionado em um formulário.
     */
    protected Button cmdUpdate;

    /**
     * Mapeamento do botão cmdDelete. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdDelete dispara o início do processo de exclusão de um registro em um formulário.
     */
    protected Button cmdDelete;

    /**
     * Mapeamento do botão cmdRefresh. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdRefresh dispara o processo de atualização geral.
     */
    protected Button cmdRefresh;

    /**
     * Mapeamento do div recordEdit. O nome deste div NÃO pode ser alterado no formulário WEB.
     * O div recordEdit é mostrado somente quando uma ação de inclusão ou alteração é executado
     */
    protected Div recordEdit;

    /**
     * Indica se uma ação de inclusão de um novo registro está em andamento. Usado para o diferenciar
     * entre um evento de inclusão (true) ou alteração(false). Só faz sentido quando o sitema está entre
     * um destes dois estados.
     */
    protected Boolean isAddNewOperation;


    /**
     * Esta função é chamada quando o botão de atualizar é pressionado.
     * Seu objetivo é reconstruir o objeto onde é armazenado os registros (Tree, Listbox).
     *
     */
    protected abstract void refresh();

    /**
     * Deleta (Exclui) o item selecionado.
     * Esta função tem o objetivo de excluir o item corrente. Todo a preparação já é executada na chamada
     * do evento do botão Excluir do formulário, de forma que esta função deve se preocupar somente
     * pela exclusão do registro no banco de dados.
     *
     * @param currentRecord Objeto que represente o registro selecionado para exclusão.
     * @throws ApplicationException
     */
    protected abstract void delete( T currentRecord ) throws ApplicationException;

    /**
     * Cleanup da função delete.
     * Após uma exclusão bem sucedida, a função afterdelete é chamada para realizar algum tipo
     * de limpeza, se necessário. Por exemplo, excluir o item selecionado de uma listbox.
     * Foi imaginado que esta função seria responsável pelo cleanup de algum objeto do formulário.
     *
     *
     * @param currentRecord Registro selecionado.
     */
    protected abstract void afterDelete( T currentRecord );

    /**
     * Cleanup das funções update ou insert.
     * Após uma operação no banco de dados bem sucedida, a função afterEdit é chamada para realizar algum tipo
     * de limpeza, se necessário. Por exemplo, inserir um novo item a um listbox.
     * Foi imaginado que esta função seria responsável pela atualização de algum objeto do formulário.
     * Para diferenciar qual o tipo de operação foi acionada, use a função isAddNewOperation.
     *
     * @param currentRecord Registro selecionado.
     */
    protected abstract void afterEdit( T currentRecord );

    /**
     * Prepara um dto para inserção no banco de dados.
     * Esta função tem como principal objetivo obter os dados dos campos da página
     * web e criar um DTO para inserir no banco de dados.
     *
     * @return Objeto a ser inserido no banco de dados.
     * @throws ApplicationException
     */
    protected abstract T saveRecord( T getCurrentRecord ) throws ApplicationException;

    /**
     * Durante o processo de inclusão de um registro no banco de dados, é solicitado ao controller a
     * criação de um novo registro através desta função.
     * O implementador deve preocupar-se apenas em retornar oum novo objeto(que represente um registro) -
     * um treeitem no caso de um tree por exemplo.
     * @return O novo objeto criado.
     * @throws ApplicationException
     */
    protected abstract T createNewRecord() throws ApplicationException;


    /**
     * Prepara a página para iniciar um procedimento de criação de um novo registro.
     * Esta função tem como objetivo principal preparar a página web para possibilitar ao usuário
     * inserir um novo registro. Por exemplo: limpar todos os campos de entrada de dados.
     * A princípio esta função deve apenas preocupar-se com os componentes visuais, os quais requerem algum
     * tipo de alteração devido ao processo de inclusão.
     *
     */
    protected abstract void prepareToInsert();

    /**
     * Prepara a página para iniciar um procedimento de atualização de um registro selecionado.
     * Assim como a função #prepareToInsert, esta função tem como objetivo principal preparar
     * a página web para possibilitar ao usuário alterar um registro selecionado.
     * Por exemplo: obter todos os dados necessários do registro selecionado e atualizar os campos de entrada de dados.
     *
     * @param currentRecord O registro corrente(selecionado).
     */
    protected abstract void prepareToUpdate( T currentRecord );


    /**
     * Obtem o registro autalmente selecionado, ou seja, o registro corrente.
     * @return Objeto selecionado.
     */
    protected abstract T getCurrentRecord();

    /**
     * Insere o novo registro no formulário. O implementador deve preocupar-se SOMENTE com os componetes visuais,
     * pois todo o resto já foi tratado por outras funçãoes. Quando esta função é chamada, pode afirmar com certeza
     * que o registro foi corretamente inserido no banco de dados.
     * @param e Novo objeto.
     */
    protected abstract void insertItem( T e );

    /**
     * Autaliza o registro no formulário. O implementador deve preocupar-se SOMENTE com os componetes visuais,
     * pois todo o resto já foi tratado por outras funçãoes. Quando esta função é chamada, pode afirmar com certeza
     * que o registro foi corretamente alterado no banco de dados.
     * @param e Novo objeto.
     */
    protected abstract void updateItem( T e );


    /**
     * Basic constructor
     * @param c
     */
    public BasicCRUDController( char c )
    {
        super( c );
    }

    /**
     * Basic constructor
     */
    public BasicCRUDController()
    {
        super();
    }


    /**
     * Tratamento dos botões visuais da página.
     * Habilita e/ou desabilita os botões de acordo com a ação solicitada (inserir e/ou alterar).
     *
     * @param bEnable
     */
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
        showEditPanel( !bEnable );
    }

    public void onClick$cmdCreate()
    {
        try {
            prepareToInsert();
            enableOperationsButtons( false );
            setAddNewOperation( true );
        }
        catch ( Exception e ) {
            onClick$cmdCancel();
            showErrorMessage( "Ocorreu um erro ao processar a solicitação de inclusão. Não foi possível obter o valor do campo Chave", "Criar Novo Registro" );
        }
    }

    public void onClick$cmdUpdate()
    {
        T currentRecord = getCurrentRecord();

        if ( currentRecord != null ) {
            enableOperationsButtons( false );
            setAddNewOperation( false );
            try {
                prepareToUpdate( currentRecord );
            }
            catch ( Exception e ) {
                onClick$cmdCancel();
                showErrorMessage( "Ocorreu um erro ao processar a solicitação de atualizar o registro", "Atualizar Registro Corrente" );
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

    /**
     * Armazena o status da alteração ou inclusão. Quando o usuário seleciona o botão incluir, esta função é
     * chamada com o parâmetro (true). Quando o usuário seleciona o botão editar (ou atualizar), esta função
     * é chamada com o parâmetro (false)
     * @param isAddNewOperation indica o tipo de operação(inclusão(true) ou alteração(false)
     */
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

    /**
     * restaura os compoentes visuais alterados pelo processo de inclusão e/ou alteração.
     * Como o próprio nome diz, cancela qualquer inclusão ou alteração.
     */
    public void onClick$cmdCancel()
    {
        showEditPanel( false );
        enableOperationsButtons( true );
        setAddNewOperation( false );
    }

}
