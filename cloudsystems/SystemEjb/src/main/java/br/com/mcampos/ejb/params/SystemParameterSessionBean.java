package br.com.mcampos.ejb.params;

import java.io.Serializable;

import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.system.SystemParameters;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "SystemParameterSession", mappedName = "SystemParameterSession" )
public class SystemParameterSessionBean extends SimpleSessionBean<SystemParameters> implements SystemParameterSession, SystemParameterSessionLocal
{
	private static final long serialVersionUID = -5687663888358795013L;

	@Override
	protected Class<SystemParameters> getEntityClass( )
	{
		return SystemParameters.class;
	}

	@Override
	public Integer getMaxTryCount( )
	{
		Integer param = this.getInt( SystemParameters.maxLoginTryCount );
		if ( param == null ) {
			return 5;
		}
		else {
			return param;
		}
	}

	@Override
	public Integer getIntValue( Serializable key )
	{
		return Integer.parseInt( this.getStringValue( key ) );
	}

	@Override
	public String getStringValue( Serializable key )
	{
		SystemParameters param = this.get( key );
		if ( param != null ) {
			return param.getValue( );
		}
		else {
			return "";
		}
	}

	@Override
	public String get( String id )
	{
		if ( SysUtils.isEmpty( id ) ) {
			return "";
		}
		SystemParameters p = super.get( id );
		if ( p != null ) {
			return p.getValue( );
		}
		else {
			return "";
		}
	}

	@Override
	public void set( String id, String description, String value )
	{
		if ( id == null || value == null ) {
			return;
		}
		SystemParameters p = super.get( id );
		if ( p == null ) {
			p = new SystemParameters( );
		}
		p.setId( id );
		p.setDescription( description );
		p.setValue( value );
		this.merge( p );
	}

	@Override
	public void remove( PrincipalDTO auth, String id )
	{
		if ( SysUtils.isEmpty( id ) ) {
			return;
		}
		SystemParameters p = super.get( id );
		if ( p != null ) {
			super.remove( auth, p );
		}
	}

	@Override
	public Integer getInt( String id )
	{
		String value = this.get( id );
		if ( SysUtils.isEmpty( value ) ) {
			return null;
		}
		try {
			return Integer.parseInt( value );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	@Override
	public void setInt( String id, String description, Integer value )
	{
		if ( value != null ) {
			this.set( id, description, value.toString( ) );
		}
	}

	@Override
	public Double getDouble( String id )
	{
		String value = this.get( id );
		if ( SysUtils.isEmpty( value ) ) {
			return null;
		}
		try {
			return Double.parseDouble( value );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	@Override
	public void setDouble( String id, String description, Double value )
	{
		if ( value != null ) {
			this.set( id, description, value.toString( ) );
		}

	}

	@Override
	public Boolean getBool( String id )
	{
		String value = this.get( id );
		if ( SysUtils.isEmpty( value ) ) {
			return null;
		}
		try {
			return Boolean.parseBoolean( value );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	@Override
	public void setBool( String id, String description, Boolean value )
	{
		if ( value != null ) {
			this.set( id, description, value.toString( ) );
		}
	}

}
