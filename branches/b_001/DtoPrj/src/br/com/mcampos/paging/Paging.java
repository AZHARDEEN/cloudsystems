package br.com.mcampos.paging;

import java.io.Serializable;

public class Paging implements Serializable
{
    @SuppressWarnings( "compatibility:4377802914779177675" )
    private static final long serialVersionUID = -9077230257581522920L;


    private Integer pageNumber;
    private Integer pageSize;

    public Paging()
    {
        super();
    }

    public Paging( Integer pageNumber, Integer pageSize )
    {
        super();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public void setPageNumber( Integer pageNumber )
    {
        if ( pageNumber.compareTo( 1 ) < 0 )
            pageNumber = 1;
        this.pageNumber = pageNumber;
    }

    public Integer getPageNumber()
    {
        return pageNumber;
    }

    public void setPageSize( Integer pageSize )
    {
        this.pageSize = pageSize;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }
}
