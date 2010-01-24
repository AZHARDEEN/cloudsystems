package br.com.mcampos.exception;

public class ApplicationException extends Exception implements ExceptionCodeInterface
{
    protected Integer errorCode;
    
    
    public ApplicationException( Throwable throwable )
    {
        super( throwable );
    }

    public ApplicationException( String string, Throwable throwable )
    {
        super( string, throwable );
    }

    public ApplicationException( String string )
    {
        super( string );
    }

    public ApplicationException( Integer code, String string )
    {
        super( string );
        setErrorCode ( code );
    }



    public ApplicationException()
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
