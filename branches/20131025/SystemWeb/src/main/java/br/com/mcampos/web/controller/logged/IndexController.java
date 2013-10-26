package br.com.mcampos.web.controller.logged;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.omg.CORBA.portable.ApplicationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.security.menu.MenuFacade;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.event.IClickEvent;
import br.com.mcampos.web.core.mdi.BaseLoggedMDIController;
import br.com.mcampos.web.core.report.JasperReportController;
import br.com.mcampos.web.core.report.ReportItem;

public class IndexController extends BaseLoggedMDIController implements IClickEvent
{
	private static final long serialVersionUID = 4709477127092943298L;
	public static final String queueName = "changeCompanyEventQueue";

	private transient MenuFacade session = null;

	@Wire( "#mainMenu" )
	private Menubar mainMenu;

	private DynamicMenu dynamicMenu = null;

	@Override
	public void onClick( MouseEvent evt )
	{
		Menuitem menuItem;

		if ( evt.getTarget( ) instanceof Menuitem ) {
			menuItem = (Menuitem) evt.getTarget( );
			Object obj;

			obj = menuItem.getAttribute( MenuController.attrMenu );
			if ( obj != null ) {
				/*
				 * TODO: add menu routine
				 */
			}
			else if ( SysUtils.isEmpty( menuItem.getValue( ) ) == false ) {
				this.loadPage( menuItem.getValue( ), true );
			}
		}
	}

	public void onNotify( CompanyEventChange evt ) throws ApplicationException
	{
		if ( this.getMdiApplication( ) != null && this.getMdiApplication( ).getChildren( ) != null ) {
			this.clear( );
		}
		if ( this.mainMenu == null ) {
			return;
		}
		if ( this.mainMenu.getChildren( ) != null ) {
			this.mainMenu.getChildren( ).clear( );
		}
		List<br.com.mcampos.jpa.security.Menu> menus = this.getSession( ).getMenus( this.getPrincipal( ) );
		for ( br.com.mcampos.jpa.security.Menu item : menus ) {
			this.getDynamicMenu( ).getParentComponent( item );
		}
		String page = this.verifyLoginStatus( null );
		if ( !SysUtils.isEmpty( page ) ) {
			this.loadPage( page, true );
		}
	}

	private void clear( )
	{
		try {
			/*
			 * initPage = getUserLocator().getInitialPage( getLoggedInUser() );
			 * if ( !SysUtils.isEmpty( initPage ) ) { gotoPage( initPage,
			 * mdiApplication ); } else
			 */
			this.getMdiApplication( ).getChildren( ).clear( );
		}
		catch ( Exception e ) {
			this.showErrorMessage( e.getMessage( ), "Erro" );
		}
	}

	private MenuFacade getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (MenuFacade) ServiceLocator.getInstance( ).getRemoteSession( MenuFacade.class, null );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		EventQueues.lookup( queueName ).subscribe( new EventListener<Event>( )
		{
			@Override
			public void onEvent( Event evt )
			{
				if ( evt instanceof CompanyEventChange ) {
					try {
						IndexController.this.onNotify( (CompanyEventChange) evt );
					}
					catch ( ApplicationException e ) {
						e.printStackTrace( );
						e = null;
					}
				}
			}
		} );
		this.subscribeOnReport( );
	}

	protected DynamicMenu getDynamicMenu( )
	{
		if ( this.dynamicMenu == null ) {
			this.dynamicMenu = new DynamicMenu( this.mainMenu, this );
		}
		return this.dynamicMenu;
	}

	@Override
	protected Component createComponents( String uri, Component parent, Map<?, ?> parameters )
	{
		return super.createComponents( uri, parent, parameters );
	}

	@Override
	protected void onReport( ReportItem item )
	{
		Map<String, Object> params = new HashMap<String, Object>( );
		params.put( JasperReportController.paramName, item );
		this.gotoPage( "/private/report.zul", this.getMdiApplication( ), params, true );
	}

}
