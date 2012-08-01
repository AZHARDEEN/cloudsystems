package br.com.mcampos.web.fdigital.samabsd.webservice;

public class InovadoraWSProxy implements br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWS
{
	private String _endpoint = null;
	private br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWS inovadoraWS = null;

	public InovadoraWSProxy( )
	{
		_initInovadoraWSProxy( );
	}

	public InovadoraWSProxy( String endpoint )
	{
		this._endpoint = endpoint;
		_initInovadoraWSProxy( );
	}

	private void _initInovadoraWSProxy( )
	{
		try {
			this.inovadoraWS = ( new br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWSServiceLocator( ) ).getInovadoraWSPort( );
			if ( this.inovadoraWS != null ) {
				if ( this._endpoint != null ) {
					( (javax.xml.rpc.Stub) this.inovadoraWS )._setProperty( "javax.xml.rpc.service.endpoint.address",
							this._endpoint );
				}
				else {
					this._endpoint = (String) ( (javax.xml.rpc.Stub) this.inovadoraWS )
							._getProperty( "javax.xml.rpc.service.endpoint.address" );
				}
			}

		}
		catch ( javax.xml.rpc.ServiceException serviceException ) {
		}
	}

	public String getEndpoint( )
	{
		return this._endpoint;
	}

	public void setEndpoint( String endpoint )
	{
		this._endpoint = endpoint;
		if ( this.inovadoraWS != null ) {
			( (javax.xml.rpc.Stub) this.inovadoraWS )._setProperty( "javax.xml.rpc.service.endpoint.address", this._endpoint );
		}

	}

	public br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWS getInovadoraWS( )
	{
		if ( this.inovadoraWS == null ) {
			_initInovadoraWSProxy( );
		}
		return this.inovadoraWS;
	}

	@Override
	public java.lang.Integer xmlType( java.lang.String arg0 ) throws java.rmi.RemoteException
	{
		if ( this.inovadoraWS == null ) {
			_initInovadoraWSProxy( );
		}
		return this.inovadoraWS.xmlType( arg0 );
	}

	@Override
	public void addFicha( java.lang.String xmlContent ) throws java.rmi.RemoteException
	{
		if ( this.inovadoraWS == null ) {
			_initInovadoraWSProxy( );
		}
		this.inovadoraWS.addFicha( xmlContent );
	}
}