package br.com.mcampos.web.core;

import java.io.Serializable;

public interface ISessionParameter extends Serializable
{

	public static final String currentPrincipal = "currentPrincipal";

	String getSessionID( );

	Object getSessionAttribute( String name );

	void setSessionAttribute( String name, Object value );

	void setSessionParameter( String name, Object value );

	Object getSessionParameter( String name );

	void clearSessionParameter( String name );

	void pushSessionParameter( String name, Object value );

	Object popSessionParameter( String name );
}
