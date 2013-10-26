package br.com.mcampos.util.system;

import java.util.Comparator;

public class StringComparator implements Comparator
{
    Boolean bAscending;

    public StringComparator()
    {
        super();
        bAscending = true;
    }

    public StringComparator ( Boolean asc )
    {
        bAscending = asc;
    }

    public int compare( Object o1, Object o2 )
    {
        String s1 = (String) o1;
        String s2 = (String) o2;
        if ( bAscending )
            return s1.compareToIgnoreCase( s2 );
        else
            return (-1 * s1.compareToIgnoreCase( s2 ) );
    }
}
