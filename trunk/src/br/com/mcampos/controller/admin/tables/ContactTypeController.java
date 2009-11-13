package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.List;

import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class ContactTypeController extends TableController
{
    protected Borderlayout borderLayout;
    /*Descrição do registro (Parte Não editável)*/
    protected Label recordId;
    protected Label recordDescription;
    protected Label recordMask;
    protected Checkbox recordDuplicate;
    
    /*Parte Editavel do regsitro*/
    protected Intbox editId;
    protected Textbox editDescription;
    protected Textbox editMask;
    protected Checkbox editDuplicate;


    public ContactTypeController()
    {
        super();
    }

    public ContactTypeController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return getLocator().getContactTypeList();
    }

    protected void insertIntoListbox( Listbox listbox, Object o )
    {
        ContactTypeDTO item = (ContactTypeDTO) o;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, o, true );
}

    protected void updateListboxItem( Listitem row, Object o, Boolean bNew )
    {
        ContactTypeDTO item = (ContactTypeDTO) o;

        if ( row == null || o == null )
            return;
        row.setValue( item.getId() );
        if ( bNew ) {
            row.appendChild( new Listcell ( item.getDescription() ) );
            row.appendChild( new Listcell ( item.getMask() ) );
        }
        else {
            List listFields;
            Listcell cell;
                        
            listFields = row.getChildren();
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
        ContactTypeDTO record;
        
        wishedId = (Integer) id;
        record = getLocator().getContactType ( wishedId );
        return record;
    }

    protected void showRecord( Object obj )
    {
        ContactTypeDTO record = (ContactTypeDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
        recordMask.setValue( record.getMask() );
        recordDuplicate.setChecked( record.getAllowDuplicate() );
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc )
    {
        ContactTypeDTO record = new ContactTypeDTO ( editId.getValue(), editDescription.getValue(), 
                                               editMask.getValue(),  editDuplicate.isChecked() );
        if ( isAddNewOperation() )
            locator.addContactType( record );        
        else
            locator.updateContactType( record );
        return record;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
        if ( item == null ) {
            if ( locator != null )
                editId.setRawValue( locator.getMaxContactTypeId() );
            else
                editId.setRawValue( new Integer (0) );
            editDescription.setRawValue( "" );
            editMask.setRawValue( "" );
            editDuplicate.setChecked( false );
            
            /*Habilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == true )
                editId.setDisabled( false );
        }
        else {
            editId.setValue( Integer.parseInt(recordId.getValue() ) );
            editDescription.setValue( recordDescription.getValue () );
            editMask.setValue( recordMask.getValue() );
            editDuplicate.setChecked( recordDuplicate.isChecked() );
            
            /*Desabilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == false )
                editId.setDisabled( true );
        }
        editDescription.setFocus( true );
    }

    protected void deleteRecord( SimpleTableLoaderLocator locator,
                                 Listitem item )
    {
        locator.deleteContactType( (Integer)item.getValue() );
    }
}
