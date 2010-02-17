package br.com.mcampos.controller.admin.tables.core;

import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.core.SimpleTableDTO;

import br.com.mcampos.dto.user.login.AccessLogTypeDTO;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
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

    protected void prepareToInsert()
    {
        editId.setRawValue( getNextId() );
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
        if ( dto != null ) {
            dto.setId( editId.getValue() );
            dto.setDescription( editDescription.getValue() );
        }
        return dto;
    }

    protected void configure( Listitem item )
    {
        if ( item == null )
            return;
        SimpleTableDTO dto = getValue( item );

        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getId().toString() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }

    protected Listitem saveRecord( Listitem getCurrentRecord )
    {
        copyTo( getValue( getCurrentRecord ) );
        return getCurrentRecord;
    }

    protected void afterDelete( Listitem currentRecord )
    {
        currentRecord.detach();
        getListboxRecord().invalidate();
    }

    protected void afterEdit( Listitem currentRecord )
    {
        if ( currentRecord == null )
            return;

        if ( isAddNewOperation() ) {
            refresh();
        }
        else {
            SimpleTableDTO dto = getValue( currentRecord );

            if ( dto != null && currentRecord.getChildren().size() > 1 ) {
                ( ( Listcell )currentRecord.getChildren().get( 1 ) ).setLabel( dto.getDescription() );
            }
        }
    }

}
