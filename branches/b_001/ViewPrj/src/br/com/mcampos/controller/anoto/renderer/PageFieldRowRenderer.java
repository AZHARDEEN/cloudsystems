package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.controller.anoto.util.IAnotoPageFieldEvent;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;


public class PageFieldRowRenderer implements RowRenderer
{
    List<FieldTypeDTO> fieldTypes;
    IAnotoPageFieldEvent listener;

    public PageFieldRowRenderer( IAnotoPageFieldEvent listener, List<FieldTypeDTO> types )
    {
        super();
        fieldTypes = types;
        this.listener = listener;
    }

    public void render( Row row, Object data ) throws Exception
    {
        row.setValue( data );
        AnotoPageFieldDTO dto = ( AnotoPageFieldDTO )data;

        if ( row.getChildren() != null && row.getChildren().size() > 0 )
            row.getChildren().clear();
        new Label( dto.getName() ).setParent( row );
        new Label( dto.getSequence().toString() ).setParent( row );
        Combobox cmbTypes = new Combobox();
        cmbTypes.setReadonly( true );
        for ( FieldTypeDTO item : fieldTypes ) {
            Comboitem i = cmbTypes.appendItem( item.getId().toString() + " - " + item.toString() );
            i.setValue( item );
            if ( item.getId().equals( dto.getType().getId() ) )
                cmbTypes.setSelectedItem( i );
        }
        cmbTypes.setParent( row );

        Checkbox icr = new Checkbox();
        icr.setChecked( dto.getPk() != null ? dto.getPk() : false );
        icr.setParent( row );
        icr.setAttribute( "field", "pk" );
        setListener( icr );

        icr = new Checkbox();
        icr.setChecked( dto.getIcr() );
        icr.setAttribute( "field", "icr" );
        icr.setParent( row );
        setListener( icr );
        setListener( cmbTypes );

        icr = new Checkbox();
        icr.setChecked( dto.getExport() != null ? dto.getExport() : false );
        icr.setParent( row );
        icr.setAttribute( "field", "export" );
        setListener( icr );

        icr = new Checkbox();
        icr.setChecked( dto.getSearchable() != null ? dto.getSearchable() : false );
        icr.setParent( row );
        icr.setAttribute( "field", "searchable" );
        setListener( icr );
    }

    protected void setListener( Component c )
    {
        if ( listener == null )
            return;

        if ( c instanceof Checkbox ) {
            c.addEventListener( Events.ON_CHECK, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        listener.onCheck( ( CheckEvent )event );
                    }
                } );
        }
        else if ( c instanceof Combobox ) {
            c.addEventListener( Events.ON_SELECT, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        listener.onSelect( ( SelectEvent )event );
                    }
                } );
        }
    }
}
