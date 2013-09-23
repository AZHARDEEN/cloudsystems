package br.com.mcampos.web.core;

import java.util.Collection;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseCrudController<BEAN extends BaseCrudSessionInterface<?>, ENTITY> extends BaseDBLoggedController<BEAN>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7715533767851637478L;
	public static final Integer statusNone = 0;
	public static final Integer statusAdd = 1;
	public static final Integer statusUpdate = 2;
	public static final Integer statusDelete = 3;

	private Integer status;

	@Wire( "#cmdUpdate" )
	private Button updateButton;

	@Wire( "#cmdCreate" )
	private Button createButton;

	@Wire( "#cmdDelete" )
	private Button deleteButton;

	@Wire( "#cmdSave" )
	private Button cmdSave;

	@Wire( "#divInfo" )
	private Div divInfo;

	@Wire( "#divEdit" )
	private Div divEdit;

	private transient ENTITY targetEntity = null;

	protected abstract void onRefresh( );

	protected abstract void onAddNew( );

	protected abstract void onUpdate( );

	protected abstract void onCancel( );

	protected abstract void updateTargetEntity( ENTITY entity );

	protected abstract void afterDelete( Collection<ENTITY> collection );

	protected abstract Collection<ENTITY> getEntitiesToDelete( );

	protected abstract boolean validateEntity( ENTITY entity, int operation );

	public BaseCrudController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setStatus( statusNone );
		if ( this.updateButton != null ) {
			this.updateButton.setDisabled( true );
		}
		if ( this.deleteButton != null ) {
			this.deleteButton.setDisabled( true );
		}
	}

	private void setStatus( Integer status )
	{
		this.status = status;
	}

	protected Integer getStatus( )
	{
		return this.status;
	}

	@Listen( "onClick = #cmdRefresh" )
	public void onClickRefresh( )
	{
		onRefresh( );
		setTargetEntity( null );
	}

	@Listen( "onClick = #cmdDelete" )
	public void onClickDelete( )
	{
		Collection<ENTITY> toDelete = getEntitiesToDelete( );
		if ( SysUtils.isEmpty( toDelete ) ) {
			return;
		}
		String deleteMsg;
		String deleteTitle;

		if ( toDelete.size( ) == 1 ) {
			deleteMsg = "Confirma a exclusão do registro selecionado";
			deleteTitle = "Exclusão";
		}
		else {
			deleteMsg = "Confirma a exclusão dos registros selecionados";
			deleteTitle = "Exclusão de Múltiplos Registros";
		}

		Messagebox
				.show( deleteMsg, deleteTitle, Messagebox.YES + Messagebox.NO, Messagebox.QUESTION, getMessageboxEventListener( ) );
	}

	@SuppressWarnings( "unchecked" )
	private void delete( )
	{
		Collection<ENTITY> toDelete = getEntitiesToDelete( );
		BaseCrudSessionInterface<ENTITY> bean = (BaseCrudSessionInterface<ENTITY>) getSession( );
		try {
			bean.remove( getPrincipal( ), toDelete );
			afterDelete( toDelete );
			setTargetEntity( null );
		}
		catch ( Exception e ) {
			bean.storeException( e );
			Messagebox.show( "Erro ao excluir o(s) registro(s) desejado(s): " + e.getMessage( ), "Erro ao Excluir", Messagebox.OK, Messagebox.ERROR );
		}

	}

	private EventListener<Event> getMessageboxEventListener( )
	{
		return new EventListener<Event>( )
		{
			@Override
			public void onEvent( Event event )
			{
				if ( Messagebox.ON_YES.equals( event.getName( ) ) ) {
					delete( );
				}
			}
		};

	}

	@Listen( "onClick = #cmdCreate" )
	public void onClickCreate( Event evt )
	{
		try {
			setStatus( statusAdd );
			onAddNew( );
			changeButtons( false );
		}
		catch ( Exception e ) {
			getSession( ).storeException( e );
			throw e;
		}
	}

	@Listen( "onClick = #cmdUpdate" )
	public void onClickUpdate( Event evt )
	{
		setStatus( statusUpdate );
		onUpdate( );
		changeButtons( false );
	}

	@Listen( "onClick = #cmdSave, #cmdCancel" )
	public void onClickConfirmCancel( Event evt )
	{
		if ( evt.getTarget( ).getId( ).equals( this.cmdSave.getId( ) ) ) {
			onSave( );
		}
		onCancel( );
		changeButtons( true );
		setStatus( statusNone );
	}

	private void changeButtons( boolean enableOperations )
	{
		if ( this.divInfo != null ) {
			this.divInfo.setVisible( enableOperations );
		}
		if ( this.divEdit != null ) {
			this.divEdit.setVisible( !enableOperations );
		}
	}

	protected void allowUpdateAndDelete( boolean bAllow )
	{
		allowUpdate( bAllow );
		allowDelete( bAllow );
	}

	protected void allowUpdate( boolean bAllow )
	{
		if ( this.updateButton != null ) {
			this.updateButton.setDisabled( !bAllow );
		}
	}

	protected void allowDelete( boolean bAllow )
	{
		if ( this.deleteButton != null ) {
			this.deleteButton.setDisabled( !bAllow );
		}
	}

	protected void onSave( )
	{
		@SuppressWarnings( "unchecked" )
		BaseCrudSessionInterface<ENTITY> bean = (BaseCrudSessionInterface<ENTITY>) getSession( );
		try {
			updateTargetEntity( getTargetEntity( ) );
			if ( validateEntity( getTargetEntity( ), getStatus( ) ) ) {
				if ( getStatus( ).equals( statusAdd ) ) {
					setTargetEntity( bean.add( getPrincipal( ), getTargetEntity( ) ) );
				}
				else {
					setTargetEntity( bean.update( getPrincipal( ), getTargetEntity( ) ) );
				}
				afterUpdate( getTargetEntity( ), getStatus( ) );
			}
		}
		catch ( Exception e )
		{
			bean.storeException( e );
			Messagebox.show( "Erro ao salvar o(s) registro(s) desejado(s): " + e.getMessage( ) + "\n" + getTargetEntity( ).getClass( ).getSimpleName( ),
					"Erro ao Excluir", Messagebox.OK, Messagebox.ERROR );
		}
	}

	protected void afterUpdate( ENTITY entity, int operation )
	{
		/*
		 * filler
		 */
	}

	protected void setTargetEntity( ENTITY targetEntity )
	{
		this.targetEntity = targetEntity;
	}

	protected ENTITY getTargetEntity( )
	{
		return this.targetEntity;
	}

	protected Button getUpdateButton( )
	{
		return this.updateButton;
	}

	protected Button getCreateButton( )
	{
		return this.createButton;
	}

	protected Button getDeleteButton( )
	{
		return this.deleteButton;
	}
}
