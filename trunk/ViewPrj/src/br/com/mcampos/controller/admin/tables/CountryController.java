package br.com.mcampos.controller.admin.tables;

import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import java.util.List;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class CountryController extends TableController
{
    Label recordId;
    Label recordCode;
    Label recordCode3;
    Label recordNumcode;

    Intbox editId;
    Textbox editCode;
    Textbox editCode3;
    Intbox editNumCode;


    public CountryController()
    {
        super();
    }

    public CountryController( char c )
    {
        super( c );
    }

    protected List getList()
    {
        return /* getLocator().getCountryList() */null;
    }

    protected void insertIntoListbox( Listbox listbox, Object e )
    {
        CountryDTO item = (CountryDTO) e;
        Listitem row;
        
        row = listbox.appendItem( item.getId().toString(), item.getId().toString() );
        if ( row != null )
            updateListboxItem( row, item, true );
    }

    protected void updateListboxItem( Listitem item, Object e, Boolean bNew )
    {
        CountryDTO record = (CountryDTO) e;

        if ( item == null || e == null )
            return;
        item.setValue( record.getId() );
        if ( bNew ) {
            item.appendChild( new Listcell ( record.getCode() ) );
            item.appendChild( new Listcell ( record.getCode3() ) );
            item.appendChild( new Listcell ( record.getNumericCode().toString() ) );
        }
        else {
            List listFields;
            Listcell cell;
                        
            listFields = item.getChildren();
            cell = (Listcell)listFields.get( 1 );
            if ( cell != null )
                cell.setLabel ( editCode.getValue( ) );
            cell = (Listcell)listFields.get( 2 );
            if ( cell != null )
                cell.setLabel ( editCode3.getValue( ) );
            cell = (Listcell)listFields.get( 3 );
            if ( cell != null )
                cell.setLabel ( editNumCode.getValue( ).toString() );
        }
    }

    protected Object getSingleRecord( Object id ) throws ApplicationException
    {
        Integer wishedId;
        CountryDTO record = null;
        
        wishedId = (Integer) id;
        /* record = getLocator().getCountry ( wishedId ); */
        return record;
    }

    protected void showRecord( Object obj )
    {
        CountryDTO record = (CountryDTO) obj;
        
        recordId.setValue( record.getId().toString() );
        recordCode3.setValue ( record.getCode3() );
        recordCode.setValue ( record.getCode() );
        recordNumcode.setValue ( record.getNumericCode().toString() );
    }

    protected Object saveRecord( SimpleTableLoaderLocator loc ) throws ApplicationException
    {
        CountryDTO record = new CountryDTO ( );
        record.setId( editId.getValue() );
        record.setCode( editCode.getValue() );
        record.setCode3( editCode3.getValue() );
        record.setNumericCode( editNumCode.getValue() );
/*         if ( isAddNewOperation() )
            locator.addCountry( record );        
        else
            locator.updateCountry( record ); */
        return record;
    }

    protected void updateEditableRecords( SimpleTableLoaderLocator locator,
                                          Listitem item )
    {
        if ( item == null ) {
            if ( locator != null )
                editId.setRawValue( /* locator.getMaxCountryId() */0 );
            else
                editId.setRawValue( new Integer (0) );
            editCode.setRawValue( "" );
            editCode3.setRawValue( "" );
            editNumCode.setRawValue( new Integer (0) );
            
            /*Habilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == true )
                editId.setDisabled( false );
        }
        else {
            editId.setValue( Integer.parseInt(recordId.getValue() ) );
            editCode.setValue( recordCode.getValue () );
            editCode3.setValue( recordCode3.getValue () );
            editNumCode.setValue( Integer.parseInt(recordNumcode.getValue () ) );
            
            /*Desabilitar os campos que compoem a chave primaria*/
            if ( editId.isDisabled() == false )
                editId.setDisabled( true );
        }
        editCode.setFocus( true );
    }

    protected void deleteRecord( SimpleTableLoaderLocator locator,
                                 Listitem item ) throws ApplicationException
    {
        /* locator.deleteUserType( (Integer)item.getValue() ); */
    }
}
