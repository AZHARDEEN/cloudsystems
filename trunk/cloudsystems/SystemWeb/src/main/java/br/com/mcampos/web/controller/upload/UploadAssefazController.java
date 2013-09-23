package br.com.mcampos.web.controller.upload;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.AssefazDTO;
import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.dto.RejectedDTO;
import br.com.mcampos.jpa.system.FileUpload;

public class UploadAssefazController extends FileUploadController
{
	private static final long serialVersionUID = -85915790781570020L;
	private static final Logger logger = LoggerFactory.getLogger( UploadAssefazController.class.getSimpleName( ) );

	@Override
	protected boolean processFile( FileUpload fileUpload, MediaDTO media )
	{
		try {
			String line;
			int nLines = 0;
			int nRecords = 0;
			ByteArrayInputStream bais = new ByteArrayInputStream( media.getObject( ) );
			BufferedReader isr = new BufferedReader( new InputStreamReader( bais, "UTF-8" ) );
			AssefazDTO dto = new AssefazDTO( );
			ArrayList<RejectedDTO> rejecteds = new ArrayList<RejectedDTO>( );

			while ( ( line = isr.readLine( ) ) != null ) {
				nLines++;
				if ( line.startsWith( "carteira" ) ) {
					continue;
				}
				nRecords++;
				logger.info( line );
				dto.set( line );
				dto.setLineNumber( nLines );
				if ( processRecord( fileUpload, dto, rejecteds ) == false ) {
					continue;
				}
			}
			isr.close( );
			bais.close( );
			fileUpload.setRecords( nRecords );
			fileUpload.setRejecteds( rejecteds.size( ) );
			getSession( ).update( fileUpload, rejecteds );
			return true;
		}
		catch ( UnsupportedEncodingException e ) {
			e.printStackTrace( );
		}
		catch ( IOException e ) {
			e.printStackTrace( );
		}
		return false;
	}

	private boolean processRecord( FileUpload fileUpload, AssefazDTO dto, ArrayList<RejectedDTO> rejecteds )
	{
		if ( dto.isValid( ) == false ) {
			rejecteds.add( createRejected( dto ) );
			return false;
		}

		dto = getSession( ).add( getPrincipal( ), fileUpload, dto, true );
		if ( dto.isValid( ) == false ) {
			rejecteds.add( createRejected( dto ) );
		}
		return dto.isValid( );
	}

	private RejectedDTO createRejected( AssefazDTO dto )
	{
		RejectedDTO rejected = new RejectedDTO( );
		rejected.setRecord( dto.getRecord( ) );
		rejected.setLineNumber( dto.getLineNumber( ) );
		rejected.setCause( dto.getCause( ) );
		return rejected;
	}
}
