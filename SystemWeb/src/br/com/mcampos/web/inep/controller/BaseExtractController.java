package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;

public abstract class BaseExtractController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = -5369756609865945929L;

	@Wire
	private Grid dataGrid;
	@Wire
	private Combobox comboEvent;

	private InepRevisor revisor;

	protected abstract List<InepAnaliticoCorrecao> getList( InepPackage item );

	protected Grid getDataGrid( )
	{
		return this.dataGrid;
	}

	protected Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( )
	{
		Comboitem item = getComboEvent( ).getSelectedItem( );
		if ( item != null ) {
			loadGrid( );
		}
	}

	private void loadGrid( )
	{
		List<InepAnaliticoCorrecao> list = Collections.emptyList( );
		Comboitem item = getComboEvent( ).getSelectedItem( );
		if ( item != null ) {
			list = getList( (InepPackage) item.getValue( ) );
		}
		getComboEvent( ).setModel( new ListModelList<InepAnaliticoCorrecao>( list ) );
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			this.revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
					getCurrentCollaborator( ) );
		}
		return this.revisor;
	}

	private void loadCombobox( )
	{
		List<InepPackage> events = getSession( ).getEvents( getCurrentCollaborator( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepPackage e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectPackage( );
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		loadCombobox( );
	}

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

}