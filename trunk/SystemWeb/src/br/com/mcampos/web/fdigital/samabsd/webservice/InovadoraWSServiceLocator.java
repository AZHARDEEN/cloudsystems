/**
 * InovadoraWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.mcampos.web.fdigital.samabsd.webservice;

public class InovadoraWSServiceLocator extends org.apache.axis.client.Service implements br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWSService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2907864682670800513L;

	public InovadoraWSServiceLocator( )
	{
	}

	public InovadoraWSServiceLocator( org.apache.axis.EngineConfiguration config )
	{
		super( config );
	}

	public InovadoraWSServiceLocator( java.lang.String wsdlLoc, javax.xml.namespace.QName sName )
			throws javax.xml.rpc.ServiceException
	{
		super( wsdlLoc, sName );
	}

	// Use to get a proxy class for InovadoraWSPort
	private java.lang.String InovadoraWSPort_address = "http://byron.apilab.ufsc.br:8080/InovadoraWS/InovadoraWS";

	@Override
	public java.lang.String getInovadoraWSPortAddress( )
	{
		return this.InovadoraWSPort_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String InovadoraWSPortWSDDServiceName = "InovadoraWSPort";

	public java.lang.String getInovadoraWSPortWSDDServiceName( )
	{
		return this.InovadoraWSPortWSDDServiceName;
	}

	public void setInovadoraWSPortWSDDServiceName( java.lang.String name )
	{
		this.InovadoraWSPortWSDDServiceName = name;
	}

	@Override
	public br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWS getInovadoraWSPort( ) throws javax.xml.rpc.ServiceException
	{
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL( this.InovadoraWSPort_address );
		}
		catch ( java.net.MalformedURLException e ) {
			throw new javax.xml.rpc.ServiceException( e );
		}
		return getInovadoraWSPort( endpoint );
	}

	@Override
	public br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWS getInovadoraWSPort( java.net.URL portAddress )
			throws javax.xml.rpc.ServiceException
	{
		try {
			br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWSPortBindingStub _stub = new br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWSPortBindingStub(
					portAddress, this );
			_stub.setPortName( getInovadoraWSPortWSDDServiceName( ) );
			return _stub;
		}
		catch ( org.apache.axis.AxisFault e ) {
			return null;
		}
	}

	public void setInovadoraWSPortEndpointAddress( java.lang.String address )
	{
		this.InovadoraWSPort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no port for the given interface, then ServiceException is thrown.
	 */
	@Override
	public java.rmi.Remote getPort( Class serviceEndpointInterface ) throws javax.xml.rpc.ServiceException
	{
		try {
			if ( br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWS.class.isAssignableFrom( serviceEndpointInterface ) ) {
				br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWSPortBindingStub _stub = new br.com.mcampos.web.fdigital.samabsd.webservice.InovadoraWSPortBindingStub(
						new java.net.URL( this.InovadoraWSPort_address ), this );
				_stub.setPortName( getInovadoraWSPortWSDDServiceName( ) );
				return _stub;
			}
		}
		catch ( java.lang.Throwable t ) {
			throw new javax.xml.rpc.ServiceException( t );
		}
		throw new javax.xml.rpc.ServiceException( "There is no stub implementation for the interface:  "
				+ ( serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName( ) ) );
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no port for the given interface, then ServiceException is thrown.
	 */
	@Override
	public java.rmi.Remote getPort( javax.xml.namespace.QName portName, Class serviceEndpointInterface )
			throws javax.xml.rpc.ServiceException
	{
		if ( portName == null ) {
			return getPort( serviceEndpointInterface );
		}
		java.lang.String inputPortName = portName.getLocalPart( );
		if ( "InovadoraWSPort".equals( inputPortName ) ) {
			return getInovadoraWSPort( );
		}
		else {
			java.rmi.Remote _stub = getPort( serviceEndpointInterface );
			( (org.apache.axis.client.Stub) _stub ).setPortName( portName );
			return _stub;
		}
	}

	@Override
	public javax.xml.namespace.QName getServiceName( )
	{
		return new javax.xml.namespace.QName( "http://webservice.samabsd.com.br/", "InovadoraWSService" );
	}

	private java.util.HashSet ports = null;

	@Override
	public java.util.Iterator getPorts( )
	{
		if ( this.ports == null ) {
			this.ports = new java.util.HashSet( );
			this.ports.add( new javax.xml.namespace.QName( "http://webservice.samabsd.com.br/", "InovadoraWSPort" ) );
		}
		return this.ports.iterator( );
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress( java.lang.String portName, java.lang.String address ) throws javax.xml.rpc.ServiceException
	{

		if ( "InovadoraWSPort".equals( portName ) ) {
			setInovadoraWSPortEndpointAddress( address );
		}
		else
		{ // Unknown Port Name
			throw new javax.xml.rpc.ServiceException( " Cannot set Endpoint Address for Unknown Port" + portName );
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress( javax.xml.namespace.QName portName, java.lang.String address )
			throws javax.xml.rpc.ServiceException
	{
		setEndpointAddress( portName.getLocalPart( ), address );
	}

}
