package br.com.mcampos.controller.admin.tables.core;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

public abstract class SimpleTableController<DTO> extends BasicListController<SimpleTableDTO>
{
    protected Label recordId;
    protected Label recordDescription;
    protected Intbox editId;
    protected Textbox editDescription;
    private Label labelCode;
    private Label labelDescription;

    private Label labelEditCode;
    private Label labelEditDescription;

    /*
	 * Labels
	 */
    private Listheader recordListDescSort;
    private Listheader recordListIdSort;
    private Listheader headerDescription;

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
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue( record.getDescription() );
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

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelCode );
        setLabel( labelDescription );
        setLabel( labelEditCode );
        setLabel( labelEditDescription );

        setLabel( headerDescription );
        setLabel( recordListDescSort );
        setLabel( recordListIdSort );
        setLabel( headerDescription );
    }

    protected Listheader getHeaderDescription()
    {
        return headerDescription;
    }
}
