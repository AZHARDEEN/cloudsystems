package br.com.mcampos.web.controller.logged;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.event.IClickEvent;

public final class DynamicMenu implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6069397668281548120L;

	protected static final String attrMenu = "dto";

	private IClickEvent clickEvent;

	private Menubar mainMenu;

	public DynamicMenu( Menubar mainMenu, IClickEvent evt )
	{
		this.setMainMenu( mainMenu );
		this.setClickEvent( evt );
	}

	public Component createMenu( br.com.mcampos.jpa.security.Menu item )
	{
		Component ret;

		if ( SysUtils.isEmpty( item.getUrl( ) ) == false ) {
			Menuitem menuItem;

			menuItem = new Menuitem( item.getDescription( ) );
			menuItem.setAutocheck( item.getAutocheck( ) );
			menuItem.setChecked( item.getChecked( ) );
			menuItem.setCheckmark( item.getCheckmark( ) );
			menuItem.setDisabled( item.getDisabled( ) );
			menuItem.setAttribute( attrMenu, item.getUrl( ) );
			menuItem.setValue( item.getUrl( ) );
			menuItem.setImage( item.getImagePath( ) );
			this.createListener( menuItem );
			ret = menuItem;
		}
		else {
			Menu menuitem = new Menu( item.getDescription( ) );
			menuitem.appendChild( new Menupopup( ) );
			ret = menuitem;
		}
		ret.setAttribute( "index", item.getSequence( ) );
		ret.setAttribute( "id", item.getId( ) );
		return ret;

	}

	private void createListener( Menuitem item )
	{
		if ( this.getClickEvent( ) != null )
		{
			item.addEventListener( Events.ON_CLICK, new EventListener<Event>( )
			{
				@Override
				public void onEvent( Event event ) throws Exception
				{
					DynamicMenu.this.getClickEvent( ).onClick( (MouseEvent) event );
				}
			} );
		}
	}

	protected IClickEvent getClickEvent( )
	{
		return this.clickEvent;
	}

	protected void setClickEvent( IClickEvent clickEvent )
	{
		this.clickEvent = clickEvent;
	}

	protected Menubar getMainMenu( )
	{
		return this.mainMenu;
	}

	protected void setMainMenu( Menubar mainMenu )
	{
		this.mainMenu = mainMenu;
	}

	public Component getParentComponent( br.com.mcampos.jpa.security.Menu item )
	{
		Component base = null;
		Component target = null;

		/*
		 * Recursivamente vai ate o menu pai
		 */
		if ( item.getParent( ) != null ) {
			base = this.getParentComponent( item.getParent( ) );
			if ( base instanceof Menu ) {
				base = base.getChildren( ).get( 0 );
			}
		}
		if ( base == null ) {
			base = this.getMainMenu( );
		}

		/*
		 * Procura por um menu ja criado com o mesmo id do item
		 */
		for ( Component c : base.getChildren( ) )
		{
			Integer id = (Integer) c.getAttribute( "id" );
			if ( id != null && id.equals( item.getId( ) ) ) {
				target = c;
				break;
			}
		}

		/*
		 * Nao achou entao cria no lugar correto
		 */
		if ( target == null )
		{
			for ( int nIndex = 0; nIndex < base.getChildren( ).size( ); nIndex++ )
			{
				Component current = base.getChildren( ).get( nIndex );
				Integer sequence = (Integer) current.getAttribute( "index" );
				if ( sequence != null && sequence.compareTo( item.getSequence( ) ) > 0 ) {
					target = this.createMenu( item );
					if ( item.getSeparatorBefore( ) ) {
						base.getChildren( ).add( ( new Menuseparator( ) ) );
					}
					base.getChildren( ).add( nIndex, target );
					break;
				}
			}
			if ( target == null ) {
				target = this.createMenu( item );
				if ( ( base instanceof Menubar && target instanceof Menu )
						|| ( base instanceof Menupopup && ( target instanceof Menu || target instanceof Menuitem ) ) ) {
					if ( item.getSeparatorBefore( ) ) {
						base.getChildren( ).add( ( new Menuseparator( ) ) );
					}
					base.getChildren( ).add( target );
				}
			}
		}
		return target;
	}
}
