package br.com.mcampos.web.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.LabelElement;
import org.zkoss.zul.impl.XulElement;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.locator.ServiceLocator;

public abstract class BaseController<T extends Component> extends SelectorComposer<T>
{

	private static final long serialVersionUID = -246593656005980750L;
	private transient HashMap<String, Object> arguments = new HashMap<String, Object>( );

	@Wire( "menuitem, menu, label, panel, listheader, button, toolbarbutton" )
	private List<XulElement> labels;

	@Wire( "window" )
	private Window mainWindow;

	public BaseController( )
	{
		super( );
	}

	protected String getCookie( String name )
	{
		Cookie[ ] cookies = ( (HttpServletRequest) Executions.getCurrent( ).getNativeRequest( ) ).getCookies( );

		if ( cookies != null ) {
			for ( Cookie cookie : cookies ) {
				if ( name.equalsIgnoreCase( cookie.getName( ) ) ) {
					return cookie.getValue( );
				}
			}
		}
		return null;
	}

	protected void setCookie( String name, String value )
	{
		// add cookie
		setCookie( name, value, 0 );
	}

	protected void setCookie( String name, String value, int days )
	{
		// add cookie
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent( ).getNativeResponse( );
		Cookie userCookie = new Cookie( name, value );
		if ( days > 0 ) {
			userCookie.setMaxAge( ( days * 24 ) * ( 3600 ) );
		}
		response.addCookie( userCookie );
	}

	protected String getSessionID( )
	{
		Object obj;

		obj = Sessions.getCurrent( ).getNativeSession( );
		if ( obj instanceof HttpSession ) {
			HttpSession httpSession;

			httpSession = (HttpSession) obj;
			return httpSession.getId( );
		}
		else {
			return null;
		}
	}

	protected Object getSessionAttribute( String name )
	{
		return ( Sessions.getCurrent( ) != null ) ? Sessions.getCurrent( ).getAttribute( name ) : null;
	}

	protected void setSessionAttribute( String name, Object value )
	{
		if ( Sessions.getCurrent( ) != null ) {
			Sessions.getCurrent( ).setAttribute( name, value );
		}
	}

	protected void setSessionParameter( String name, Object value )
	{
		setSessionAttribute( name, value );
	}

	protected Object getSessionParameter( String name )
	{
		Object value;

		value = getSessionAttribute( name );
		return value;
	}

	protected void clearSessionParameter( String name )
	{
		setSessionParameter( name, null );
	}

	protected Object getParameter( String name )
	{
		Map<?, ?> map = Executions.getCurrent( ).getArg( );
		Object param = null;

		if ( map != null ) {
			param = map.get( name );
		}
		if ( param == null ) {
			param = Executions.getCurrent( ).getParameter( name );
		}
		return param;
	}

	protected void gotoPage( String uri, boolean mustClear )
	{
		gotoPage( uri, null, this.arguments, mustClear );
	}

	protected void gotoPage( String uri, Component parent, boolean mustClear )
	{
		gotoPage( uri, parent, this.arguments, mustClear );
	}

	protected void gotoPage( String uri, Component parent, Map<?, ?> parameters, boolean mustClear )
	{
		if ( parent == null && getMainWindow( ) != null ) {
			parent = getMainWindow( ).getParent( );
		}
		if ( parent != null ) {
			if ( mustClear ) {
				if ( SysUtils.isEmpty( parent.getChildren( ) ) == false ) {
					parent.getChildren( ).clear( );
				}
			}
			Component c = Executions.getCurrent( ).createComponents( uri, parent, parameters );
			if ( c != null )
			{
				c.setAttribute( "zulPage", uri );
			}
		}
		else {
			redirect( uri );
		}
	}

	protected void redirect( String uri )
	{
		Executions.getCurrent( ).sendRedirect( uri );
	}

	protected void setLanguage( String setLang ) throws Exception
	{
		// set session wide language to new value
		if ( SysUtils.isEmpty( setLang ) ) {
			setLang = "pt_BR";
		}
		Sessions.getCurrent( ).setAttribute( "preflang", setLang );
		// read the session language attribute
		String sessLang = (String) Sessions.getCurrent( ).getAttribute( "preflang" );
		// set the new preferred locale
		// otherwise it will use the default language (no session attribute
		// and/or language parameter
		if ( !( sessLang == null ) ) {
			Locale preferredLocale = org.zkoss.util.Locales.getLocale( sessLang );
			Sessions.getCurrent( ).setAttribute( org.zkoss.web.Attributes.PREFERRED_LOCALE, preferredLocale );
			org.zkoss.util.Locales.setThreadLocal( org.zkoss.util.Locales.getLocale( sessLang ) );
		}
		// Iterate through variables of the current class
		for ( Field f : this.getClass( ).getDeclaredFields( ) ) {
			String compName = this.getClass( ).getName( ) + "." + f.getName( );
			String compLabel = Labels.getLabel( compName );
			// only set lable if value found, otherwise it renders empty
			if ( !( compLabel == null ) ) {
				String compType = f.getType( ).getName( );
				if ( compType.equals( "org.zkoss.zul.Button" ) ) {
					( (Button) f.get( this ) ).setLabel( compLabel );
				}
				else if ( compType.equals( "org.zkoss.zul.Label" ) )
				{
					( (Label) f.get( this ) ).setValue( compLabel );
					// Other component types need to be implemented if required
				}
			}
		}
	}

	protected void setLabel( Label comp )
	{
		if ( comp != null ) {
			String value = Labels.getLabel( comp.getId( ) );
			if ( SysUtils.isEmpty( value ) == false ) {
				comp.setValue( value );
			}
		}
	}

	protected void setLabel( LabelElement comp )
	{
		if ( comp != null ) {
			String value = Labels.getLabel( comp.getId( ) );
			if ( SysUtils.isEmpty( value ) == false ) {
				comp.setLabel( value );
			}
		}
	}

	protected void setLabel( Panel comp )
	{
		if ( comp != null ) {
			String value = Labels.getLabel( comp.getId( ) );
			if ( SysUtils.isEmpty( value ) == false ) {
				comp.setTitle( value );
			}
		}
	}

	protected void setLabel( XulElement comp )
	{
		if ( comp != null ) {
			Labels.getLabel( comp.getId( ) );
			if ( comp instanceof Label ) {
				setLabel( (Label) comp );
			}
			else if ( comp instanceof Panel ) {
				setLabel( (Panel) comp );
			}
			else if ( comp instanceof LabelElement ) {
				setLabel( (LabelElement) comp );
			}
		}
	}

	protected String getLabel( String labelId )
	{
		return Labels.getLabel( labelId );
	}

	@Override
	public void doAfterCompose( T comp ) throws Exception
	{
		super.doAfterCompose( comp );
		if ( SysUtils.isEmpty( this.labels ) == false ) {
			for ( XulElement item : this.labels ) {
				setLabel( item );
			}
		}
	}

	protected void showErrorMessage( String msg, String title )
	{
		try {
			Messagebox.show( msg, title, Messagebox.OK, Messagebox.ERROR );
		}
		catch ( Exception e ) {
			e = null;
		}
	}

	protected Object getSession( Class<?> zClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( zClass );
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return null;
	}

	protected Window getMainWindow( )
	{
		return this.mainWindow;
	}
}
