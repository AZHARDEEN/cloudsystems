package br.com.mcampos.web.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.Locales;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.metainfo.PageDefinition;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.LabelElement;
import org.zkoss.zul.impl.XulElement;
import org.zkoss.zul.theme.Themes;

import br.com.mcampos.dto.core.CredentialDTO;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.event.IDialogEvent;
import br.com.mcampos.web.core.report.JasperReportController;
import br.com.mcampos.web.core.report.ReportItem;
import br.com.mcampos.zkutils.ReportEvent;

public abstract class BaseController<T extends Component> extends SelectorComposer<T> implements ISessionParameter, CookieInterface
{

	public static final String baseLoggedIndexPage = "/private/index.zul";

	private static final long serialVersionUID = -246593656005980750L;
	private transient HashMap<String, Object> arguments = new HashMap<String, Object>( );
	private static final Logger logger = LoggerFactory.getLogger( BaseController.class );

	@Wire( "menuitem, menu, label, panel, listheader, button, toolbarbutton" )
	private List<XulElement> labels;

	@Wire( "window" )
	private Window mainWindow;

	private String requestPath;

	private CookieImpl cookieManager;

	public BaseController( )
	{
		super( );
	}

	private CookieImpl getCookieManager( )
	{
		if ( cookieManager == null )
			cookieManager = new CookieImpl( );
		return cookieManager;
	}

	@Override
	public Cookie findCookie( String name )
	{
		return getCookieManager( ).findCookie( name );
	}

	@Override
	public String getCookie( String name )
	{
		return getCookieManager( ).getCookie( name );
	}

	@Override
	public void deleteCookie( String name )
	{
		getCookieManager( ).deleteCookie( name );
	}

	@Override
	public void setCookie( String name, String value )
	{
		getCookieManager( ).setCookie( name, value );
	}

	@Override
	public void setCookie( String name, String value, int days )
	{
		getCookieManager( ).setCookie( name, value, days );
	}

	@Override
	public String getSessionID( )
	{
		Object obj;

		obj = Sessions.getCurrent( ).getNativeSession( );
		if ( obj instanceof HttpSession ) {
			HttpSession httpSession;

			httpSession = (HttpSession) obj;
			return httpSession.getId( );
		}
		else
			return null;
	}

	protected CredentialDTO getCredential( )
	{
		CredentialDTO c = new CredentialDTO( );

		c.setLocale( Locales.getCurrent( ) );
		if ( Executions.getCurrent( ) != null ) {
			c.setRemoteAddr( Executions.getCurrent( ).getRemoteAddr( ) );
			c.setRemoteHost( Executions.getCurrent( ).getRemoteHost( ) );
			c.setProgram( Executions.getCurrent( ).getBrowser( ) );
		}
		else
			c.setRemoteAddr( "n/a" );
		c.setSessionId( getSessionID( ) );
		return c;
	}

	@Override
	public Object getSessionAttribute( String name )
	{
		return ( Sessions.getCurrent( ) != null ) ? Sessions.getCurrent( ).getAttribute( name ) : null;
	}

	@Override
	public void setSessionAttribute( String name, Object value )
	{
		if ( Sessions.getCurrent( ) != null )
			Sessions.getCurrent( ).setAttribute( name, value );
	}

	@Override
	public void setSessionParameter( String name, Object value )
	{
		setSessionAttribute( name, value );
	}

	@Override
	public Object getSessionParameter( String name )
	{
		Object value;

		value = getSessionAttribute( name );
		return value;
	}

	@Override
	public void clearSessionParameter( String name )
	{
		setSessionParameter( name, null );
	}

	@Override
	public void pushSessionParameter( String name, Object value )
	{
		setSessionAttribute( name, value );
	}

	@Override
	public Object popSessionParameter( String name )
	{
		Object obj = getSessionAttribute( name );
		clearSessionParameter( name );
		return obj;
	}

	protected Object getParameter( String name )
	{
		Map<?, ?> map = Executions.getCurrent( ).getArg( );
		Object param = null;

		if ( map != null )
			param = map.get( name );
		if ( param == null )
			param = Executions.getCurrent( ).getParameter( name );
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
		if ( parent == null && getMainWindow( ) != null )
			parent = getMainWindow( ).getParent( );
		if ( parent != null ) {
			if ( mustClear )
				if ( SysUtils.isEmpty( parent.getChildren( ) ) == false )
					parent.getChildren( ).clear( );
			createComponents( uri, parent, parameters );
		}
		else
			redirect( uri );
	}

