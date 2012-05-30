package br.com.mcampos.ejb.params;

import java.io.Serializable;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface SystemParameterSessionLocal extends BaseSessionInterface<SystemParameters>
{
	Integer getMaxTryCount( );

	Integer getIntValue( Serializable key );

	String getStringValue( Serializable key );
	
	public String get( String id );

	public void set( String id, String description, String value );

	public void remove( String id );

	public Integer getInt( String id );

	public void setInt( String id, String description, Integer value );

	public Double getDouble( String id );

	public void setDouble( String id, String description, Double value );

	public Boolean getBool( String id );

	public void setBool( String id, String description, Boolean value );
	
}
