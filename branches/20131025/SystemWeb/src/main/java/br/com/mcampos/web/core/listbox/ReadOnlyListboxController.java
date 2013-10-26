package br.com.mcampos.web.core.listbox;

import java.util.Collection;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.core.PagingSessionInterface;
import br.com.mcampos.jpa.security.AccessLog;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBController;

public abstract class ReadOnlyListboxController<SESSION extends BaseSessionInterface, ENTITY> extends BaseDBController<SESSION>
{
	private static final long serialVersionUID = -5982064141322569509L;

	@Wire( "listbox#listTable" )
	private Listbox listbox;

	@Wire( value = "paging" )
	private List<Paging> pagings;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		initPaging( 0, ListboxParams.maxListBoxPageSize );
		setupComparator( );
		getListbox( ).setItemRenderer( getListRenderer( ) );
		loadPage( 0 );
	}

	protected Listbox getListbox( )
	{
		return this.listbox;
	}

	protected List<Paging> getPaging( )
	{
		return this.pagings;
	}

	protected ListitemRenderer<?> getListRenderer( )
	{
		return new BasicListRenderer( );
	}

	protected void setupComparator( )
	{
		List<Component> headers = getListbox( ).getListhead( ).getChildren( );
		if ( headers != null ) {
			for ( int nIndex = 0; nIndex < headers.size( ); nIndex++ ) {
				Listheader header = (Listheader) headers.get( nIndex );
				header.setSortAscending( new BaseComparator( nIndex, true ) );
				header.setSortDescending( new BaseComparator( nIndex, false ) );
			}
		}
	}

	@SuppressWarnings( "unchecked" )
	protected void initPaging( int page, int rows )
	{
		int totalSize;

		if ( SysUtils.isEmpty( getPaging( ) ) == false ) {
			totalSize = getPagingSession( ).count( getPrincipal( ) );
			if ( totalSize >= rows ) {
				setPagingEventListener( );
				for ( Paging item : getPaging( ) ) {
					item.setTotalSize( totalSize );
					item.setActivePage( page );
					item.setPageSize( rows );
				}
			}
			else {
				for ( Paging item : getPaging( ) ) {
					item.setVisible( false );
				}
				getListbox( ).setModel( new ListModelList<AccessLog>( getPagingSession( ).getAll( getPrincipal( ) ) ) );
			}
		}
	}

	protected void setPagingEventListener( )
	{
		for ( Paging item : getPaging( ) ) {
			item.addEventListener( "onPaging", getEventListener( ) );
		}

	}

	protected void setActivePage( int activePage )
	{
		for ( Paging item : getPaging( ) ) {
			item.setActivePage( activePage );
		}
	}

	protected int getActivePage( )
	{
		return getPaging( ).get( 0 ).getActivePage( );
	}

	protected void onPaging( PagingEvent event )
	{
		loadPage( event.getActivePage( ) );
		setActivePage( event.getActivePage( ) );
	}

	@SuppressWarnings( "unchecked" )
	private void loadPage( int activePage )
	{
		getListbox( ).setModel(
				new ListModelList<AccessLog>( getPagingSession( ).getAll( getPrincipal( ), null,
						new DBPaging( activePage, ListboxParams.maxListBoxPageSize ) ) ) );
	}

	private EventListener<PagingEvent> getEventListener( )
	{
		return new EventListener<PagingEvent>( )
		{
			@Override
			public void onEvent( PagingEvent event )
			{
				onPaging( event );
			}
		};
	}

	@SuppressWarnings( "rawtypes" )
	protected PagingSessionInterface getPagingSession( )
	{
		return (PagingSessionInterface) super.getSession( );
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	protected Collection<ENTITY> getList( )
	{
		PagingSessionInterface session = getPagingSession( );
		return session.getAll( getPrincipal( ), null, new DBPaging( getActivePage( ), ListboxParams.maxListBoxPageSize ) );
	}

}
