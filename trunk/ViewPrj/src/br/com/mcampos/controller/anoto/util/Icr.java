package br.com.mcampos.controller.anoto.util;

import org.jawin.COMException;
import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;

public class Icr
{
    protected final String paramDir = "C:/Program Files/A2iA/A2iA FieldReader V3.4 R2/Parms/SoftField/Parms";

    protected static Icr instance;
    protected DispatchPtr A2iAObj;


    protected Icr() throws COMException
    {
        super();
        init ();
    }

    protected void init () throws COMException
    {
        Ole32.CoInitialize();
        A2iAObj = new DispatchPtr("A2iAFieldReaderCOM.APIFieldReader");
        engineInit();
    }

    public static Icr getInstance () throws COMException
    {
        if ( instance == null ) {
            instance = new Icr();
        }
        return instance;
    }

    public static void main ( String[] args )
    {
        Icr teste;


        try {
            teste = Icr.getInstance();
        }
        catch ( COMException e ) {
            System.out.println ( e.getMessage() );
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        // Call to tear down the COM library when application is finished
        Ole32.CoUninitialize();
    }

    protected void engineInit () throws COMException
    {
        A2iAObj.invoke("ScrInit", paramDir);
    }
}
