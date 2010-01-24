package br.com.mcampos.exception;

public class ApplicationRuntimeException extends RuntimeException implements ExceptionCodeInterface
{
    protected Integer errorCode;
    
    public ApplicationRuntimeException( Throwable throwable )
    {
        super( throwable );
    }

    public ApplicationRuntimeException( String string, Throwable throwable )
    {
        super( string, throwable );
    }

    public ApplicationRuntimeException( String string )
    {
        super( string );
    }


    public ApplicationRuntimeException( Integer code, String string )
    {
        super( string );
        setErrorCode( code );
    }


    public ApplicationRuntimeException()
    {
        super();
    }

    public void setErrorCode( Integer errorCode )
    {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode()
    {
        return errorCode;
    }
}
