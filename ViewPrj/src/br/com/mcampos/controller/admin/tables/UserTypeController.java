package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class UserTypeController extends TableController
{
    protected Label recordId;
    protected Label recordDescription;
    protected Intbox editId;
    protected Textbox editDescription;
    
    public UserTypeController()
    {
        super();
    }

    public UserTypeController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return /* getLocator().getUserTypeList() */null;
    }

    protected void insertIntoListbox( Listbox listbox, Object e )
    {
        UserTypeDTO item = (UserTypeDTO) e;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, item, true );
}

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew )
    {
        UserTypeDTO record = (UserTypeDTO) e;

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

    protected Object getSingleRecord( Object id ) throws ApplicationException
    {
        Integer wishedId;
        UserTypeDTO record = null;
        
        wishedId = (Integer) id;
        /* record = getLocator().getUserType ( wishedId ); */
        return record;
    }

    protected void showRecord( Object obj )
    {
        UserTypeDTO record = (UserTypeDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
}

    protected Object saveRecord( SimpleTableLoaderLocator loc )
    {
        return null;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
    }

    protected void deleteRecord( SimpleTableLoaderLocator locator,
                                 Listitem item )
    {
    }
}
