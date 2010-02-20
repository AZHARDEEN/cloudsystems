package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableListModel;
import br.com.mcampos.controller.admin.tables.core.SimpleTableLocator;
import br.com.mcampos.controller.core.BasicCRUDController;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.persistence.NoResultException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Treeitem;

public abstract class BasicListController<DTO> extends BasicCRUDController<Listitem> implements ListitemRenderer
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

    protected abstract DTO createDTO();

    protected abstract DTO copyTo( DTO dto );

    protected abstract void configure( Listitem item );

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

        DTO record;

        if ( getListboxRecord().getSelectedCount() == 1 ) {
            try {
                record = getValue( getListboxRecord().getSelectedItem() );
                if ( record != null )
                    showRecord( record );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Obter Informações do Registro" );
            }
        }
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
        return ( ( DTO )selecteItem.getValue() );
    }

    @Override
    protected Listitem createNewRecord() throws ApplicationException
    {
        DTO dto;

        Listitem item = new Listitem();
        dto = copyTo( createDTO() );
        render( item, dto );
        return item;
    }

    public void render( Listitem item, Object data )
    {
        if ( item != null ) {
            item.setValue( data );
            configure( item );
        }
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listboxRecord.setItemRenderer( this );
        refresh();
    }

    protected void refresh()
    {
        try {
            getListboxRecord().setModel( new SimpleTableListModel( getRecordList() ) );
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
}
