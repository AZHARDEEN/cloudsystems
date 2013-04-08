package br.com.mcampos.sysutils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class SysUtils
{

	static final byte[ ] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
			(byte) '7',
			(byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
			(byte) 'f' };

	public SysUtils( )
	{
		super( );
	}

	public static String trim( String s )
	{
		return ( isEmpty( s ) ? null : s.trim( ) );
	}

	public static boolean isEmpty( String s )
	{
		if ( isNull( s ) ) {
			return true;
		}
		if ( s.isEmpty( ) == true ) {
			return true;
		}
		return false;
	}

	public static boolean isZero( Integer i )
	{
		if ( isNull( i ) ) {
			return true;
		}
		if ( i.equals( 0 ) ) {
			return true;
		}
		return false;
	}

	public static boolean isNull( Object o )
	{
		return o == null ? true : false;
	}

	public static Timestamp nowTimestamp( )
	{
		java.util.Date today = new java.util.Date( );
		return ( new java.sql.Timestamp( today.getTime( ) ) );
	}

	public static boolean isEmpty( Collection<?> list )
	{
		return ( ( isNull( list ) || list.size( ) == 0 ) ? true : false );
	}

	public static String formatDateForSQLSearch( Date date )
	{
		DateFormat df = new SimpleDateFormat( "yyyyMMdd HHmmss" );
		return df.format( date );
	}

	public static String formatDate( Date date )
	{
		return formatDate( date, "dd/MM/yyyy HH:mm:ss" );
	}

	public static String formatDate( Date date, String format )
	{
		if ( date != null ) {
			if ( isEmpty( format ) ) {
				format = "dd/MM/yyyy HH:mm:ss";
			}
			DateFormat df = new SimpleDateFormat( format );
			return df.format( date );
		}
		else {
			return "";
		}
	}

	public static String toUpperCase( String fieldValue )
	{
		if ( fieldValue == null || fieldValue.isEmpty( ) ) {
			return fieldValue;
		}
		return fieldValue.toUpperCase( );
	}

	public static String toLowerCase( String fieldValue )
	{
		if ( fieldValue == null || fieldValue.isEmpty( ) ) {
			return fieldValue;
		}
		return fieldValue.toLowerCase( );
	}

	public static String unaccent( String str )
	{
		if ( SysUtils.isEmpty( str ) == false ) {
			return Normalizer.normalize( str.trim( ), Normalizer.Form.NFD ).replaceAll( "\\p{IsM}+", "" );
		}
		else {
			return str;
		}
	}

	public static String getHexString( byte[ ] raw, String caracterCode ) throws UnsupportedEncodingException
	{
		byte[ ] hex = new byte[ 2 * raw.length ];
		int index = 0;

		for ( byte b : raw ) {
			int v = b & 0xFF;
			hex[ index++ ] = HEX_CHAR_TABLE[ v >>> 4 ];
			hex[ index++ ] = HEX_CHAR_TABLE[ v & 0xF ];
		}
		return new String( hex, caracterCode );
	}

	public static String itrim( String source )
	{
		return source.replaceAll( "\\b\\s{2,}\\b", " " );
	}

	public static List<Field> getDeclaredFields( Class<?> clazz )
	{
		List<Field> fields = new ArrayList<Field>( );
		do {
			try {
				fields.addAll( Arrays.asList( clazz.getDeclaredFields( ) ) );
			}
			catch ( Exception e ) {
				e = null;
			}
			clazz = clazz.getSuperclass( );
		} while ( clazz != null );
		return fields;
	}

	public static boolean search( Object object, String criteria ) throws IllegalArgumentException, IllegalAccessException
	{
		List<Field> fields = getDeclaredFields( object.getClass( ) );
		for ( Field field : fields ) {
			field.setAccessible( true );
			if ( String.valueOf( field.get( object ) ).equalsIgnoreCase( criteria ) ) {
				return true;
			}
		}
		return false;
	}

	public static Field getDeclaredField( Object object, String name ) throws NoSuchFieldException
	{
		Field field = null;
		Class<?> clazz = object.getClass( );
		do {
			try {
				field = clazz.getDeclaredField( name );
			}
			catch ( Exception e ) {
				e = null;
			}
			clazz = clazz.getSuperclass( );
		} while ( field == null && clazz != null );

		if ( field == null ) {
			throw new NoSuchFieldException( );
		}

		return field;
	}

	public static Method getDeclaredMethod( Object object, String name ) throws NoSuchMethodException
	{
		Method method = null;
		Class<?> clazz = object.getClass( );
		do {
			try {
				method = clazz.getDeclaredMethod( name );
			}
			catch ( Exception e ) {
				e = null;
			}
			clazz = clazz.getSuperclass( );
		} while ( method == null && clazz != null );

		if ( method == null ) {
			throw new NoSuchMethodException( );
		}

		return method;
	}

	public static Object invokeMethod( Object object, String methodName, Object[ ] arguments ) throws NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Method method = getDeclaredMethod( object, methodName );
		method.setAccessible( true );
		return method.invoke( object, arguments );
	}

	public static <T> T newInstance( Class<T> clazz ) throws IllegalArgumentException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		return newInstance( clazz, new Class[ 0 ], new Object[ 0 ] );
	}

	public static <T> T newInstance( Class<T> clazz, Class<?>[ ] paramClazzes, Object[ ] params ) throws IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{

		return clazz.getConstructor( paramClazzes ).newInstance( params );
	}

	public static void setFieldValue( Object object, String fieldName, Object newValue ) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException
	{

		Field field = getDeclaredField( object, fieldName );
		field.setAccessible( true );
		field.set( object, newValue );
	}

	public static void add( List<Object> list, Object obj )
	{
		if ( list == null || obj == null ) {
			return;
		}
		int nIndex = list.indexOf( obj );
		if ( nIndex < 0 ) {
			list.add( obj );
		}
	}

	public static void remove( List<?> list, Object obj )
	{
		if ( list == null || obj == null ) {
			return;
		}
		int nIndex = list.indexOf( obj );
		if ( nIndex >= 0 ) {
			list.remove( nIndex );
		}
	}

	public static String getExtension( File f )
	{
		String ext = null;
		String s = f.getName( );
		int i = s.lastIndexOf( '.' );

		if ( i > 0 && i < s.length( ) - 1 ) {
			ext = s.substring( i + 1 ).toLowerCase( );
		}

		if ( ext == null ) {
			return "";
		}
		return ext;
	}

	public static byte[ ] readByteFromStream( InputStream inputStream ) throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream( );
		DataOutputStream dos = new DataOutputStream( baos );
		byte[ ] data = new byte[ 4096 ];
		int count = inputStream.read( data );
		while ( count != -1 )
		{
			dos.write( data, 0, count );
			count = inputStream.read( data );
		}

		return baos.toByteArray( );
	}

	public static Date parseDate( String value, String format )
	{

		if ( isEmpty( value ) || isEmpty( format ) ) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat( format );
		try {
			Date date = formatter.parse( value );
			return date;
		}
		catch ( ParseException e ) {
			System.out.println( "Error parsing date: " + value );
			e.printStackTrace( );
			return null;
		}
	}

	public static Locale Locale_BR( )
	{
		return new Locale( "pt", "BR" );
	}
}
