package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.system.SystemParametersDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.Collections;
import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class SystemParametersController extends TableController {

    protected Label recordId;
    protected Label recordDescription;
    protected Label recordValue;
    
    
    protected Textbox editId;
    protected Textbox editDescription;
    protected Textbox editValue;

    public SystemParametersController() {
        super();
    }

    public SystemParametersController( char c ) {
        super( c );
    }

    protected List getList() {
        return getLocator().getSystemParametersList();
    }

    protected void insertIntoListbox( Listbox listbox, Object e ) {
        SystemParametersDTO item = (SystemParametersDTO) e;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, item, true );
    }

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew ) {
        SystemParametersDTO record = (SystemParametersDTO) e;

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

    protected Object getSingleRecord( Object id ) {
        String wishedId;
        SystemParametersDTO record;
        
        wishedId = (String) id;
        record = getLocator().getSystemParameters( wishedId );
        return record;
    }

    protected void showRecord( Object obj ) {
        SystemParametersDTO record = (SystemParametersDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
        recordValue.setValue ( record.getValue() );
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc ) {
        SystemParametersDTO record = new SystemParametersDTO ( editId.getValue(), editDescription.getValue(), editValue.getValue() );
        if ( isAddNewOperation() )
            locator.addSystemParameters( record );        
        else
            locator.updateSystemParameters( record );
        return record;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator, Listitem item ) {
        if ( item == null ) {
            editId.setRawValue( "" );
            editDescription.setRawValue( "" );
            editValue.setRawValue( "" );
            
            /*Habilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == true )
                editId.setDisabled( false );
            editId.setFocus( true );
        }
        else {
            editId.setValue( recordId.getValue() );
            editDescription.setValue( recordDescription.getValue () );
            editValue.setValue( recordValue.getValue() );
            
            /*Desabilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == false )
                editId.setDisabled( true );
            editDescription.setFocus( true );
        }
    }

    protected void deleteRecord( SimpleTableLoaderLocator locator, Listitem item ) {
        
    }

}
