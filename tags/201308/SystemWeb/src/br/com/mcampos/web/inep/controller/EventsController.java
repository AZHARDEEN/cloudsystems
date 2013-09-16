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
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.packs.InepPackageSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.core.listbox.ListboxParams;
import br.com.mcampos.web.inep.controller.renderer.EventListRenderer;

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
		if ( e != null )
		{
			this.infoInitDate.setValue( SysUtils.formatDate( e.getInitDate( ) ) );
			this.infoEndDate.setValue( SysUtils.formatDate( e.getEndDate( ) ) );
			this.initDate.setValue( e.getInitDate( ) );
			this.endDate.setValue( e.getEndDate( ) );
			this.infoId.setValue( e.getId( ).getId( ).toString( ) );
			this.infoDescription.setValue( e.getDescription( ) );
			this.id.setValue( e.getId( ).getId( ) );
			this.description.setValue( e.getDescription( ) );
		}
		else
		{
			this.infoInitDate.setValue( "" );
			this.infoEndDate.setValue( "" );
			this.infoId.setValue( "" );
			this.infoDescription.setValue( "" );

			this.initDate.setValue( null );
			this.endDate.setValue( new Date( ) );
			this.id.setValue( getSession( ).getNextId( getCurrentCollaborator( ) ) );
			this.description.setValue( "" );
			this.description.setFocus( true );
		}
	}

	@Override
	protected void updateTargetEntity( InepPackage target )
	{
		target.setEndDate( this.endDate.getValue( ) );
		target.setInitDate( this.initDate.getValue( ) != null ? this.initDate.getValue( ) : new Date( ) );
		target.getId( ).setCompanyId( getCurrentCollaborator( ).getCompany( ).getId( ) );
		target.getId( ).setId( this.id.getValue( ) );
		target.setDescription( this.description.getValue( ) );
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
		return getSession( ).getAll( getCurrentCollaborator( ), new DBPaging( activePage, ListboxParams.maxListBoxPageSize ) );
	}
}