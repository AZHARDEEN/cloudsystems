package br.com.mcampos.ejb.cloudsystem;

import java.io.Serializable;

public class DBPaging implements Serializable
{
    private static final long serialVersionUID = -9077230257581522920L;

    private Integer rows = 100;
    private Integer page = 1;

    public DBPaging( int page, int rows )
    {
        setPage( page );
        setRows( rows );
    }

    public Integer getRows()
    {
        return this.rows;
    }

    public void setRows( Integer rows )
    {
        this.rows = rows;
    }

    public Integer getPage()
    {
        return this.page;
    }

    public void setPage( Integer page )
    {
        this.page = page;
    }

}
