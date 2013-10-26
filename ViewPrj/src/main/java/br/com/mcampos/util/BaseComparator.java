package br.com.mcampos.util;

import java.util.Comparator;

public abstract class BaseComparator implements Comparator
{
    protected boolean ascending;

    public BaseComparator()
    {
        super();
    }

    public BaseComparator( boolean bAsc )
    {
        super();
        this.ascending = bAsc;
    }

    public boolean isAscending()
    {
        return ascending;
    }
}
