package br.com.mcampos.controller.anoto.renderer;


import com.anoto.api.Attachment;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.linear.codabar.CodabarBarcode;
import net.sourceforge.barbecue.linear.code128.Code128Barcode;
import net.sourceforge.barbecue.linear.code39.Code39Barcode;
import net.sourceforge.barbecue.linear.ean.EAN13Barcode;
import net.sourceforge.barbecue.linear.twoOfFive.Int2of5Barcode;
import net.sourceforge.barbecue.output.OutputException;

import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;


public class AttatchmentGridRenderer implements RowRenderer
{
    public AttatchmentGridRenderer()
    {
        super();
    }

    public void render( Row row, Object data ) throws Exception
    {
        row.setValue( data );
        Attachment a = ( Attachment )data;
        row.appendChild( new Label( "Tipo do Anexo" ) );
        if ( a.getType() == Attachment.ATTACHMENT_TYPE_BARCODE ) {
            showBarCode( row, a );
        }
        else {
            row.appendChild( new Label( "Outro" ) );
        }
    }

    protected void showBarCode( Row row, Attachment a ) throws BarcodeException, OutputException
    {
        byte[] barCodeData = a.getData();
        Barcode barcode = null;
        if ( barCodeData != null ) {
            int type = barCodeData[ 0 ];
            String sValue = "";
            Byte caracter;
            for ( int nCount = 1; nCount < barCodeData.length; nCount++ ) {
                caracter = barCodeData[ nCount ];
                sValue += caracter.toString();
            }
            try {
                switch ( type ) {
                case 1: barcode = new CodabarBarcode( sValue );
                    break;
                case 2: if ( sValue.length() > 12 )
                        sValue = sValue.substring( 0, 12 );
                    barcode = new EAN13Barcode( sValue );
                    break;
                case 3: break;
                case 4: barcode = new Code39Barcode( sValue, false, true );
                    break;
                case 5: barcode = new Code128Barcode( sValue );
                    break;
                case 6: barcode = new Int2of5Barcode( sValue );
                    break;
                }
            }
            catch ( Exception e ) {
                barcode = null;
            }
        }
        if ( barcode != null ) {
            Image img = new Image();
            img.setContent( BarcodeImageHandler.getImage( barcode ) );
            row.appendChild( img );
        }
    }
}
