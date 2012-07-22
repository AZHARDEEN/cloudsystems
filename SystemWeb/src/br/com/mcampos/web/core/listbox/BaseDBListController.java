package br.com.mcampos.web.core.listbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.ext.Paginal;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.core.PagingSessionInterface;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseCrudController;

public abstract class BaseDBListController<BEAN, ENTITY> extends BaseCrudController<BEAN, ENTITY>
{
	private static final long serialVersionUID = 7099297274300371931L;

	@Wire( "listbox#listTable" )
	Listbox listbox;

	@Wire( value = "paging" )
	private List<Paginal> pagings;

	protected abstract void showFields( ENTITY targetEntity );

	protected ListitemRenderer<?> getListRenderer( )
	{
		return new BasicListRenderer( );
	}

	public BaseDBListController( )
	{
		super( );
	}

	protected Listbox getListbox( )
	{
		return this.listbox;
	}

	protected List<Paginal> getPaging( )
	{
		if ( this.pagings == null && getListbox( ).getPaginal( ) != null ) {
			this.pagings = new ArrayList<Paginal>( );
			this.pagings.add( getListbox( ).getPaginal( ) );
		}
		return this.pagings;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setupComparator( );
		getListbox( ).setItemRenderer( getListRenderer( ) );
		initPaging( 0, getRows( ) );
		loadPage( 0 );
	}

	protected int getRows( )
	{
		int rows = getListbox( ).getRows( );
		if ( rows == 0 ) {
			rows = ListboxParams.maxListBoxPageSize;
			getListbox( ).setRows( rows );
		}
		return rows;
	}

	protected void initPaging( int page, int rows )
	{
		int totalSize;

		if ( SysUtils.isEmpty( getPaging( ) ) == false && getPagingSession( ) != null ) {
			totalSize = getPagingSession( ).count( );
			if ( totalSize >= rows ) {
				setPagingEventListener( );
				for ( Paginal item : getPaging( ) ) {
					item.setTotalSize( totalSize );
					item.setActivePage( page );
					item.setPageSize( rows );
				}
			}
			else {
				for ( Paginal item : getPaging( ) ) {
					if ( item instanceof Paging ) {
						( (Paging) item ).setVisible( false );
					}
				}
				getListbox( ).setModel( new ListModelList<ENTITY>( getList( ) ) );
			}
		}
	}

	private void setupComparator( )
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
	protected List<ENTITY> getSelectedRecords( )
	{
		Set<Listitem> itens;

		itens = getListbox( ).getSelectedItems( );
		ArrayList<ENTITY> entities = null;
		if ( itens != null && itens.size( ) > 0 ) {
			entities = new ArrayList<ENTITY>( itens.size( ) );
			for ( Listitem listitem : itens ) {
				entities.add( (ENTITY) listitem.getValue( ) );
			}
		}
		return entities;
	}

	@Listen( "onSelect = listbox#listTable" )
	public void onSelect( )
	{
		List<ENTITY> itens = getSelectedRecords( );
		if ( itens != null ) {
			if ( itens.size( ) == 1 ) {
				allowUpdateAndDelete( true );
				showFields( itens.get( 0 ) );
			}
			else {
				allowDelete( true );
				allowUpdate( false );
			}
		}
		else {
			showFields( null );
			allowUpdateAndDelete( false );
		}
	}

	@Override
	public void onRefresh( )
	{
		getListbox( ).setModel( new ListModelList<ENTITY>( getList( ) ) );
		allowUpdateAndDelete( false );
	}

	@Override
	protected void onAddNew( )
	{
		showFields( null );
		setTargetEntity( getNew( ) );
	}

	@Override
	protected void onUpdate( )
	{
		List<ENTITY> items = getSelectedRecords( );
		if ( items != null ) {
			setTargetEntity( ( (ArrayList<ENTITY>) getSelectedRecords( ) ).get( 0 ) );
			showFields( items.get( 0 ) );
			disableListBox( true );
		}
	}

	private void disableListBox( boolean bDisable )
	{
		List<Listitem> list = getListbox( ).getItems( );
		for ( Listitem item : list ) {
			item.setDisabled( bDisable );
		}
		getListbox( ).setDisabled( bDisable );
	}

	@Override
	protected void onCancel( )
	{
		disableListBox( false );
		List<ENTITY> items = getSelectedRecords( );
		if ( items != null ) {
			showFields( items.get( 0 ) );
		}
		else {
			showFields( null );
		}
	}

	@Override
	protected Collection<ENTITY> getEntitiesToDelete( )
	{
		return getSelectedRecords( );
	}

	@SuppressWarnings( "unchecked" )
	private ListModelList<ENTITY> getModel( )
	{
		ListModelList<ENTITY> model = (ListModelList<ENTITY>) getListbox( ).getModel( );
		return model;
	}

	@Override
	protected void afterUpdate( ENTITY entity, int operation )
	{
		ListModelList<ENTITY> model = getModel( );
		int nIndex = model.indexOf( entity );
		if ( nIndex < 0 ) {
			model.add( entity );
			nIndex = model.indexOf( entity );
		}
		else {
			model.set( nIndex, entity );
		}
		Listitem item = getListbox( ).getItemAtIndex( nIndex );
		item.setValue( entity );
		getListbox( ).setSelectedIndex( nIndex );

		onSelect( );
	}

	@Override
	protected void afterDelete( Collection<ENTITY> collection )
	{
		ListModelList<ENTITY> model = getModel( );
		model.removeAll( collection );
		showFields( null );
	}

	protected Collection<ENTITY> getList( )
	{
		@SuppressWarnings( "unchecked" )
		BaseSessionInterface<ENTITY> session = (BaseSessionInterface<ENTITY>) getSession( );
		return session.getAll( );
	}

	protected ENTITY getNew( )
	{
		@SuppressWarnings( "unchecked" )
		BaseSessionInterface<ENTITY> session = (BaseSessionInterface<ENTITY>) getSession( );
		Class<ENTITY> cls = session.getPersistentClass( );
		try {
			ENTITY newEntity = cls.newInstance( );
			return newEntity;
		}
		catch ( Exception e ) {
			e.printStackTrace( );
			return null;
		}
	}

	protected void setPagingEventListener( )
	{
		for ( Paginal item : getPaging( ) ) {
			item.addEventListener( "onPaging", getEventListener( ) );
		}

	}

	private void setActivePage( int activePage )
	{
		for ( Paginal item : getPaging( ) ) {
			item.setActivePage( activePage );
		}
	}

	protected void onPaging( PagingEvent event )
	{
		loadPage( event.getActivePage( ) );
		setActivePage( event.getActivePage( ) );
	}

	protected void loadPage( int activePage )
	{
		getListbox( ).setModel(
				new ListModelList<ENTITY>( getAll( activePage ) ) );
	}

	protected Collection<ENTITY> getAll( int activePage )
	{
		return getPagingSession( ).getAll( null, new DBPaging( activePage, getRows( ) ) );
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

	@SuppressWarnings( "unchecked" )
	protected PagingSessionInterface<ENTITY> getPagingSession( )
	{
		Object obj = super.getSession( );

		return (PagingSessionInterface<ENTITY>) obj;
	}

}
