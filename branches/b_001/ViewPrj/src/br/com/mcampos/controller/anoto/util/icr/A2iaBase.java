package br.com.mcampos.controller.anoto.util.icr;

public abstract class A2iaBase
{
    private A2ia icrObj;
    private Integer id;

    public A2iaBase( A2ia obj )
    {
        super();
        setIcrObj( obj );
    }

    public void setIcrObj( A2ia icrObj )
    {
        this.icrObj = icrObj;
    }

    public A2ia getIcrObj()
    {
        return icrObj;
    }

    protected void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
}
