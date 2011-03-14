package br.com.mcampos.sysutils;


import java.io.UnsupportedEncodingException;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public final class SysUtils
{

    static final byte[] HEX_CHAR_TABLE = { ( byte )'0', ( byte )'1', ( byte )'2', ( byte )'3', ( byte )'4', ( byte )'5', ( byte )'6', ( byte )'7', ( byte )'8', ( byte )'9', ( byte )'a', ( byte )'b', ( byte )'c', ( byte )'d', ( byte )'e', ( byte )'f' };

    public SysUtils()
    {
        super();
    }

    public static String trim( String s )
    {
        return ( isEmpty( s ) ? null : s.trim() );
    }

    public static boolean isEmpty( String s )
    {
        if ( isNull( s ) )
            return true;
        if ( s.isEmpty() == true )
            return true;
        return false;
    }

    public static boolean isZero( Integer i )
    {
        if ( isNull( i ) )
            return true;
        if ( i.equals( 0 ) )
            return true;
        return false;
    }

    public static boolean isNull( Object o )
    {
        return o == null ? true : false;
    }

    public static Timestamp nowTimestamp()
    {
        java.util.Date today = new java.util.Date();
        return ( new java.sql.Timestamp( today.getTime() ) );
    }

    public static boolean isEmpty( List<?> list )
    {
        if ( isNull( list ) )
            return true;
        if ( list.size() == 0 )
            return true;
        return false;
    }

    public static String formatDateForSQLSearch( Date date )
    {
        DateFormat df = new SimpleDateFormat( "yyyyMMdd HHmmss" );
        return df.format( date );
    }

    public static String formatDate( Date date )
    {
        DateFormat df = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        return df.format( date );
    }


    public static String toUpperCase( String fieldValue )
    {
        if ( fieldValue == null || fieldValue.isEmpty() )
            return fieldValue;
        return fieldValue.toUpperCase();
    }

    public static String toLowerCase( String fieldValue )
    {
        if ( fieldValue == null || fieldValue.isEmpty() )
            return fieldValue;
        return fieldValue.toLowerCase();
    }


    public static String removeAccents( String str )
    {
        if ( SysUtils.isEmpty( str ) == false ) {
            String nfdNormalizedString = Normalizer.normalize( str, Normalizer.Form.NFD );
            Pattern pattern = Pattern.compile( "\\p{InCombiningDiacriticalMarks}+" );
            return pattern.matcher( nfdNormalizedString ).replaceAll( "" );
        }
        else
            return str;
    }


    public static String getHexString( byte[] raw, String caracterCode ) throws UnsupportedEncodingException
    {
        byte[] hex = new byte[ 2 * raw.length ];
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
}
