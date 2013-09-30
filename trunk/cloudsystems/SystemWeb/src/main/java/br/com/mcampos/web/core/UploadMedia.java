package br.com.mcampos.web.core;

import java.io.IOException;
import java.io.InputStreamReader;

import br.com.mcampos.dto.system.MediaDTO;

public class UploadMedia
{
	public UploadMedia( )
	{
		super( );
	}

	public static MediaDTO getMedia( org.zkoss.util.media.Media media ) throws IOException
	{
		MediaDTO dto = new MediaDTO( );
		int mediaSize = 1;

		dto.setFormat( media.getFormat( ) );
		dto.setName( media.getName( ) );
		dto.setMimeType( media.getContentType( ) );
		if ( media.inMemory( ) ) {
			if ( media.isBinary( ) ) {
				dto.setObject( media.getByteData( ) );
			}
			else {
				dto.setObject( media.getStringData( ).getBytes( "UTF-8" ) );
			}
		}
		else {
			if ( media.isBinary( ) ) {
				mediaSize = media.getStreamData( ).available( );
				dto.setObject( new byte[ mediaSize ] );
				media.getStreamData( ).read( dto.getObject( ), 0, mediaSize );
			}
			else {
				InputStreamReader is = (InputStreamReader) media.getReaderData( );
				StringBuffer strBuffer = new StringBuffer( 1024 * 64 );
				char[ ] chArray = new char[ 512 ];
				int nRead;
				do {
					nRead = is.read( chArray );
					if ( nRead > 0 ) {
						strBuffer.append( chArray, 0, nRead );
					}
				} while ( nRead > 0 );
				mediaSize = strBuffer.length( );
				dto.setObject( strBuffer.toString( ).getBytes( "UTF-8" ) );
			}
		}
		return dto;
	}
}
