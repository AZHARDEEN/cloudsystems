package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class UserStatusController extends TableController
{
    protected Label recordId;
    protected Label recordDescription;
    
    /*Parte Editavel do regsitro*/
    protected Intbox editId;
    protected Textbox editDescription;


    public UserStatusController()
    {
        super();
    }

    public UserStatusController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return getLocator().getUserStatusList();
    }

    protected void insertIntoListbox( Listbox listbox, Object e )
    {
        UserStatusDTO item = (UserStatusDTO) e;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, e, true );
    }

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew )
    {
        UserStatusDTO record = (UserStatusDTO) e;

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
        UserStatusDTO record;
        
        wishedId = (Integer) id;
        record = getLocator().getUserStatus ( wishedId );
        return record;
    }

    protected void showRecord( Object obj )
    {
        UserStatusDTO record = (UserStatusDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc )
    {
        UserStatusDTO record = new UserStatusDTO ( editId.getValue(), editDescription.getValue() );
        if ( isAddNewOperation() )
            locator.addUserStatus( record );        
        else
            locator.updateUserStatus( record );
        return record;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
        if ( item == null ) {
            if ( locator != null )
                editId.setRawValue( locator.getMaxUserStatusId() );
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

    protected void deleteRecord( SimpleTableLoaderLocator locator,
                                 Listitem item )
    {
        locator.deleteUserStatus( (Integer)item.getValue() );
    }
}
