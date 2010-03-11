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

    protected void clearRecordInfo ()
    {
        editId.setRawValue( 0 );
        editDescription.setValue( "" );
    }

    protected void prepareToInsert()
    {
        editId.setRawValue( getNextId() );
        editId.setReadonly( false );
        editDescription.setFocus( true );
    }

    protected SimpleTableDTO prepareToUpdate( Object currentRecord )
    {
        SimpleTableDTO dto = null;

        //dto = getValue( currentRecord );

        editId.setValue( dto.getId() );
        editId.setReadonly( true );
        editDescription.setValue( dto.getDescription() );
        return dto;
    }

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

    protected SimpleTableDTO copyTo( SimpleTableDTO dto )
    {
        if ( dto != null ) {
            dto.setId( editId.getValue() );
            dto.setDescription( editDescription.getValue() );
        }
        return dto;
    }

    public void render( Listitem item, Object value )
    {
        SimpleTableDTO dto = (SimpleTableDTO) value;

        if ( dto != null ) {
            item.getChildren().clear();
            item.getChildren().add( new Listcell( dto.getId().toString() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }

}
