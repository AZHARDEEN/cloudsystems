package br.com.mcampos.web.inep.controller;

import java.util.Collection;
import java.util.Date;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.packs.InepPackageSession;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.core.listbox.ListboxParams;
import br.com.mcampos.web.renderer.inep.EventListRenderer;

public class EventsController extends BaseDBListController<InepPackageSession, InepPackage>
{
	private static final long serialVersionUID = 3519756939620280341L;

	@Wire
	private Label infoId;

	@Wire
	private Label infoDescription;

	@Wire
	private Label infoInitDate;

	@Wire
	private Label infoEndDate;

	@Wire
	private Intbox id;

	@Wire
	private Textbox description;

	@Wire
	private Datebox initDate;

	@Wire
	private Datebox endDate;

	@Override
	protected Class<InepPackageSession> getSessionClass( )
	{
		return InepPackageSession.class;
	}

	@Override
	protected void showFields( InepPackage e )
	{
		if( e != null )
		{
			infoInitDate.setValue( SysUtils.formatDate( e.getInitDate( ) ) );
			infoEndDate.setValue( SysUtils.formatDate( e.getEndDate( ) ) );
			initDate.setValue( e.getInitDate( ) );
			endDate.setValue( e.getEndDate( ) );
			infoId.setValue( e.getId( ).getId( ).toString( ) );
			infoDescription.setValue( e.getDescription( ) );
			id.setValue( e.getId( ).getId( ) );
			description.setValue( e.getDescription( ) );
		}
		else
		{
			infoInitDate.setValue( "" );
			infoEndDate.setValue( "" );
			infoId.setValue( "" );
			infoDescription.setValue( "" );

			initDate.setValue( null );
			endDate.setValue( new Date( ) );
			id.setValue( getSession( ).getNextId( getPrincipal( ) ) );
			description.setValue( "" );
			description.setFocus( true );
		}
	}

	@Override
	protected void updateTargetEntity( InepPackage target )
	{
		target.setEndDate( endDate.getValue( ) );
		target.setInitDate( initDate.getValue( ) != null ? initDate.getValue( ) : new Date( ) );
		target.getId( ).setCompanyId( getPrincipal( ).getCompanyID( ) );
		target.getId( ).setId( id.getValue( ) );
		target.setDescription( description.getValue( ) );
	}

	@Override
	protected boolean validateEntity( InepPackage entity, int operation )
	{
		return true;
	}

	@Override
	protected ListitemRenderer<?> getListRenderer( )
	{
		return new EventListRenderer( );
	}

	@Override
	protected Collection<InepPackage> getList( )
	{
		return getAll( 0 );
	}

	@Override
	protected Collection<InepPackage> getAll( int activePage )
	{
		return getSession( ).getAll( getPrincipal( ), new DBPaging( activePage, ListboxParams.maxListBoxPageSize ) );
	}
}
