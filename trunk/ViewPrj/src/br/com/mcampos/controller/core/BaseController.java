package br.com.mcampos.controller.core;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;
import br.com.mcampos.util.system.CloudSystemSessionListener;
import br.com.mcampos.util.system.ImageUtil;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


public abstract class BaseController extends GenericForwardComposer
{
    public static final String browseHistoryParameterName = "browseHistory";
    public static final String bookmarkId = "bookmark";

    protected Component rootParent;
    protected transient HashMap<String, Object> arguments = new HashMap<String, Object>();
    protected static final String basePage = "/private/index.zul";

    protected Image imageClienteLogo;
    protected Image imageCompanyLogo;

    public BaseController( char c )
    {
        super( c );
    }

    public BaseController()
    {
        super();
    }

    protected String getCookie( String name )
    {
        Cookie[] cookies = ( ( HttpServletRequest )Executions.getCurrent().getNativeRequest() ).getCookies();

        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( name.equalsIgnoreCase( cookie.getName() ) )
                    return cookie.getValue();
            }
        }
        return null;
    }

    protected void setCookie( String name, String value )
    {
        //add cookie
        HttpServletResponse response = ( HttpServletResponse )Executions.getCurrent().getNativeResponse();
        Cookie userCookie = new Cookie( name, value );
        response.addCookie( userCookie );
    }


    protected void setCookie( String name, String value, int days )
    {
        //add cookie
        HttpServletResponse response = ( HttpServletResponse )Executions.getCurrent().getNativeResponse();
        Cookie userCookie = new Cookie( name, value );
        userCookie.setMaxAge( ( days * 24 ) * ( 3600 ) );
        response.addCookie( userCookie );
    }

    protected String getSessionID()
    {
        Object obj;

        obj = Sessions.getCurrent().getNativeSession();
        if ( obj instanceof HttpSession ) {
            HttpSession httpSession;

            httpSession = ( HttpSession )obj;
            return httpSession.getId();
        }
        else
            return null;
    }


    protected Object getSessionAttribute( String name )
    {
        return ( Sessions.getCurrent() != null ) ? Sessions.getCurrent().getAttribute( name ) : null;
    }

    protected void setSessionAttribute( String name, Object value )
    {
        if ( Sessions.getCurrent() != null )
            Sessions.getCurrent().setAttribute( name, value );
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
        setSessionAttribute( CloudSystemSessionListener.userSessionId, user );
    }

    protected AuthenticationDTO getLoggedInUser()
    {
        return ( AuthenticationDTO )getSessionAttribute( CloudSystemSessionListener.userSessionId );
    }

    protected Boolean isUserLogged()
    {
        return getLoggedInUser() != null ? true : false;
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
        Map map = Executions.getCurrent().getArg();
        Object param = null;

        if ( map != null )
            param = map.get( name );
        if ( param == null )
            param = Executions.getCurrent().getParameter( name );
        return param;
    }


    protected void gotoPage( String uri )
    {
        gotoPage( uri, null, arguments );
    }

    protected void gotoPage( String uri, Component parent )
    {
        gotoPage( uri, parent, arguments );
    }

    protected void gotoPage( String uri, Component parent, Map parameters )
    {
        if ( parent != null ) {
            try {
                if ( SysUtils.isEmpty( parent.getChildren() ) == false )
                    parent.getChildren().clear();
            }
            catch ( NullPointerException e ) {
                e = null;
            }
            //setBookmark( uri, parent, parameters );
            Executions.getCurrent().createComponents( uri, parent, parameters );
        }
        else {
            redirect( uri );
        }
    }

    protected void gotoPage( PageBrowseHistory history )
    {
        Component rootComponent;

        if ( history == null )
            return;
        if ( history.getRoot() != null ) {
            rootComponent = history.getRoot();
            if ( rootComponent != null && SysUtils.isEmpty( rootComponent.getChildren() ) == false ) {
                if ( desktop.equals( rootComponent.getDesktop() ) ) {
                    history.getRoot().getChildren().clear();
                    Executions.getCurrent().createComponents( history.getUri(), history.getRoot(), history.getParameter() );
                }
                else {
                    redirect( basePage );
                }
            }
        }
        else {
            redirect( history.getUri() );
        }
    }

    protected void gotoPage( String uri, Map parameters )
    {
        gotoPage( uri, null, parameters );
    }

    protected Component getRootParent()
    {
        return rootParent;
    }

    protected void redirect( String uri )
    {
        clearBookmark();
        Executions.getCurrent().sendRedirect( uri );
    }

    protected void redirectNewWindow( String uri )
    {
        clearBookmark();
        Executions.getCurrent().sendRedirect( uri, "_blank" );
    }


    protected void clearBookmark()
    {
        clearSessionParameter( browseHistoryParameterName );
    }

    protected void removeMe()
    {
        if ( rootParent != null ) {
            Component parent;

            parent = rootParent.getParent();
            if ( parent != null ) {
                List children = parent.getChildren();

                if ( children != null )
                    children.clear();
            }
        }
    }

    public void onClick$cmdCancel()
    {
        removeMe();
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
            return ServiceLocator.getInstance().getRemoteSession( remoteClass );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }


    protected void setLanguage( String setLang ) throws Exception
    {

        System.out.println( "Org Language: " + session.getAttribute( "preflang" ) );

        // set session wide language to new value
        if ( !setLang.isEmpty() ) {
            session.setAttribute( "preflang", setLang );
        }

        // read the session language attribute
        String sessLang = ( String )session.getAttribute( "preflang" );
        System.out.println( "New Language: " + session.getAttribute( "preflang" ) );

        // set the new preferred locale
        // otherwise it will use the default language (no session attribute and/or language parameter
        if ( !( sessLang == null ) ) {
            Locale preferredLocale = org.zkoss.util.Locales.getLocale( sessLang );
            session.setAttribute( org.zkoss.web.Attributes.PREFERRED_LOCALE, preferredLocale );
            org.zkoss.util.Locales.setThreadLocal( org.zkoss.util.Locales.getLocale( sessLang ) );
        }

        // Iterate through variables of the current class
        for ( Field f : this.getClass().getDeclaredFields() ) {
            // System.out.println(this.getClass().getName() + "." + f.getName() + " of type " + f.getType().getName());
            String compName = this.getClass().getName() + "." + f.getName();
            String compLabel = Labels.getLabel( compName );
            String compType = f.getType().getName();

            // only set lable if value found, otherwise it renders empty
            if ( !( compLabel == null ) ) {
                if ( compType.equals( "org.zkoss.zul.Button" ) )
                    ( ( Button )f.get( this ) ).setLabel( compLabel );
                else if ( compType.equals( "org.zkoss.zul.Label" ) )
                    ( ( Label )f.get( this ) ).setValue( compLabel );
                // Other component types need to be implemented if required
            }
        }
    }

    protected void setLabel( Label comp )
    {
        if ( comp != null ) {
            String value = Labels.getLabel( comp.getId() );
            if ( SysUtils.isEmpty( value ) == false )
                comp.setValue( value );
        }
    }

    protected void setLabel( LabelElement comp )
    {
        if ( comp != null ) {
            String value = Labels.getLabel( comp.getId() );
            if ( SysUtils.isEmpty( value ) == false )
                comp.setLabel( value );
        }
    }

    protected void setLabel( Panel comp )
    {
        if ( comp != null ) {
            String value = Labels.getLabel( comp.getId() );
            if ( SysUtils.isEmpty( value ) == false )
                comp.setTitle( value );
        }
    }

    protected String getLabel( String labelId )
    {
        return Labels.getLabel( labelId );
    }

    protected void loadCombobox( Combobox combo, List list )
    {
        if ( combo == null || SysUtils.isEmpty( list ) )
            return;

        if ( combo.getChildren() != null )
            combo.getChildren().clear();
        if ( SysUtils.isEmpty( list ) == false ) {
            for ( Object dto : list ) {
                Comboitem item = combo.appendItem( dto.toString() );
                if ( item != null )
                    item.setValue( dto );
            }
        }
    }

    protected void setParameter( String name, Object value )
    {
        arguments.put( name, value );
    }

    protected void setClientLogo( byte[] image )
    {
        if ( imageClienteLogo != null )
            ImageUtil.loadImage( imageClienteLogo, image );
    }
}
