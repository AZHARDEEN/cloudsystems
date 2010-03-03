package br.com.mcampos.controller.anoto.renderer;

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
            for ( String value : prop.values ) {
                box.appendChild( new Label( value ) );
            }
            row.appendChild( box );
        }
        else {
            row.appendChild( new Label( prop.values.get( 0 ) ) );
        }
    }
}
