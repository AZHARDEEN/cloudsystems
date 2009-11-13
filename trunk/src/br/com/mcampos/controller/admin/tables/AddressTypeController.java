package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.List;

import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class AddressTypeController extends TableController
{
    protected Borderlayout borderLayout;
    protected Label recordId;
    protected Label recordDescription;
    protected Intbox editId;
    protected Textbox editDescription;
    
    public AddressTypeController( char c )
    {
        super( c );
    }

    public AddressTypeController()
    {
        super();
    }
    
 
    protected List getList ()
    {
        return getLocator().getAddressTypeList();
    }
    
    protected void updateListboxItem ( Listitem row, Object o, Boolean bNew )
    {
        AddressTypeDTO item = (AddressTypeDTO) o;

        if ( row == null || o == null )
            return;
        row.setValue( item.getId() );
        if ( bNew ) {
            row.appendChild( new Listcell ( item.getDescription() ) );
        }
        else {
            List listFields;
            Listcell cell;
                        
            listFields = row.getChildren();
            cell = (Listcell)listFields.get( 1 );
            if ( cell != null )
                cell.setLabel ( editDescription.getValue( ) );
        }
    }
    
    protected void insertIntoListbox ( Listbox listBox, Object o )
    {
        AddressTypeDTO item = (AddressTypeDTO) o;
        Listitem row;
        
        row = listBox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, o, true );
    }
    
    protected Object getSingleRecord ( Object id )
    {
        Integer wishedId;
        AddressTypeDTO record;
        
        wishedId = (Integer) id;
        record = getLocator().getAddressType ( wishedId );
        return record;
    }
    
    protected void showRecord ( Object obj )
    {
        AddressTypeDTO record = (AddressTypeDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
    }
    
    protected void updateEditableRecords( SimpleTableLoaderLocator locator, Listitem updateItem )
    {
        if ( updateItem == null ) {
            if ( locator != null )
                editId.setRawValue( locator.getMaxAddressTypeId() );
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
    
    protected Object saveRecord ( SimpleTableLoaderLocator locator )
    {
        AddressTypeDTO record = new AddressTypeDTO ( editId.getValue(), editDescription.getValue() );
        if ( isAddNewOperation() )
            locator.addAddressType( record );        
        else
            locator.updateAddressType( record );
        return record;
    }


    protected void deleteRecord ( SimpleTableLoaderLocator locator, Listitem item )
    {
        locator.deleteAddressType( (Integer)item.getValue() );
    }
}



