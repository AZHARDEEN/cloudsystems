package br.com.mcampos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import br.com.mcampos.dto.system.MediaDTO;

public final class MediaUtil
{
	public static final String MEDIA_SAVE_DIR = "/var/local/system/media";

	public static final Integer MAX_DATABASE_MEDIA_OBJECT_SIZE = ( (int) ( 1024 * 1204 * 1.5 ) );

	public static MediaDTO getMediaDTO( Integer companyId, String simpleName, byte[ ] content, String mimeType ) throws IOException
	{

		if ( companyId == null || simpleName == null || content == null || content.length == 0 ) {
			throw new InvalidParameterException( "MediaUtil.saveTo parameters are invalid" );
		}
		MediaDTO mediaDto = new MediaDTO( );
		if ( content.length > MAX_DATABASE_MEDIA_OBJECT_SIZE.intValue( ) ) {
			saveTo( companyId, simpleName, content );
			mediaDto.setPath( saveTo( companyId, simpleName, content ) );
			mediaDto.setSize( content.length );
		}
		else {
			mediaDto.setObject( content );
		}
		mediaDto.setMimeType( mimeType );
		mediaDto.setName( simpleName );
		return mediaDto;
	}

	public static String saveTo( Integer companyId, String simpleName, byte[ ] content ) throws IOException
	{
		File dir = new File( MEDIA_SAVE_DIR + "/" + companyId );
		if ( !dir.exists( ) ) {
			if ( !dir.mkdirs( ) ) {
				throw new IOException( "Error creating target dir " + dir.getAbsolutePath( ) );
			}
		}
		File file = new File( dir, simpleName );
		if ( file.exists( ) ) {
			file.delete( );
		}
		FileOutputStream out = new FileOutputStream( file );
		out.write( content, 0, content.length );
		out.close( );
		return file.getAbsolutePath( );
	}

}
