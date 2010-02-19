package br.com.mcampos.controller.core;

import br.com.mcampos.dto.security.AuthenticationDTO;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;
import br.com.mcampos.util.system.CloudSystemSessionListener;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public abstract class BaseController extends GenericForwardComposer
{
    public static final String browseHistoryParameterName = "browseHistory";
    protected Component rootParent;
    public static final String bookmarkId = "bookmark";

    public BaseController( char c )
    {
        super( c );
    }

    public BaseController()
    {
        super();
    }

    public String getCookie( String name )
    {
        Cookie[] cookies = ( ( HttpServletRequest )Executions.getCurrent().getNativeRequest() ).getCookies();


        for ( Cookie cookie : cookies ) {
            if ( name.equalsIgnoreCase( cookie.getName() ) )
                return cookie.getValue();
        }
        return null;
    }

    public void setCookie( String name, String value )
    {
        //add cookie
        HttpServletResponse response = ( HttpServletResponse )Executions.getCurrent().getNativeResponse();
        Cookie userCookie = new Cookie( name, value );
        response.addCookie( userCookie );
    }


    public void setCookie( String name, String value, int days )
    {
        //add cookie
        HttpServletResponse response = ( HttpServletResponse )Executions.getCurrent().getNativeResponse();
        Cookie userCookie = new Cookie( name, value );
        userCookie.setMaxAge( ( days * 24 ) * ( 3600 ) );
        response.addCookie( userCookie );
    }

    public String getSessionID()
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


    public Object getSessionAttribute( String name )
    {
        return ( Sessions.getCurrent() != null ) ? Sessions.getCurrent().getAttribute( name ) : null;
    }

    public void setSessionAttribute( String name, Object value )
    {
        if ( Sessions.getCurrent() != null )
            Sessions.getCurrent().setAttribute( name, value );
    }

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

    public void setLoggedInUser( AuthenticationDTO user )
    {
        setSessionAttribute( CloudSystemSessionListener.userSessionId, user );
    }

    public AuthenticationDTO getLoggedInUser()
    {
        return ( AuthenticationDTO )getSessionAttribute( CloudSystemSessionListener.userSessionId );
    }

    public Boolean isUserLogged()
    {
        return getLoggedInUser() != null ? true : false;
    }

    public void setParameter( String name, Object value )
    {
        setSessionAttribute( name, value );
    }

    public Object getParameter( String name )
    {
        Object value;

        value = getSessionAttribute( name );
        return value;
    }

    public void clearParameter( String name )
    {
        setParameter( name, null );
    }


    protected void gotoPage( String uri )
    {
        //Executions.getCurrent().sendRedirect( uri );
        //Executions.createComponents( uri, self, null);
        gotoPage( uri, null, null );
    }

    protected void gotoPage( String uri, Component parent )
    {
        gotoPage( uri, parent, null );
    }

    protected void gotoPage( String uri, Component parent, Map parameters )
    {
        //Executions.getCurrent().sendRedirect( uri );
        if ( parent != null ) {
            if ( SysUtils.isEmpty( parent.getChildren() ) == false )
                parent.getChildren().clear();
            Executions.getCurrent().createComponents( uri, parent, parameters );
            setBookmark( uri, parent, parameters );
        }
        else
            Executions.getCurrent().sendRedirect( uri );
    }

    protected void gotoPage( PageBrowseHistory history )
    {
        if ( history == null )
            return;
        if ( history.getRoot() != null ) {
            if ( SysUtils.isEmpty( history.getRoot().getChildren() ) == false )
                history.getRoot().getChildren().clear();
            Executions.getCurrent().createComponents( history.getUri(), history.getRoot(), history.getParameter() );
        }
        else
            Executions.getCurrent().sendRedirect( history.getUri() );

    }

    protected void gotoPage( String uri, Map parameters )
    {
        gotoPage( uri, null, parameters );
    }

    public void setRootParent( Component rootParent )
    {
        this.rootParent = rootParent;
    }

    public Component getRootParent()
    {
        return rootParent;
    }

    protected void redirect( String uri )
    {
        Executions.getCurrent().sendRedirect( uri );
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

    protected void setBookmark( String uri, Component parent, Map parameters )
    {
        String strBookmark;
        BookmarkHelper bookmarkHelper;

        bookmarkHelper = getHistory();
        bookmarkHelper.add( uri, parent, parameters );
        strBookmark = String.format( "%s%d", bookmarkId, bookmarkHelper.get().size() - 1 );
        desktop.setBookmark( strBookmark );
    }


    protected Object getRemoteSession( Class remoteClass )
    {
        try {
            return ServiceLocator.getInstance().getRemoteSession( remoteClass );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }
}
