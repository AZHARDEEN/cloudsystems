package br.com.mcampos.controller.anoto.util.icr;


import br.com.mcampos.sysutils.SysUtils;

import org.jawin.COMException;

public class A2iaDocument extends A2iaBase
{
    public static final int typeJPEG = 1;
    public static final int typeTIFF = 2;

    private String imagePath;
    private String stringType;

    public A2iaDocument( A2ia obj )
    {
        super( obj );
    }

    public void close () throws COMException
    {
        if ( SysUtils.isZero( getId()) == false ) {
            getIcrObj().close( this );
            setId( null );
            System.out.println ( "Document is closed!" );
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        close ();
        System.out.println ( "Document is finalized!" );
        super.finalize();
    }

    public void setImage ( String imagePath, Integer imageType ) throws COMException
    {
        setImagePath( imagePath );
        setStringType( imageType );
        getIcrObj().defineImage( this );
    }

    protected void setStringType ( Integer type )
    {
        switch ( type )
        {
            case typeJPEG:
                setStringType( "JPEG" );
                break;
            case typeTIFF:
                setStringType( "TIFF" );
                break;
            default:
                setStringType( "" );
                break;
        }
    }

    protected void setImagePath( String imagePath )
    {
        this.imagePath = imagePath;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    protected void setStringType( String imageStringType )
    {
        this.stringType = imageStringType;
    }

    public String getStringType()
    {
        return stringType;
    }
}
