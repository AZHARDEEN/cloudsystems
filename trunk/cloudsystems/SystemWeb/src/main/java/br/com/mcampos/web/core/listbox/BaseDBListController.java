package br.com.mcampos.web.core.listbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
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

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.core.PagingSessionInterface;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseCrudController;

public abstract class BaseDBListController<BEAN extends BaseCrudSessionInterface<?>, ENTITY> extends BaseCrudController<BEAN, ENTITY>
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
		if ( this.pagings == null && this.getListbox( ).getPaginal( ) != null ) {
			this.pagings = new ArrayList<Paginal>( );
			this.pagings.add( this.getListbox( ).getPaginal( ) );
		}
		return this.pagings;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.setupComparator( );
		this.getListbox( ).setItemRenderer( this.getListRenderer( ) );
		this.initPaging( 0, this.getRows( ) );
		this.loadPage( 0 );
	}

	protected int getRows( )
	{
		int rows = this.getListbox( ).getRows( );
		if ( rows == 0 ) {
			rows = ListboxParams.maxListBoxPageSize;
			this.getListbox( ).setRows( rows );
		}
		return rows;
	}

	protected int getCount( )
	{
		return this.getPagingSession( ).count( this.getPrincipal( ) );
	}

	protected void initPaging( int page, int rows )
	{
		int totalSize;

		if ( SysUtils.isEmpty( this.getPaging( ) ) == false && this.getPagingSession( ) != null ) {
			totalSize = this.getCount( );
			if ( totalSize >= rows ) {
				this.setPagingEventListener( );
				for ( Paginal item : this.getPaging( ) ) {
					item.setTotalSize( totalSize );
					item.setActivePage( page );
					item.setPageSize( rows );
				}
			}
			else {
				for ( Paginal item : this.getPaging( ) ) {
					if ( item instanceof Paging ) {
						( (Paging) item ).setVisible( false );
					}
				}
				this.getListbox( ).setModel( new ListModelList<ENTITY>( this.getList( ) ) );
			}
		}
	}

	private void setupComparator( )
	{
		List<Component> headers = this.getListbox( ).getListhead( ).getChildren( );
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

		itens = this.getListbox( ).getSelectedItems( );
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
	public void onSelectListbox( Event evt ) throws Exception
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		try {
			this.onSelect( );
		}
		catch ( Exception e ) {
			this.getSession( ).storeException( e );
			throw e;
		}
	}

	private void onSelect( )
	{
		List<ENTITY> itens = this.getSelectedRecords( );
		if ( itens != null ) {
			if ( itens.size( ) == 1 ) {
				this.allowUpdateAndDelete( true );
				this.showFields( itens.get( 0 ) );
			}
			else {
				this.allowDelete( true );
				this.allowUpdate( false );
			}
		}
		else {
			this.showFields( null );
			this.allowUpdateAndDelete( false );
		}
	}

	@Override
	public void onRefresh( )
	{
		this.getListbox( ).setModel( new ListModelList<ENTITY>( this.getList( ) ) );
		this.allowUpdateAndDelete( false );
	}

	@Override
	protected void onAddNew( )
	{
		this.showFields( null );
		this.setTargetEntity( this.getNew( ) );
	}

	@Override
	protected void onUpdate( )
	{
		List<ENTITY> items = this.getSelectedRecords( );
		if ( items != null ) {
			this.setTargetEntity( ( (ArrayList<ENTITY>) this.getSelectedRecords( ) ).get( 0 ) );
			this.showFields( items.get( 0 ) );
			this.disableListBox( true );
		}
	}

	private void disableListBox( boolean bDisable )
	{
		List<Listitem> list = this.getListbox( ).getItems( );
		for ( Listitem item : list ) {
			item.setDisabled( bDisable );
		}
		this.getListbox( ).setDisabled( bDisable );
	}

	@Override
	protected void onCancel( )
	{
		this.disableListBox( false );
		List<ENTITY> items = this.getSelectedRecords( );
		if ( items != null ) {
			this.showFields( items.get( 0 ) );
		}
		else {
			this.showFields( null );
		}
	}

	@Override
	protected Collection<ENTITY> getEntitiesToDelete( )
	{
		return this.getSelectedRecords( );
	}

	@SuppressWarnings( "unchecked" )
	private ListModelList<ENTITY> getModel( )
	{
		ListModelList<ENTITY> model = (ListModelList<ENTITY>) this.getListbox( ).getModel( );
		return model;
	}

	@Override
	protected void afterUpdate( ENTITY entity, int operation )
	{
		ListModelList<ENTITY> model = this.getModel( );
		int nIndex = model.indexOf( entity );
		if ( nIndex < 0 ) {
			model.add( entity );
			nIndex = model.indexOf( entity );
		}
		else {
			model.set( nIndex, entity );
		}
		Listitem item = this.getListbox( ).getItemAtIndex( nIndex );
		item.setValue( entity );
		this.getListbox( ).setSelectedIndex( nIndex );

		this.onSelect( );
	}

	@Override
	protected void afterDelete( Collection<ENTITY> collection )
	{
		ListModelList<ENTITY> model = this.getModel( );
		model.removeAll( collection );
		if ( model.getSize( ) > 0 ) {
			this.getListbox( ).setSelectedIndex( 0 );
		}
		this.onSelect( );
	}

	protected Collection<ENTITY> getList( )
	{

		@SuppressWarnings( "unchecked" )
		BaseCrudSessionInterface<ENTITY> session = (BaseCrudSessionInterface<ENTITY>) this.getSession( );
		try {
			return session.getAll( this.getPrincipal( ) );
		}
		catch ( Exception e ) {
			session.storeException( e );
			return Collections.emptyList( );
		}
	}

	protected ENTITY getNew( )
	{
		@SuppressWarnings( "unchecked" )
		BaseCrudSessionInterface<ENTITY> session = (BaseCrudSessionInterface<ENTITY>) this.getSession( );
		Class<ENTITY> cls = session.getPersistentClass( );
		try {
			ENTITY newEntity = cls.newInstance( );
			return newEntity;
		}
		catch ( Exception e ) {
			session.storeException( e );
			return null;
		}
	}

	protected void setPagingEventListener( )
	{
		for ( Paginal item : this.getPaging( ) ) {
			item.addEventListener( "onPaging", this.getEventListener( ) );
		}

	}

	private void setActivePage( int activePage )
	{
		for ( Paginal item : this.getPaging( ) ) {
			item.setActivePage( activePage );
		}
	}

	protected void onPaging( PagingEvent event )
	{
		this.loadPage( event.getActivePage( ) );
		this.setActivePage( event.getActivePage( ) );
	}

	protected void loadPage( int activePage )
	{
		this.getListbox( ).setModel( new ListModelList<ENTITY>( this.getAll( activePage ) ) );
	}

	protected Collection<ENTITY> getAll( int activePage )
	{
		return this.getPagingSession( ).getAll( this.getPrincipal( ), null, new DBPaging( activePage, this.getRows( ) ) );
	}

	private EventListener<PagingEvent> getEventListener( )
	{
		return new EventListener<PagingEvent>( )
		{
			@Override
			public void onEvent( PagingEvent event )
			{
				BaseDBListController.this.onPaging( event );
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
