package br.com.mcampos.util;

import java.util.Comparator;

import org.zkoss.zul.Listitem;

public class StringComparator extends BaseComparator
{
    public StringComparator( boolean bAsc )
    {
        super();
    }

    public int compare( Object o1, Object o2 )
    {
        Integer i1, i2;
        Listitem it1, it2;
        int result;


        it1 = ( Listitem )o1;
        it2 = ( Listitem )o2;

        i1 = ( Integer )it1.getValue();
        i2 = ( Integer )it2.getValue();

        result = i1.compareTo( i2 );
        return ( isAscending() ) ? result : -result;
    }
}
