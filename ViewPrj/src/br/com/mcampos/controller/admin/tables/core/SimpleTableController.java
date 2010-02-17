package br.com.mcampos.controller.admin.tables.core;

import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.core.SimpleTableDTO;

import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public abstract class SimpleTableController<DTO> extends BasicListController<SimpleTableDTO>
{
    protected Label recordId;
    protected Label recordDescription;
    protected Intbox editId;
    protected Textbox editDescription;

    public SimpleTableController()
    {
        super();
    }

    protected void prepareToInsert()
    {
        editId.setRawValue( 0 );
        editId.setReadonly( false );
        editDescription.setFocus( true );
        editDescription.setRawValue( "" );
    }

    protected void prepareToUpdate( Listitem currentRecord )
    {
        SimpleTableDTO dto = null;

        dto = getValue( currentRecord );

        editId.setValue( dto.getId() );
        editId.setReadonly( true );
        editDescription.setValue( dto.getDescription() );
    }

    protected void showRecord( SimpleTableDTO record )
    {
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue( record.getDescription() );
    }

    protected SimpleTableDTO copyTo( SimpleTableDTO dto )
    {
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue() );
        return dto;
    }

    protected void configure( Listitem item )
    {
        SimpleTableDTO dto = getValue( item );

        item.setLabel( dto.getDisplayName() );
    }
}
