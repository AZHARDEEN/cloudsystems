package br.com.mcampos.controller.admin.tables.core;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.core.SimpleTableDTO;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public abstract class SimpleTableController<DTO> extends BasicListController<SimpleTableDTO>
{
    protected Label recordId;
    protected Label recordDescription;
    protected Intbox editId;
    protected Textbox editDescription;

    protected abstract Integer getNextId();

    public SimpleTableController()
    {
        super();
    }

    @Override
    protected void clearRecordInfo()
    {
        editId.setRawValue( 0 );
        editDescription.setValue( "" );
    }

    @Override
    protected void prepareToInsert()
    {
        clearRecordInfo();
        editId.setRawValue( getNextId() );
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
    public void render( Listitem item, Object value )
    {
        SimpleTableDTO dto = ( SimpleTableDTO )value;

        if ( dto != null ) {
            item.getChildren().clear();
            item.getChildren().add( new Listcell( dto.getId().toString() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }

}
