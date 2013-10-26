package br.com.mcampos.sysutils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SysUtils
{
	static final byte[ ] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
			(byte) '7',
			(byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
			(byte) 'f' };
	private static final Logger LOGGER = LoggerFactory.getLogger( ServiceLocator.class.getSimpleName( ) );
	private static final int CONVERT_HEX_VALUE = 0xFF;
	private static final int RIGHT_SHIFT_VALUE = 4;
	private static final int CONVERT_HEX_VALUE_8BIT = 0xF;
	private static final int BUFFER_SIZE = 4096;

	private SysUtils( )
	{
		super( );
	}

	public static String trim( String s )
	{
		return ( isEmpty( s ) ? null : s.trim( ) );
	}

	public static boolean isEmpty( String s )
	{
		return ( isNull( s ) || s.isEmpty( ) );
	}

	public static boolean isEmpty( Vector<?> s )
	{
		return ( isNull( s ) || s.isEmpty( ) );
	}

	public static boolean isEmpty( Object[ ] s )
	{
		return ( isNull( s ) || s.length == 0 );
	}

	public static boolean isEmptyAfterTrim( String s )
	{
		return ( isEmpty( s ) || isEmpty( s.trim( ) ) );
	}

	public static boolean isZero( Integer i )
	{
		return ( isNull( i ) || i.equals( 0 ) );
	}

	public static boolean isNull( Object o )
	{
		return ( o == null );
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
			String aux = format;
			if ( isEmpty( aux ) ) {
				aux = "dd/MM/yyyy HH:mm:ss";
			}
			DateFormat df = new SimpleDateFormat( aux );
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
		if ( !SysUtils.isEmpty( str ) ) {
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
			int v = b & CONVERT_HEX_VALUE;
			hex[ index++ ] = HEX_CHAR_TABLE[ v >>> RIGHT_SHIFT_VALUE ];
			hex[ index++ ] = HEX_CHAR_TABLE[ v & CONVERT_HEX_VALUE_8BIT ];
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
		Class<?> localClass = clazz;
		do {
			try {
				fields.addAll( Arrays.asList( localClass.getDeclaredFields( ) ) );
			}
			catch ( Exception e ) {
				e = null;
			}
			localClass = localClass.getSuperclass( );
		} while ( localClass != null );
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

	public static byte[ ] readByteFromStream( InputStream inputStream )
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream( );
		DataOutputStream dos = new DataOutputStream( baos );
		byte[ ] data = new byte[ BUFFER_SIZE ];
		try {
			int count = inputStream.read( data );
			while ( count != -1 )
			{
				dos.write( data, 0, count );
				count = inputStream.read( data );
			}
		}
		catch ( Exception e ) {
			e = null;
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
			return formatter.parse( value );
		}
		catch ( ParseException e ) {
			LOGGER.error( "Format: value= " + value + ". format=" + format, e );
			return null;
		}
	}

	public static Locale getLocalePtBR( )
	{
		return new Locale( "pt", "BR" );
	}

	public static Number parseNumber( String value )
	{
		try {
			return NumberFormat.getInstance( getLocalePtBR( ) ).parse( value );
		}
		catch ( ParseException e ) {
			LOGGER.error( "parseNumber: " + value, e );
			return null;
		}
	}

	public static BigDecimal parseBigDecimal( String value )
	{
		Number number = parseNumber( value );

		return new BigDecimal( number.doubleValue( ) );
	}

	public static Integer parseInteger( String value )
	{
		try {
			return Integer.parseInt( value );
		}
		catch ( Exception e )
		{
			return Integer.valueOf( 0 );
		}
	}

	public static String format( String source, String regexPattern, String regexMask )
	{
		if ( SysUtils.isEmpty( source ) ) {
			return source;
		}
		try {
			return source.replaceFirst( regexPattern, regexMask );
		}
		catch ( Exception e )
		{
			LOGGER.error( "Format: source= " + source + ". regexPattern=" + regexPattern + ". regetMask=" + regexMask, e );
			return source;
		}

	}

	public static String format( String source, String meioMaskPattern )
	{
		if ( isEmpty( meioMaskPattern ) || isEmpty( source ) ) {
			return source;
		}
		if ( meioMaskPattern.equalsIgnoreCase( "phone" ) ) {
			return formatPhone( source );
		}
		else if ( meioMaskPattern.equalsIgnoreCase( "cep" ) ) {
			return formatCEP( source );
		}
		else if ( meioMaskPattern.equalsIgnoreCase( "cpf" ) ) {
			return formatCPF( source );
		}
		else if ( meioMaskPattern.equalsIgnoreCase( "cnpj" ) ) {
			return formatCPF( source );
		}
		return source;
	}

	public static String formatCEP( String source )
	{
		return format( source, "^\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d).*$", "$1$2\\.$3$4$5-$6$7$8" );
	}

	public static String formatPhone( String source )
	{
		return format( source, "^\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d).*$",
				"($1$2) $3$4$5$6-$7$8$9$10" );
	}

	public static String formatCPF( String source )
	{
		return format( source, "^\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d).*$",
				"$1$2$3\\.$4$5$6\\.$7$8$9-$10$11" );
	}

	public static String formatCNPJ( String source )
	{
		return format(
				source,
				"^\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d)\\D*(\\d).*$",
				"$1$2\\.$3$4$5\\.$6$7$8$9/$10$11$12$13-$14$15" );
	}
}
