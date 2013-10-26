package br.com.mcampos.web.core;

import javax.servlet.http.Cookie;

public interface CookieInterface
{
	Cookie findCookie( String name );

	String getCookie( String name );

	void deleteCookie( String name );

	void setCookie( String name, String value );

	void setCookie( String name, String value, int days );

}
