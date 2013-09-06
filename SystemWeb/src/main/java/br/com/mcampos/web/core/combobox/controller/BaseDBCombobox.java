package br.com.mcampos.web.core.combobox.controller;

import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import br.com.mcampos.ejb.core.ReadOnlySessionInterface;
import br.com.mcampos.ejb.core.SimpleEntity;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.core.combobox.BasicComboitemRenderer;
import br.com.mcampos.web.locator.ServiceLocator;

public abstract class BaseDBCombobox<BEAN> extends BaseController<Combobox>
{
	private static final long serialVersionUID = 1161098428937124830L;
	private Class<BEAN> persistentClass;
	private transient BEAN session = null;

	protected abstract Class<BEAN> getSessionClass( );

	@Wire( "combobox" )
	Combobox combobox;

	public BaseDBCombobox( )
	{
		super( );
		setPersistentClass( );
	}

	@SuppressWarnings( "unchecked" )
	protected BEAN getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (BEAN) ServiceLocator.getInstance( ).getRemoteSession( this.persistentClass );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	protected void setPersistentClass( )
	{
		this.persistentClass = getSessionClass( );
	}

	protected void loadCombobox( )
	{
		if ( getCombobox( ) == null ) {
			return;
		}
		getCombobox( ).getChildren( ).clear( );
		List<?> list = getList( );
		if ( SysUtils.isEmpty( list ) ) {
			return;
		}
		for ( Object dto : list ) {
			Comboitem item;

			if ( dto instanceof SimpleEntity<?> ) {
				item = getCombobox( ).appendItem( ( (SimpleEntity<?>) dto ).getDescription( ) );
			}
			else {
				item = getCombobox( ).appendItem( dto.toString( ) );
			}
			if ( item != null ) {
				item.setValue( dto );
			}
		}
		setDefaultSelected( 0 );
	}

	protected List<?> getList( )
	{
		ReadOnlySessionInterface<?> s = (ReadOnlySessionInterface<?>) getSession( );
		if ( s != null ) {
			return (List<?>) s.getAll( getPrincipal( ) );
		}
		else {
			return Collections.emptyList( );
		}
	}

	public Combobox getCombobox( )
	{
		return this.combobox;
	}

	@Override
	public void doAfterCompose( Combobox comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getCombobox( ).setItemRenderer( getRenderer( ) );
		loadCombobox( );
	}

	protected ComboitemRenderer<?> getRenderer( )
	{
		return new BasicComboitemRenderer( );
	}

	protected void setDefaultSelected( int nIndex )
	{
		if ( getCombobox( ) != null && getCombobox( ).getChildren( ).size( ) > 0 ) {
			getCombobox( ).setSelectedIndex( nIndex );
		}
	}

}
