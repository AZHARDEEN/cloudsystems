package br.com.mcampos.controller.anoto.util.icr;


import br.com.mcampos.sysutils.SysUtils;

import org.jawin.COMException;

public class A2iaChannel extends A2iaBase
{
    private Integer paramId;

    public A2iaChannel( Integer id, A2ia obj ) throws COMException
    {
        super( obj );
        setIcrObj( obj );
        setParamId( id );
        configure ();
    }

    private void configure () throws COMException
    {
        setProperty ( "cpu[1].cpuServer", "");
        setProperty ( "cpu[1].paramDir", A2ia.paramDir );
        setProperty ( "cpu[1].dongleServer", "");
        open();
    }

    private void setProperty ( String name, String param ) throws COMException
    {
        getIcrObj().setProperty( getParamId(), name, param );
    }

    private void setParamId( Integer id )
    {
        this.paramId = id;
    }

    public Integer getParamId()
    {
        return paramId;
    }

    private void open () throws COMException
    {
        Integer id = getIcrObj().openChannel( this );
        setId (id);
    }

    public void close () throws COMException
    {
        if ( SysUtils.isZero( getId()) == false ) {
            getIcrObj().close( this );
            setId( null );
            System.out.println ( "Channel is closed!" );
        }
    }


    @Override
    protected void finalize() throws Throwable
    {
        close ();
        System.out.println ( "Channel is finalized!" );
        super.finalize();
    }
}
