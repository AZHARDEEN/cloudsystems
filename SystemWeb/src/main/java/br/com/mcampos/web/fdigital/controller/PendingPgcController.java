package br.com.mcampos.web.fdigital.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.FieldFilterDTO;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.fdigital.pgc.PgcSession;
import br.com.mcampos.entity.fdigital.Pgc;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.filter.FilterDialog;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class PendingPgcController extends BaseDBListController<PgcSession, Pgc>
{
	private static final long serialVersionUID = -1764902234835340562L;

	@Wire
	private Textbox filterPen;

	@Override
	protected Class<PgcSession> getSessionClass( )
	{
		return PgcSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
	}

	@Override
	protected void showFields( Pgc targetEntity )
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateTargetEntity( Pgc entity )
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean validateEntity( Pgc entity, int operation )
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Collection<Pgc> getAll( int activePage )
	{
		return getPagingSession( ).getAll( getPrincipal( ), getFilter( ), new DBPaging( activePage, getRows( ) ) );
	}

	@Override
	protected int getCount( )
	{
		return getSession( ).count( getPrincipal( ), getFilter( ) );
	}

	private String getFilter( )
	{
		String filter = " t.status.id <> 1";
		if ( filterPen != null && SysUtils.isEmpty( filterPen.getText( ) ) == false ) {
			filter += " and t.penId = ?1";
		}
		return filter;
	}

	@Listen( "onClick=#cmdFilter" )
	public void onFilter( Event evt )
	{
		Map<String, Object> params = new HashMap<String, Object>( );

		params.put( FilterDialog.fieldsParamName, getFieldFilter( ) );
		Component c = createComponents( "/templates/filter_template.zul", getMainWindow( ), params );
		if ( c instanceof Window ) {
			Window dlg = (Window) c;
			dlg.doModal( );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private List<FieldFilterDTO> getFieldFilter( )
	{
		FieldFilterDTO f;
		ArrayList<FieldFilterDTO> filter = new ArrayList<FieldFilterDTO>( );
		f = new FieldFilterDTO( );
		f.setDisplayName( "Caneta" );
		f.setName( "penId" );
		filter.add( f );

		f = new FieldFilterDTO( );
		f.setDisplayName( "Status" );
		f.setName( "status.id" );
		filter.add( f );
		return filter;
	}
}
