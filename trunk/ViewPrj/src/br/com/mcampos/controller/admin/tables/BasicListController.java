package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.core.BasicCRUDController;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public abstract class BasicListController<DTO> extends BasicCRUDController implements ListitemRenderer
{

    protected Listbox listboxRecord;


    /**
     * Quando um item da tree é selecionado, o implementador desta função abstrata deve mostrar todas as informações
     * pertinentes ao registro. Em alguns casos pode-se recorrer ao banco de dados para obter mais informações.
     *
     * @param record Indica o registro selecionado. Neste caso não é repassado o treeitem, mas o seu value associado.
     * @throws ApplicationException
     */
    protected abstract void showRecord( DTO record ) throws ApplicationException;

    protected abstract void clearRecordInfo ( );

    //protected abstract void configure( Listitem item );

    protected abstract List getRecordList() throws ApplicationException;


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

    public void onSelect$listboxRecord()
    {
        try {
            Set selection = getModel().getSelection();
            if ( selection.size() == 1 ) {
                showRecord( (DTO)selection.iterator().next() );
            }
            else {
                clearRecordInfo();
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Informações do Registro Selecionado" );
        }
    }

    protected ListModelList getModel ()
    {
        ListModelList listModel;

        listModel =  ( ListModelList ) getListboxRecord().getModel();
        if ( listModel == null ) {
            listModel = new ListModelList ();
            getListboxRecord().setModel( listModel );
        }
        return listModel;
    }

    protected Listitem getCurrentRecord()
    {
        if ( getListboxRecord().getSelectedCount() == 1 ) {
            return getListboxRecord().getSelectedItem();
        }
        else
            return null;
    }


    protected DTO getValue( Listitem selecteItem )
    {
        if ( selecteItem == null )
            return null;
        Object selectedItem = selecteItem.getValue();
        return (DTO)selecteItem;
    }

    /*
    public void render( Listitem item, Object data )
    {
        if ( item != null ) {
            item.setValue( data );
            configure( item );
        }
    }
    */

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listboxRecord.setItemRenderer( this );
        listboxRecord.setModel( new ListModelList () );
        refresh();
    }

    protected void refresh()
    {
        try {
            showRecord( null );
            if ( getListboxRecord().getModel() == null )
                getListboxRecord().setModel( new ListModelList( getRecordList() ) );
            else {
                ListModelList listModel = ( ListModelList ) getListboxRecord().getModel();
                listModel.clear();
                listModel.addAll( getRecordList() );
            }
        }
        catch ( ApplicationException e ) {
            e = null;
        }
    }

    @Override
    protected void showEditPanel( Boolean bShow )
    {
        super.showEditPanel( bShow );
        for ( Object item : getListboxRecord().getItems() )
            ( ( Listitem )item ).setDisabled( bShow );
    }

    protected void afterDelete( Object currentRecord )
    {
        /*
        int currentIndex = getListboxRecord().getSelectedIndex();
        currentRecord.detach();
        if ( currentIndex > 0 ) {
            currentIndex --;
        }
        else{
            if ( getListboxRecord().getItemCount() > 0 )
                currentIndex ++;
        }
        getListboxRecord().setSelectedIndex( currentIndex );
        onSelect$listboxRecord();
        */
    }

    protected void afterEdit( Object currentRecord )
    {
    }

    protected Object saveRecord( Object getCurrentRecord )
    {
        return getCurrentRecord;
    }
}
