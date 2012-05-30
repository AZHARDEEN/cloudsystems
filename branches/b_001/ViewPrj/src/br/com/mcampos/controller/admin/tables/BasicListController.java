package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.core.BasicCRUDController;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public abstract class BasicListController<DTO> extends BasicCRUDController
{

    private Listbox listboxRecord;


    /**
     * Quando um item da tree é selecionado, o implementador desta função abstrata deve mostrar todas as informações
     * pertinentes ao registro. Em alguns casos pode-se recorrer ao banco de dados para obter mais informações.
     *
     * @param record Indica o registro selecionado. Neste caso não é repassado o treeitem, mas o seu value associado.
     * @throws ApplicationException
     */
    protected abstract void showRecord( DTO record ) throws ApplicationException;

    protected abstract void clearRecordInfo();

    protected abstract List getRecordList() throws ApplicationException;

    protected abstract ListitemRenderer getRenderer();


    public BasicListController( char c )
    {
        super( c );
    }

    public BasicListController()
    {
        super();
    }

    public Listbox getListboxRecord()
    {
        return listboxRecord;
    }

    protected Object getSelectetItem()
    {
        Object object = null;


        Set selection = getModel().getSelection();
        /*
         * ONLY SINGLE SELECTION IS AVAILABLE, BY NOW!!!
         */
        if ( selection.size() == 1 )
            object = selection.iterator().next();
        return object;
    }

    public void onSelect$listboxRecord()
    {
        try {
            DTO dto = ( DTO )getSelectetItem();
            if ( dto != null ) {
                showRecord( dto );
            }
            else {
                clearRecordInfo();
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Informações do Registro Selecionado" );
        }
    }

    protected ListModelList getModel()
    {
        ListModelList listModel;

        listModel = ( ListModelList )getListboxRecord().getModel();
        if ( listModel == null ) {
            listModel = new ListModelList( new ArrayList<DTO>(), true );
            getListboxRecord().setModel( listModel );
        }
        return listModel;
    }

    protected Object getCurrentRecord()
    {
        return getSelectetItem();
    }


    protected DTO getValue( Listitem item )
    {
        if ( item == null )
            return null;
        Object selectedItem = item.getValue();
        return ( DTO )selectedItem;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listboxRecord.setItemRenderer( getRenderer() );
        refresh();
    }

    protected void refresh()
    {
        try {
            clearRecordInfo();
            getModel().clear();
            getModel().addAll( getRecordList() );
        }
        catch ( ApplicationException e ) {
            e = null;
        }
    }

    protected void afterDelete( Object currentRecord )
    {
        getModel().remove( currentRecord );
    }

    protected void afterPersist( Object currentRecord )
    {
        if ( isAddNewOperation() ) {
            getModel().add( ( DTO )currentRecord );
        }
        else {
            int nIndex = getModel().indexOf( currentRecord );
            getModel().set( nIndex, ( DTO )currentRecord );
        }
        try {
            showRecord( ( DTO )currentRecord );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Formulário" );
        }
    }
}
