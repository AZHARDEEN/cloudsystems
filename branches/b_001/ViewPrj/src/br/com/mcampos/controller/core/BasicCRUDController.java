package br.com.mcampos.controller.core;


import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;


public abstract class BasicCRUDController extends LoggedBaseController
{

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

    private Button cmdCancel;
    private Button cmdSave;

    /**
     * Mapeamento do div recordView. O nome deste div NÃO pode ser alterado no formulário WEB.
     * O div recordEdit é mostrado somente quando uma ação de inclusão ou alteração é executado
     */
    private Div recordView;

    /**
     * Mapeamento do div recordDetail. O nome deste div NÃO pode ser alterado no formulário WEB.
     * O div recordDetail é mostrado somente quando o usuário seleciona um objeto e o sistema entende que
     * existem mais informações para serem exibidas.
     */
    private Div recordEdit;

    /**
     * Indica se uma ação de inclusão de um novo registro está em andamento. Usado para o diferenciar
     * entre um evento de inclusão (true) ou alteração(false). Só faz sentido quando o sitema está entre
     * um destes dois estados.
     */
    private Boolean isAddNewOperation;

    private Div div_crud_buttons;

    private Div div_submit_cancel;


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
    protected abstract void delete( Object currentRecord ) throws ApplicationException;

    /**
     * Cleanup da função delete.
     * Após uma exclusão bem sucedida, a função afterdelete é chamada para realizar algum tipo
     * de limpeza, se necessário. Por exemplo, excluir o item selecionado de uma listbox.
     * Foi imaginado que esta função seria responsável pelo cleanup de algum objeto do formulário.
     *
     *
     * @param currentRecord Registro selecionado.
     */
    protected abstract void afterDelete( Object currentRecord );

    /**
     * Cleanup das funções update ou insert.
     * Após uma operação no banco de dados bem sucedida, a função afterEdit é chamada para realizar algum tipo
     * de limpeza, se necessário. Por exemplo, inserir um novo item a um listbox.
     * Foi imaginado que esta função seria responsável pela atualização de algum objeto do formulário.
     * Para diferenciar qual o tipo de operação foi acionada, use a função isAddNewOperation.
     *
     * @param currentRecord Registro selecionado.
     */
    protected abstract void afterPersist( Object currentRecord );

    /**
     * Prepara um dto para inserção no banco de dados.
     * Esta função tem como principal objetivo obter os dados dos campos da página
     * web e criar um DTO para inserir no banco de dados.
     * Durante o processo de inclusão de um registro no banco de dados, é solicitado ao controller a
     * criação de um novo registro através desta função (Neste caso o parametro getCurrentRecord é null.
     *
     * @return Objeto a ser inserido no banco de dados.
     * @throws ApplicationException
     */
    protected abstract Object saveRecord( Object currentRecord ) throws ApplicationException;

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
    protected abstract Object prepareToUpdate( Object currentRecord );


    /**
     * Obtem o registro autalmente selecionado, ou seja, o registro corrente.
     * @return Objeto selecionado.
     */
    protected abstract Object getCurrentRecord();


    /**
     * Cria um novo registro. Este procedimento é usado na rotina de inclusao.
     * @return Objeto selecionado.
     */
    protected abstract Object createNewRecord();

    protected abstract void persist( Object e ) throws ApplicationException;

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
    private void enableOperationsButtons( Boolean bEnable )
    {
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
            showErrorMessage( "Ocorreu um erro ao processar a solicitação de inclusão. Não foi possível obter o valor do campo Chave",
                              "Criar Novo Registro" );
        }
    }

    public void onClick$cmdUpdate()
    {
        Object currentRecord = getCurrentRecord();

        if ( currentRecord != null ) {
            enableOperationsButtons( false );
            setAddNewOperation( false );
            try {
                prepareToUpdate( currentRecord );
            }
            catch ( Exception e ) {
                onClick$cmdCancel();
                showErrorMessage( "Ocorreu um erro ao processar a solicitação de atualizar o registro",
                                  "Atualizar Registro Corrente" );
            }
        }
    }

    public void onClick$cmdDelete()
    {
        Object currentRecord;

        currentRecord = getCurrentRecord();
        if ( currentRecord != null ) {
            try {
                delete( currentRecord );
                afterDelete( currentRecord );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( "Ocorreu um erro ao tentar excluir o registro. A transação foi desfeita.", "Excluir Registro" );
            }
            catch ( EJBException e ) {
                showErrorMessage( "Não é possível excluir o registro.", "Excluir Registro" );
            }
        }
    }

    public void onClick$cmdRefresh()
    {
        refresh();
    }

    protected void showEditPanel( Boolean bShow )
    {
        if ( bShow ) {
            if ( recordView != null ) {
                if ( recordView.isVisible() == true )
                    recordView.setVisible( false );
            }
            if ( recordEdit != null ) {
                if ( recordEdit.isVisible() == false )
                    recordEdit.setVisible( true );
            }
            if ( div_submit_cancel != null ) {
                if ( div_submit_cancel.isVisible() == false )
                    div_submit_cancel.setVisible( true );
            }
            if ( div_crud_buttons != null ) {
                if ( div_crud_buttons.isVisible() == true )
                    div_crud_buttons.setVisible( false );
            }
        }
        else {
            if ( recordView != null ) {
                if ( recordView.isVisible() == false )
                    recordView.setVisible( true );
            }
            if ( recordEdit != null ) {
                if ( recordEdit.isVisible() == true )
                    recordEdit.setVisible( false );
            }
            if ( div_submit_cancel != null ) {
                if ( div_submit_cancel.isVisible() == true )
                    div_submit_cancel.setVisible( false );
            }
            if ( div_crud_buttons != null ) {
                if ( div_crud_buttons.isVisible() == false )
                    div_crud_buttons.setVisible( true );
            }
        }
    }

    /**
     * Armazena o status da alteração ou inclusão. Quando o usuário seleciona o botão incluir, esta função é
     * chamada com o parâmetro (true). Quando o usuário seleciona o botão editar (ou atualizar), esta função
     * é chamada com o parâmetro (false)
     * @param isAddNewOperation indica o tipo de operação(inclusão(true) ou alteração(false)
     */
    private void setAddNewOperation( Boolean isAddNewOperation )
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

        enableOperationsButtons( true );
        try {
            if ( isAddNewOperation() )
                record = createNewRecord();
            else
                record = getCurrentRecord();
            if ( record != null ) {
                persist( saveRecord( record ) );
                afterPersist( record );
            }
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
        enableOperationsButtons( true );
        setAddNewOperation( false );
    }

    protected void hideCreateButton( boolean bHide )
    {
        cmdCreate.setVisible( !bHide );
    }

    protected void hideDeleteButton( boolean bHide )
    {
        cmdCreate.setVisible( !bHide );
    }

    protected Div getRecordEdit()
    {
        return recordEdit;
    }

    @Override
    public void doAfterCompose( Component component ) throws Exception
    {
        super.doAfterCompose( component );
        setLabel( cmdCreate );
        setLabel( cmdUpdate );
        setLabel( cmdDelete );
        setLabel( cmdRefresh );
        setLabel( cmdCancel );
        setLabel( cmdSave );
        div_submit_cancel.setVisible( false );
    }
}
