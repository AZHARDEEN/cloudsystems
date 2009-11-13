package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;


import java.util.List;

import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class CivilStateController extends TableController
{
    protected Borderlayout borderLayout;
    protected Label recordId;
    protected Label recordDescription;

    protected Intbox editId;
    protected Textbox editDescription;
    
    public CivilStateController( char c )
    {
        super( c );
    }

    public CivilStateController()
    {
        super();
    }
    
 
    protected List getList ()
    {
        return getLocator().getCivilStateList();
    }
    
    protected void updateListboxItem ( Listitem row, Object o, Boolean bNew )
    {
        CivilStateDTO item = (CivilStateDTO) o;

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
        CivilStateDTO item = (CivilStateDTO) o;
        Listitem row;
        
        row = listBox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, o, true );
    }
    
    protected Object getSingleRecord ( Object id )
    {
        Integer wishedId;
        CivilStateDTO record;
        
        wishedId = (Integer) id;
        record = getLocator().getCivilState ( wishedId );
        return record;
    }
    
    protected void showRecord ( Object obj )
    {
        CivilStateDTO record = (CivilStateDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
    }
    
    protected void updateEditableRecords( SimpleTableLoaderLocator locator, Listitem updateItem )
    {
        if ( updateItem == null ) {
            if ( locator != null )
                editId.setRawValue( locator.getMaxCivilStateId() );
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
        CivilStateDTO record = new CivilStateDTO ( editId.getValue(), editDescription.getValue() );
        if ( isAddNewOperation() )
            locator.addCivilState( record );        
        else
            locator.updateCivilState( record );
        return record;
    }


    protected void deleteRecord ( SimpleTableLoaderLocator locator, Listitem item )
    {
        locator.deleteCivilState( (Integer)item.getValue() );
    }
}



