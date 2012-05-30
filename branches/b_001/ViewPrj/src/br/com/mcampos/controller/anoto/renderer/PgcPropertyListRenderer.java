package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PgcPropertyListRenderer implements ListitemRenderer
{
    private int sequence = 0;


    public PgcPropertyListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        PgcPropertyDTO dto = ( PgcPropertyDTO )data;
        if ( dto.getId() == PgcPropertyDTO.gpsPropertyId ) {
            try {
                insertDescription( item, dto );
            }
            catch ( Exception e ) {
                e = null;
            }
            sequence++;
        }
        else {
            item.getChildren().add( new Listcell( dto.getValue() ) );
        }
    }

    private void insertDescription( Listitem item, PgcPropertyDTO dto ) throws Exception
    {
        switch ( sequence ) {
        case 3:
            item.getChildren().add( new Listcell( "Latitude" ) );
            item.getChildren().add( new Listcell( dto.getValue() ) );
            break;
        case 4:
            item.getChildren().add( new Listcell( "Longitude" ) );
            item.getChildren().add( new Listcell( dto.getValue() ) );
            break;
        case 5:
            item.getChildren().add( new Listcell( "Altitude" ) );
            item.getChildren().add( new Listcell( getHexValue( dto.getValue() ) + " M" ) );
            break;
        case 6:
            item.getChildren().add( new Listcell( "Precisão" ) );
            item.getChildren().add( new Listcell( getHexValue( dto.getValue() ) + " M" ) );
            break;
        default:
            item.getChildren().add( new Listcell( "N/A" ) );
            item.setVisible( false );
        }
    }

    private Integer getHexValue( String hexValue )
    {
        String value;

        if ( SysUtils.isEmpty( hexValue ) )
            return 0;
        hexValue.trim();
        if ( hexValue.startsWith( "0x" ) )
            value = hexValue.substring( 2 );
        else
            value = hexValue.trim();
        try {
            return Integer.parseInt( value, 16 );
        }
        catch ( Exception e ) {
            return 0;
        }
    }
}
