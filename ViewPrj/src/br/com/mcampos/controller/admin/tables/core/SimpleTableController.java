package br.com.mcampos.controller.admin.tables.core;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

public abstract class SimpleTableController<DTO> extends BasicListController<SimpleTableDTO>
{
    protected Label recordId;
    protected Label recordDescription;
    protected Intbox editId;
    protected Textbox editDescription;

    protected abstract Integer getNextId() throws ApplicationException;

    public SimpleTableController()
    {
        super();
    }

    @Override
    protected void clearRecordInfo()
    {
        editId.setRawValue( 0 );
        editDescription.setRawValue( "" );
    }

    @Override
    protected void prepareToInsert()
    {
        clearRecordInfo();
        try {
            editId.setRawValue( getNextId() );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "GetNextId" );
        }
        editId.setReadonly( false );
        editDescription.setFocus( true );
    }

    @Override
    protected SimpleTableDTO prepareToUpdate( Object currentRecord )
    {
        SimpleTableDTO dto = ( SimpleTableDTO )currentRecord;

        //dto = getValue( currentRecord );

        editId.setValue( dto.getId() );
        editId.setReadonly( true );
        editDescription.setValue( dto.getDescription() );
        return dto;
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        if ( record != null ) {
            recordId.setValue( record.getId().toString() );
            recordDescription.setValue( record.getDescription() );
        }
        else {
            clearRecordInfo();
        }
    }

    @Override
    protected Object saveRecord( Object object )
    {
        SimpleTableDTO dto = ( SimpleTableDTO )object;
        if ( isAddNewOperation() )
            dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue() );
        return dto;
    }

    @Override
    protected ListitemRenderer getRenderer()
    {
        return new SimpleTableListRenderer();
    }
}
