package br.com.mcampos.web.core;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Executions;

public final class CookieImpl implements CookieInterface, Serializable
{
	private static final long serialVersionUID = -7459706590264490807L;

	@Override
	public Cookie findCookie( String name )
	{
		Cookie[ ] cookies = ((HttpServletRequest) Executions.getCurrent( ).getNativeRequest( )).getCookies( );

		if( cookies != null ) {
			for( Cookie cookie : cookies ) {
				if( name.equalsIgnoreCase( cookie.getName( ) ) ) {
					return cookie;
				}
			}
		}
		return null;

	}

	@Override
	public String getCookie( String name )
	{
		Cookie c = findCookie( name );
		if( c != null ) {
			return c.getValue( );
		}
		return null;
	}

	@Override
	public void deleteCookie( String name )
	{
		Cookie c = findCookie( name );
		if( c != null ) {
			c.setMaxAge( 0 );
		}
	}

	@Override
	public void setCookie( String name, String value )
	{
		// add cookie
		setCookie( name, value, 15 );
	}

	@Override
	public void setCookie( String name, String value, int days )
	{
		// add cookie
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent( ).getNativeResponse( );
		Cookie userCookie = new Cookie( name, value );
		if( days > 0 ) {
			userCookie.setMaxAge( (days * 24) * (3600) );
		}
		response.addCookie( userCookie );
	}

}
