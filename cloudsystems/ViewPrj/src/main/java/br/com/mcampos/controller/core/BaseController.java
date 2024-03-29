package br.com.mcampos.controller.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.impl.LabelElement;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.CloudSystemSessionListener;
import br.com.mcampos.util.system.ImageUtil;

public abstract class BaseController<COMPONENT extends Component> extends GenericForwardComposer<COMPONENT>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 835366511251388934L;
	public static final String browseHistoryParameterName = "browseHistory";
	public static final String bookmarkId = "bookmark";

	protected Component rootParent;
	protected transient HashMap<String, Object> arguments = new HashMap<String, Object>( );
	protected static final String basePage = "/private/index.zul";
	private static final Logger LOGGER = LoggerFactory.getLogger( LoggedBaseController.class.getSimpleName( ) );

	protected Image imageClienteLogo;
	protected Image imageCompanyLogo;

	public BaseController( )
	{
		super( );
	}

	public BaseController( char c )
	{
		super( );
	}

	@Override
	public void doAfterCompose( COMPONENT comp ) throws Exception
	{
		LOGGER.info( "Called doAfterCompose( COMPONENT comp ) throws Exception FROM BaseController[" + comp.getId( ) + "]" );
		super.doAfterCompose( comp );
		int a = 0;

		if ( a == 0 ) {
			a++;
		}
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
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent( ).getNativeResponse( );
		Cookie userCookie = new Cookie( name, value );
		response.addCookie( userCookie );
	}

	protected void setCookie( String name, String value, int days )
	{
		// add cookie
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent( ).getNativeResponse( );
		Cookie userCookie = new Cookie( name, value );
		userCookie.setMaxAge( ( days * 24 ) * ( 3600 ) );
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

	/*
	protected BookmarkHelper getHistory()
	{
	    BookmarkHelper bookmarkHelper;

	    bookmarkHelper = ( BookmarkHelper )getSessionAttribute( browseHistoryParameterName );
	    if ( bookmarkHelper == null ) {
	        bookmarkHelper = new BookmarkHelper();
	        setSessionAttribute( browseHistoryParameterName, bookmarkHelper );
	    }
	    return bookmarkHelper;
	}
	*/

	protected void setLoggedInUser( AuthenticationDTO user )
	{
		this.setSessionAttribute( CloudSystemSessionListener.userSessionId, user );
	}

	protected AuthenticationDTO getLoggedInUser( )
	{
		return (AuthenticationDTO) this.getSessionAttribute( CloudSystemSessionListener.userSessionId );
	}

	protected Boolean isUserLogged( )
	{
		return this.getLoggedInUser( ) != null ? true : false;
	}

	protected void setSessionParameter( String name, Object value )
	{
		this.setSessionAttribute( name, value );
	}

	protected Object getSessionParameter( String name )
	{
		Object value;

		value = this.getSessionAttribute( name );
		return value;
	}

	protected void clearSessionParameter( String name )
	{
		this.setSessionParameter( name, null );
	}

	protected Object getParameter( String name )
	{
		Map map = Executions.getCurrent( ).getArg( );
		Object param = null;

		if ( map != null ) {
			param = map.get( name );
		}
		if ( param == null ) {
			param = Executions.getCurrent( ).getParameter( name );
		}
		return param;
	}

	protected void gotoPage( String uri )
	{
		this.gotoPage( uri, null, this.arguments );
	}

	protected void gotoPage( String uri, Component parent )
	{
		this.gotoPage( uri, parent, this.arguments );
	}

	protected void gotoPage( String uri, Component parent, Map parameters )
	{
		if ( parent != null ) {
			try {
				if ( SysUtils.isEmpty( parent.getChildren( ) ) == false ) {
					parent.getChildren( ).clear( );
				}
			}
			catch ( NullPointerException e ) {
				e = null;
			}
			// setBookmark( uri, parent, parameters );
			Executions.getCurrent( ).createComponents( uri, parent, parameters );
		}
		else {
			this.redirect( uri );
		}
	}

	protected void gotoPage( PageBrowseHistory history )
	{
		Component rootComponent;

		if ( history == null ) {
			return;
		}
		if ( history.getRoot( ) != null ) {
			rootComponent = history.getRoot( );
			if ( rootComponent != null && SysUtils.isEmpty( rootComponent.getChildren( ) ) == false ) {
				if ( Executions.getCurrent( ).getDesktop( ).equals( rootComponent.getDesktop( ) ) ) {
					history.getRoot( ).getChildren( ).clear( );
					Executions.getCurrent( ).createComponents( history.getUri( ), history.getRoot( ), history.getParameter( ) );
				}
				else {
					this.redirect( basePage );
				}
			}
		}
		else {
			this.redirect( history.getUri( ) );
		}
	}

	protected void gotoPage( String uri, Map parameters )
	{
		this.gotoPage( uri, null, parameters );
	}

	protected Component getRootParent( )
	{
		return this.rootParent;
	}

	protected void redirect( String uri )
	{
		this.clearBookmark( );
		Executions.getCurrent( ).sendRedirect( uri );
	}

	protected void redirectNewWindow( String uri )
	{
		this.clearBookmark( );
		Executions.getCurrent( ).sendRedirect( uri, "_blank" );
	}

	protected void clearBookmark( )
	{
		this.clearSessionParameter( browseHistoryParameterName );
	}

	protected void removeMe( )
	{
		if ( this.rootParent != null ) {
			Component parent;

			parent = this.rootParent.getParent( );
			if ( parent != null ) {
				List children = parent.getChildren( );

				if ( children != null ) {
					children.clear( );
				}
			}
		}
	}

	public void onClick$cmdCancel( )
	{
		this.removeMe( );
	}

	/*
	protected void setBookmark( String uri, Component parent, Map parameters )
	{
	    String strBookmark;
	    BookmarkHelper bookmarkHelper;

	    bookmarkHelper = getHistory();
	    bookmarkHelper.add( uri, parent, parameters );
	    strBookmark = String.format( "%s%d", bookmarkId, bookmarkHelper.get().size() - 1 );
	    if ( desktop != null )
	        desktop.setBookmark( strBookmark );
	}
	*/

	protected Object getRemoteSession( Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( remoteClass, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

	protected void setLanguage( String setLang ) throws Exception
	{

		System.out.println( "Org Language: " + Sessions.getCurrent( ).getAttribute( "preflang" ) );

		// set session wide language to new value
		if ( !setLang.isEmpty( ) ) {
			Sessions.getCurrent( ).setAttribute( "preflang", setLang );
		}

		// read the session language attribute
		String sessLang = (String) Sessions.getCurrent( ).getAttribute( "preflang" );
		System.out.println( "New Language: " + Sessions.getCurrent( ).getAttribute( "preflang" ) );

		// set the new preferred locale
		// otherwise it will use the default language (no session attribute and/or language parameter
		if ( !( sessLang == null ) ) {
			Locale preferredLocale = org.zkoss.util.Locales.getLocale( sessLang );
			Sessions.getCurrent( ).setAttribute( org.zkoss.web.Attributes.PREFERRED_LOCALE, preferredLocale );
			org.zkoss.util.Locales.setThreadLocal( org.zkoss.util.Locales.getLocale( sessLang ) );
		}

		// Iterate through variables of the current class
		for ( Field f : this.getClass( ).getDeclaredFields( ) ) {
			// System.out.println(this.getClass().getName() + "." + f.getName() + " of type " + f.getType().getName());
			String compName = this.getClass( ).getName( ) + "." + f.getName( );
			String compLabel = Labels.getLabel( compName );
			String compType = f.getType( ).getName( );

			// only set lable if value found, otherwise it renders empty
			if ( !( compLabel == null ) )
			{
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

	protected String getLabel( String labelId )
	{
		return Labels.getLabel( labelId );
	}

	protected void loadCombobox( Combobox combo, List list )
	{
		if ( combo == null || SysUtils.isEmpty( list ) ) {
			return;
		}

		if ( combo.getChildren( ) != null ) {
			combo.getChildren( ).clear( );
		}
		if ( SysUtils.isEmpty( list ) == false ) {
			for ( Object dto : list ) {
				Comboitem item = combo.appendItem( dto.toString( ) );
				if ( item != null ) {
					item.setValue( dto );
				}
			}
		}
	}

	protected void setParameter( String name, Object value )
	{
		this.arguments.put( name, value );
	}

	protected void setClientLogo( byte[ ] image )
	{
		if ( this.imageClienteLogo != null ) {
			ImageUtil.loadImage( this.imageClienteLogo, image );
		}
	}

}
