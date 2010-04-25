package br.com.mcampos.controller.anoto.util.icr;

import org.jawin.COMException;
import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;

public abstract class A2iaCom
{
    private DispatchPtr comObject = null;

    public A2iaCom() throws COMException
    {
        super();
        Ole32.CoInitialize();
        comObject = new DispatchPtr("A2iAFieldReaderCOM.APIFieldReader");

    }

    protected void setComObject( DispatchPtr comObject )
    {
        this.comObject = comObject;
    }

    protected DispatchPtr getComObject()
    {
        return comObject;
    }


    protected Object invoke ( String name ) throws COMException
    {
        return getComObject().invoke ( name );
    }

    protected Object invoke ( String name, Object obj ) throws COMException
    {
        return getComObject().invoke ( name, obj );
    }

    protected Object invoke ( String name, Object obj, Object param ) throws COMException
    {
        return getComObject().invoke ( name, obj, param );
    }

    protected Object invoke ( String name, Object obj, Object param, Object cookie ) throws COMException
    {
        return getComObject().invoke ( name, obj, param, cookie );
    }

    protected Object invoke ( String name, Object obj, Object param, Object cookie, Object subparam ) throws COMException
    {
        return getComObject().invoke ( name, obj, param, cookie, subparam );
    }

    @Override
    protected void finalize() throws Throwable
    {
        System.out.println ( "A2iaCom is finalized!" );
        Ole32.CoUninitialize();
        super.finalize();
    }
}
