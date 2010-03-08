package br.com.mcampos.controller.anoto.renderer;

import br.com.mcampos.dto.anoto.PgcStatusDTO;

import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcStatus;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Vbox;


public class PropertyRowRenderer implements RowRenderer
{
    public PropertyRowRenderer()
    {
        super();
    }

    public void render( Row row, Object data ) throws Exception
    {
        row.setValue( data );
        GridProperties prop = ( GridProperties )data;

        row.appendChild( new Label( prop.name ) );
        if ( prop.values.size() > 1 ) {
            Vbox box = new Vbox();
            for ( Object value : prop.values ) {
                box.appendChild( addLabel( value ) );
            }
            row.appendChild( box );
        }
        else {
            Object obj = prop.values.get( 0 );
            row.appendChild( addLabel( obj ) );
        }
    }

    protected Label addLabel( Object value )
    {
        Label label = new Label( value.toString() );
        if ( value instanceof PgcStatusDTO ) {
            renderPgcStatus( label, ( PgcStatusDTO )value );
        }
        label.setWidth( "94%" );
        return label;
    }

    protected void renderPgcStatus( Label component, PgcStatusDTO status )
    {
        if ( status.getId() != PgcStatusDTO.statusOk )
            component.setSclass( "alert_label" );
    }
}
