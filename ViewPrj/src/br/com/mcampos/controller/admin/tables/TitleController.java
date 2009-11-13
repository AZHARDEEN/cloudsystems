package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;


import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class TitleController extends TableController
{
    protected Label recordId;
    protected Label recordDescription;
    protected Label recordMask;
    
    /*Parte Editavel do regsitro*/
    protected Intbox editId;
    protected Textbox editDescription;
    protected Textbox editMask;


    public TitleController()
    {
        super();
    }

    public TitleController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return getLocator().getTitleList();
    }

    protected void insertIntoListbox( Listbox listbox, Object e )
    {
        TitleDTO item = (TitleDTO) e;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, e, true );
    }

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew )
    {
        TitleDTO record = (TitleDTO) e;

        if ( item == null || e == null )
            return;
        item.setValue( record.getId() );
        if ( bNew ) {
            item.appendChild( new Listcell ( record.getDescription() ) );
            item.appendChild( new Listcell ( record.getAbbreviation() ) );
        }
        else {
            List listFields;
            Listcell cell;
                        
            listFields = item.getChildren();
            cell = (Listcell)listFields.get( 1 );
            if ( cell != null )
                cell.setLabel ( editDescription.getValue( ) );
            cell = (Listcell)listFields.get( 2 );
            if ( cell != null )
                cell.setLabel ( editMask.getValue() );
        }
    }

    protected Object getSingleRecord( Object id )
    {
        Integer wishedId;
        TitleDTO record;
        
        wishedId = (Integer) id;
        record = getLocator().getTitle ( wishedId );
        return record;
    }

    protected void showRecord( Object obj )
    {
        TitleDTO record = (TitleDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
        recordMask.setValue( record.getAbbreviation() );
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc )
    {
        TitleDTO record = new TitleDTO ( editId.getValue(), editDescription.getValue(), 
                                               editMask.getValue() );
        if ( isAddNewOperation() )
            locator.addTitle( record );        
        else
            locator.updateTitle( record );
        return record;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
        if ( item == null ) {
            if ( locator != null )
                editId.setRawValue( locator.getMaxTitleId() );
            else
                editId.setRawValue( new Integer (0) );
            editDescription.setRawValue( "" );
            editMask.setRawValue( "" );
            
            /*Habilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == true )
                editId.setDisabled( false );
        }
        else {
            editId.setValue( Integer.parseInt(recordId.getValue() ) );
            editDescription.setValue( recordDescription.getValue () );
            editMask.setValue( recordMask.getValue() );
            
            /*Desabilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == false )
                editId.setDisabled( true );
        }
        editDescription.setFocus( true );
    }

    protected void deleteRecord( SimpleTableLoaderLocator locator,
                                 Listitem item )
    {
        locator.deleteTitle( (Integer)item.getValue() );
    }

}
