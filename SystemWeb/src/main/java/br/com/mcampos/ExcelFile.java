package br.com.mcampos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.InvalidParameterException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;

public class ExcelFile implements Serializable
{
	private static final long serialVersionUID = 7165568606231513814L;
	private static final Logger LOGGER = LoggerFactory.getLogger( ExcelFile.class );

	public enum TYPE
	{
		typeXLS, typeXLSX
	};

	private Workbook excel;

	public ExcelFile( )
	{

	}

	public ExcelFile( Media media )
	{
		if ( media == null ) {
			InvalidParameterException e = new InvalidParameterException( "Media cannot be null in Excel File constructor" );
			LOGGER.error( "Error opening excel file", e );
			throw e;
		}
		TYPE type = this.getType( media );
		if ( media.inMemory( ) )
		{
			try {
				this.open( media.getByteData( ), type );
			}
			catch ( Exception e ) {
				LOGGER.error( "Error opening excel file", e );
			}
		}
		else {
			try {
				this.open( media.getStreamData( ), type );
			}
			catch ( Exception e ) {
				LOGGER.error( "Error opening excel file", e );
			}
		}
	}

	public void open( byte[ ] media, TYPE type ) throws IOException
	{
		this.open( new ByteArrayInputStream( media ), type );
	}

	public void open( InputStream stream, TYPE type ) throws IOException
	{
		if ( type == TYPE.typeXLS ) {
			this.excel = new HSSFWorkbook( stream );
		}
		else {
			this.excel = new XSSFWorkbook( stream );
		}
	}

	private TYPE getType( Media media )
	{
		String aux = media.getName( ).toLowerCase( );
		return ( aux.endsWith( ".xlsx" ) ? TYPE.typeXLSX : TYPE.typeXLS );
	}

	public Sheet getSheetAt( int nIndex )
	{
		if ( this.excel == null ) {
			return null;
		}
		return this.excel.getSheetAt( nIndex );
	}

}
