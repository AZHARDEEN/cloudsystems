package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.Collections;
import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class CompanyTypeController extends TableController
{
    protected Label recordId;
    protected Label recordDescription;
    protected Intbox editId;
    protected Textbox editDescription;


    public CompanyTypeController()
    {
        super();
    }

    public CompanyTypeController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return /* getLocator().getCompanyTypeList() */null;
    }

    protected void insertIntoListbox( Listbox listbox, Object e )
    {
        CompanyTypeDTO item = (CompanyTypeDTO) e;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, item, true );
    }

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew )
    {
        CompanyTypeDTO record = (CompanyTypeDTO) e;

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
        CompanyTypeDTO record = null;
        
        wishedId = (Integer) id;
        /* record = getLocator().getCompanyType ( wishedId ); */
        return record;
    }

    protected void showRecord( Object obj )
    {
        CompanyTypeDTO record = (CompanyTypeDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue ( record.getDescription() );
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc ) throws ApplicationException
    {
        CompanyTypeDTO record = new CompanyTypeDTO ( editId.getValue(), editDescription.getValue() );
/*         if ( isAddNewOperation() )
            locator.addCompanyType( record );        
        else
            locator.updateCompanyType( record ); */
        return record;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
        if ( item == null ) {
            if ( locator != null )
                editId.setRawValue( /* locator.getMaxCompanyTypeId() */0 );
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
                                 Listitem item ) throws ApplicationException
    {
        /* locator.deleteCompanyType( (Integer)item.getValue() ); */
    }
}
