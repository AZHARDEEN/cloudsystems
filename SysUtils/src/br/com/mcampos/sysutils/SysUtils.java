package br.com.mcampos.sysutils;


import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


public final class SysUtils
{
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

    @SuppressWarnings( "unchecked" )
    public static boolean isEmpty( List list )
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
}