	protected Component createComponents( String uri, Component parent, Map<?, ?> parameters )
	{
		Component c = Executions.getCurrent( ).createComponents( uri, parent, parameters );
		if ( c != null )
			c.setAttribute( "zulPage", uri );
		return c;
	}

	protected void doModal( Window w, IDialogEvent evt )
	{
		if ( w != null ) {
			if ( w instanceof BaseDialogWindow )
				( (BaseDialogWindow) w ).setCallEvent( evt );
			w.doModal( );
		}
	}

	protected void unloadMe( )
	{
		Component parent = null;

		if ( parent == null && getMainWindow( ) != null )
			parent = getMainWindow( ).getParent( );
		if ( parent != null )
			if ( SysUtils.isEmpty( parent.getChildren( ) ) == false )
				parent.getChildren( ).clear( );
	}

	protected void redirect( String uri )
	{
		if ( uri == null )
			uri = "/private/index.zul";
		Executions.getCurrent( ).sendRedirect( uri );
	}

	protected void setLanguage( String setLang ) throws Exception
	{
		// set session wide language to new value
		if ( SysUtils.isEmpty( setLang ) )
			setLang = "pt_BR";
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
				if ( compType.equals( "org.zkoss.zul.Button" ) )
					( (Button) f.get( this ) ).setLabel( compLabel );
				else if ( compType.equals( "org.zkoss.zul.Label" ) )
					( (Label) f.get( this ) ).setValue( compLabel );
				// Other component types need to be implemented if required
			}
		}
	}

	protected void setLabel( Label comp )
	{
		if ( comp != null ) {
			String value = Labels.getLabel( comp.getId( ) );
			if ( SysUtils.isEmpty( value ) == false )
				comp.setValue( value );
		}
	}

	protected void setLabel( LabelElement comp )
	{
		if ( comp != null ) {
			String value = Labels.getLabel( comp.getId( ) );
			if ( SysUtils.isEmpty( value ) == false )
				comp.setLabel( value );
		}
	}

	protected void setLabel( Panel comp )
	{
		if ( comp != null ) {
			String value = Labels.getLabel( comp.getId( ) );
			if ( SysUtils.isEmpty( value ) == false )
				comp.setTitle( value );
		}
	}

	protected void setLabel( XulElement comp )
	{
		if ( comp != null ) {
			Labels.getLabel( comp.getId( ) );
			if ( comp instanceof Label )
				setLabel( (Label) comp );
			else if ( comp instanceof Panel )
				setLabel( (Panel) comp );
			else if ( comp instanceof LabelElement )
				setLabel( (LabelElement) comp );
		}
	}

	protected String getLabel( String labelId )
	{
		return Labels.getLabel( labelId );
	}

	@Override
	public void doAfterCompose( T comp ) throws Exception
	{
		try {
			String[ ] themes = Themes.getThemes( );
			if ( Themes.getCurrentTheme( ).equals( themes[ 2 ] ) == false )
				Themes.setTheme( Executions.getCurrent( ), themes[ 2 ] );
		}
		catch ( Exception e ) {
			e = null;
		}
		super.doAfterCompose( comp );
		if ( SysUtils.isEmpty( this.labels ) == false )
			for ( XulElement item : this.labels )
				setLabel( item );
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
			return ServiceLocator.getInstance( ).getRemoteSession( zClass, ServiceLocator.ejbName[ 0 ] );
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

	/**
	 * @return the requestPath
	 */
	protected String getRequestPath( )
	{
		return requestPath;
	}

	/**
	 * @param requestPath
	 *            the requestPath to set
	 */
	private void setRequestPath( String requestPath )
	{
		this.requestPath = requestPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zkoss.zk.ui.select.SelectorComposer#doBeforeCompose(org.zkoss.zk.
	 * ui.Page, org.zkoss.zk.ui.Component,
	 * org.zkoss.zk.ui.metainfo.ComponentInfo)
	 */
	@Override
	public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
	{
		PageDefinition pd = compInfo.getPageDefinition( );
		logger.info( pd.getRequestPath( ) );
		setRequestPath( pd.getRequestPath( ) );
		return super.doBeforeCompose( page, parent, compInfo );
	}

	protected ListModelList<?> getModel( Listbox c )
	{
		if ( c == null || c.getModel( ) == null )
			return null;
		Object obj = c.getModel( );
		if ( obj instanceof ListModelList )
			return (ListModelList<?>) obj;
		else
			return null;
	}

	/*
	 * 
	 * $.mask.masks : { 'phone' : { mask : '(99) 9999-9999' }, 'phone-us' : {
	 * mask : '(999) 9999-9999' }, 'cpf' : { mask : '999.999.999-99' }, 'cnpj' :
	 * { mask : '99.999.999/9999-99' }, 'date' : { mask : '39/19/9999' }, //uk
	 * date 'date-us' : { mask : '19/39/9999' }, 'cep' : { mask : '99999-999' },
	 * 'time' : { mask : '29:69' }, 'cc' : { mask : '9999 9999 9999 9999' },
	 * //credit card mask 'integer' : { mask : '999.999.999.999', type :
	 * 'reverse' }, 'decimal' : { mask : '99,999.999.999.999', type : 'reverse',
	 * defaultValue: '000' }, 'decimal-us' : { mask : '99.999,999,999,999', type
	 * : 'reverse', defaultValue: '000' }, 'signed-decimal' : { mask :
	 * '99,999.999.999.999', type : 'reverse', defaultValue : '+000' },
	 * 'signed-decimal-us' : { mask : '99,999.999.999.999', type : 'reverse',
	 * defaultValue : '+000' } }
	 * 
	 * $.mask.rules = { 'z': /[a-z]/, 'Z': /[A-Z]/, 'a': /[a-zA-Z]/, '*':
	 * /[0-9a-zA-Z]/, '@': /[0-9a-zA-ZçÇáàãéèíìóòõúùü]/ };
	 * 
	 * 
	 * $.mask.rules = { '0': /[0]/, '1': /[0-1]/, '2': /[0-2]/, '3': /[0-3]/,
	 * '4': /[0-4]/, '5': /[0-5]/, '6': /[0-6]/, '7': /[0-7]/, '8': /[0-8]/,
	 * '9': /[0-9]/ };
	 * 
	 * $.mask.options = { attr: 'alt', // an attr to look for the mask name or
	 * the mask itself mask: null, // the mask to be used on the input type:
	 * 'fixed', // the mask of this mask maxLength: -1, // the maxLength of the
	 * mask defaultValue: '', // the default value for this input textAlign:
	 * true, // to use or not to use textAlign on the input selectCharsOnFocus:
	 * true, //selects characters on focus of the input setSize: false, // sets
	 * the input size based on the length of the mask (work with fixed and
	 * reverse masks only) autoTab: true, // auto focus the next form element
	 * fixedChars: '[(),.:/ -]', // fixed chars to be used on the masks.
	 * onInvalid: function(){}, onValid: function(){}, onOverflow: function(){}
	 * };
	 * 
	 * souce: http://www.meiocodigo.com/projects/meiomask
	 */
	protected void setMask( String id, String mask )
	{
		setMask( id, mask, false );
	}

	protected void setMask( Component c, String mask )
	{
		if ( c == null )
			return;
		setMask( c.getId( ), mask );
	}

	protected void setMask( Component c, String mask, boolean clear )
	{
		if ( c == null )
			return;
		setMask( c.getId( ), mask, clear );
	}

	protected void setMask( String id, String mask, boolean clear )
	{
		if ( clear ) {
			if ( mask != null )
				Clients.evalJavaScript( "jQuery('$" + id + "').setMask('" + mask + "').val('');" );
			else
				Clients.evalJavaScript( "jQuery('$" + id + "').unsetMask().val('');" );
		}
		else if ( mask != null )
			Clients.evalJavaScript( "jQuery('$" + id + "').setMask('" + mask + "');" );
		else
			Clients.evalJavaScript( "jQuery('$" + id + "').unsetMask();" );
	}

	protected PrincipalDTO getPrincipal( )
	{
		Object obj = getSessionParameter( currentPrincipal );

		if ( obj instanceof PrincipalDTO ) {
			PrincipalDTO login = (PrincipalDTO) obj;
			if ( login != null && login.getPersonify( ) != null )
				return login.getPersonify( );
			return login;
		}
		else
			return null;
	}

	protected void subscribeOnReport( )
	{
		EventQueues.lookup( ReportEvent.queueName ).subscribe( new EventListener<Event>( )
		{
			@Override
			public void onEvent( Event evt )
			{
				if ( evt != null && evt instanceof ReportEvent ) {
					evt.stopPropagation( );
					try {
						ReportEvent reportEvent = (ReportEvent) evt;
						onReport( reportEvent.getItem( ) );
					}
					catch ( Exception e ) {
						e.printStackTrace( );
						e = null;
					}
				}
			}
		} );

	}

	protected void onReport( ReportItem item )
	{
		Map<String, Object> params = new HashMap<String, Object>( );
		params.put( JasperReportController.paramName, item );
		gotoPage( "/private/report.zul", null, params, true );
	}

}
