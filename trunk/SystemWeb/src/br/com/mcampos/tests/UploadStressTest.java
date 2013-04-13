package br.com.mcampos.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.web.locator.ServiceLocator;

public class UploadStressTest
{
	private static final Logger logger = LoggerFactory.getLogger( UploadStressTest.class.getSimpleName( ) );
	
	public void upload( String filetoUpload ) throws ParseException, IOException
	{
		String url = "http://localhost:8080/System/upload_pgc";
		// String viewStateName = "javax.faces.ViewState";
		// String submitButtonValue = "Upload"; // Value of upload submit button.

		HttpClient httpClient = new DefaultHttpClient( );
		HttpContext httpContext = new BasicHttpContext( );
		httpContext.setAttribute( ClientContext.COOKIE_STORE, new BasicCookieStore( ) );

		// HttpGet httpGet = new HttpGet( url );
		// HttpResponse getResponse = httpClient.execute( httpGet, httpContext );
		// Document document = Jsoup.parse( EntityUtils.toString( getResponse.getEntity( ) ) );
		// String viewStateValue = document.select( "input[type=hidden][name=" + viewStateName + "]" ).val( );
		// String uploadFieldName = document.select( "input[type=file]" ).attr( "name" );
		// String submitButtonName = document.select( "input[type=submit][value=" + submitButtonValue + "]" ).attr( "name" );

		File file = new File( filetoUpload );
		InputStream fileContent = new FileInputStream( file );
		String fileContentType = "application/octet-stream"; // Or whatever specific.
		String fileName = file.getName( );

		HttpPost httpPost = new HttpPost( url );
		MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );
		entity.addPart( "pgcFile", new InputStreamBody( fileContent, fileContentType, fileName ) );
		// entity.addPart( viewStateName, new StringBody( viewStateValue ) );
		// entity.addPart( submitButtonName, new StringBody( submitButtonValue ) );
		httpPost.setEntity( entity );
		httpClient.execute( httpPost, httpContext );
	}

	public static void main( String[ ] args )
	{
		UploadStressTest ust = new UploadStressTest( );

		String[ ] files = ust.getFiles( "T:\\temp\\pgc" );
		if ( files == null || files.length == 0 ) {
			return;
		}
		for ( String file : files ) {
			logger.info( "Processando arquivo: " + file );
			try {
				ust.upload( "T:\\temp\\pgc\\" + file );
			}
			catch ( ParseException e ) {
				e.printStackTrace( );
			}
			catch ( IOException e ) {
				e.printStackTrace( );
			}
		}
	}

	public String[ ] getFiles( String directoryName )
	{
		File dir = new File( directoryName );

		String[ ] children = null;
		// It is also possible to filter the list of returned files.
		// This example does not return any files that start with `.'.
		FilenameFilter filter = new FilenameFilter( )
		{
			@Override
			public boolean accept( File dir, String name )
			{
				name = name.toLowerCase( );
				return !name.endsWith( ".pcg" );
			}
		};
		children = dir.list( filter );
		return children;
	}
}
