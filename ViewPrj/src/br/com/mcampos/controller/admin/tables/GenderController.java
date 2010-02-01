package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class GenderController extends TableController
{
    protected Label recordId;
    protected Label recordDescription;

    protected Intbox editId;
    protected Textbox editDescription;
    
    public GenderController()
    {
        super();
    }

    public GenderController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return /* getLocator().getGenderList() */null;
    }

    protected void insertIntoListbox( Listbox listbox, Object e )
    {
        GenderDTO item = (GenderDTO ) e;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, item, true );
    }

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew )
    {
        GenderDTO record = (GenderDTO) e;

        if ( item == null || e == null )
            return;
        item.setValue( record.getId() );
        if ( bNew ) {
            item.appendChild( new Listcell ( record.getDescription() ) );
        }
        else {
            List listFields;
            Listcell cell;
                        
            listFields = item.getChildren();
            cell = (Listcell)listFields.get( 1 );
            if ( cell != null )
                cell.setLabel ( editDescription.getValue( ) );
        }
    }

    protected Object getSingleRecord( Object id )
    {
        Integer wishedId;
        GenderDTO record = null;
        
        wishedId = (Integer) id;
/*         try {
            record = getLocator().getGender ( wishedId );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Error" );
        } */
        return record;
    }

    protected void showRecord( Object obj )
    {
        GenderDTO record = (GenderDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc )
    {
        GenderDTO record = new GenderDTO ( editId.getValue(), editDescription.getValue(), null );
/*         try {
            if ( isAddNewOperation() ) {
                locator.addGender( record );
            }
        else
            locator.updateGender( record );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Error" );
        } */
        return record;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
        if ( item == null ) {
            if ( locator != null )
                editId.setRawValue( /* locator.getMaxGenderId() */0 );
            else
                editId.setRawValue( new Integer (0) );
            editDescription.setRawValue( "" );
            
            /*Habilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == true )
                editId.setDisabled( false );
        }
        else {
            editId.setValue( Integer.parseInt(recordId.getValue() ) );
            editDescription.setValue( recordDescription.getValue () );
            
            /*Desabilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == false )
                editId.setDisabled( true );
        }
        editDescription.setFocus( true );
    }

    protected void deleteRecord( SimpleTableLoaderLocator locator, Listitem item )
    {
/*         try {
            locator.deleteGender( (Integer)item.getValue() );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Error" );
        } */
    }
}
