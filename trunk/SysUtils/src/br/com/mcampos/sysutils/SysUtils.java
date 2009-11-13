package br.com.mcampos.sysutils;

import java.lang.reflect.Array;

import java.sql.Timestamp;

import java.util.List;
import java.util.Vector;

public final class SysUtils
{
    public SysUtils()
    {
        super();
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
    
    public static boolean isEmpty ( List list )
    {
        if ( isNull ( list ) )
            return true;
        if ( list.size() == 0 )
            return true;
        return false;
    }
}
