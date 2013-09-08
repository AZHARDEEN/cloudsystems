package br.com.mcampos.web.inep.controller.report;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.reporting.BaseSubscriptionDTO;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;

public class InterfacesController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = 984742122241722367L;

	@Wire
	private Listbox reportList;

	@Wire
	private Combobox comboEvent;

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

	@Listen( "onClick = #btnExport " )
	public void onExport( Event evt )
	{
		Listitem item = this.reportList.getSelectedItem( );
		if ( item == null ) {
			showErrorMessage( "Selecione um item da lista para exportar", "Exportar Arquivos de Interface" );
			return;
		}
		Comboitem itemEvent = getComboEvent( ).getSelectedItem( );
		Integer report = Integer.parseInt( (String) item.getValue( ) );
		List<BaseSubscriptionDTO> list = getSession( ).report( (InepEvent) itemEvent.getValue( ), report );
		exportReport( list );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void exportReport( List<BaseSubscriptionDTO> list )
	{
		if ( SysUtils.isEmpty( list ) ) {
			return;
		}
		StringBuffer buffer = new StringBuffer( list.get( 0 ).getHeader( ) );
		buffer.append( "\n" );
		for ( BaseSubscriptionDTO item : list ) {
			String[ ] fields = item.getFields( );
			boolean first = true;
			for ( String field : fields )
			{
				if ( !first ) {
					buffer.append( ";" );
				}
				buffer.append( field );
				first = false;
			}
			buffer.append( "\n" );
		}
		Integer report = Integer.parseInt( (String) this.reportList.getSelectedItem( ).getValue( ) );
		Filedownload.save( buffer.toString( ), "text/plain", "report_" + report.toString( ) + ".txt" );
	}

	private Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		loadCombobox( );
	}

	private void loadCombobox( )
	{
		List<InepEvent> events = getSession( ).getEvents( getPrincipal( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
		}
	}

}
