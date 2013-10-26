package br.com.mcampos.controller.anoto.renderer;

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

import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.Attachment;

public class AttatchmentGridRenderer implements RowRenderer
{
	public AttatchmentGridRenderer( )
	{
		super( );
	}

	@Override
	public void render( Row row, Object data, int index ) throws Exception
	{
		row.setValue( data );
		PgcAttachmentDTO a = (PgcAttachmentDTO) data;
		if ( a.getType( ) == Attachment.ATTACHMENT_TYPE_BARCODE ) {
			row.appendChild( new Label( a.getValue( ) ) );
			showBarCode( row, a );
		}
		else
			row.appendChild( new Label( "Outro" ) );
	}

	protected void showBarCode( Row row, PgcAttachmentDTO a ) throws BarcodeException, OutputException
	{
		Barcode barcode = null;
		if ( SysUtils.isEmpty( a.getValue( ) ) == false )
			try {
				switch ( a.getBarcodeType( ) ) {
				case 1:
					barcode = new CodabarBarcode( a.getValue( ) );
					break;
				case 2:
					barcode = new EAN13Barcode( a.getValue( ) );
					break;
				case 3:
					break;
				case 4:
					barcode = new Code39Barcode( a.getValue( ), false, true );
					break;
				case 5:
					barcode = new Code128Barcode( a.getValue( ) );
					break;
				case 6:
					barcode = new Int2of5Barcode( a.getValue( ) );
					break;
				}
			}
			catch ( Exception e ) {
				barcode = null;
			}
		if ( barcode != null ) {
			Image img = new Image( );
			img.setContent( BarcodeImageHandler.getImage( barcode ) );
			row.appendChild( img );
		}
	}
}
