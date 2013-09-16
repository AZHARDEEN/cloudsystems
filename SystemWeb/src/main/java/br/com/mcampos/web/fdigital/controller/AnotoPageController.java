package br.com.mcampos.web.fdigital.controller;

import java.util.Collection;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.fdigital.form.pad.page.AnotoPageSession;
import br.com.mcampos.jpa.fdigital.AnotoForm;
import br.com.mcampos.jpa.fdigital.AnotoPage;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class AnotoPageController extends BaseDBListController<AnotoPageSession, AnotoPage>
{
	private static final long serialVersionUID = 908771873595482637L;

	@Wire
	private Label recordId;
	@Wire
	private Label recordDescription;
	@Wire
	private Label recordIcrTemplate;

	@Wire
	private Textbox editId;
	@Wire
	private Textbox editDescription;
	@Wire
	private Textbox editIcrTemplate;

	@Wire
	private Combobox comboForm;

	@Override
	protected void showFields( AnotoPage targetEntity )
	{
		if ( targetEntity != null ) {
			this.recordId.setValue( targetEntity.getId( ).getId( ) );
			this.recordDescription.setValue( targetEntity.getDescription( ) );
			this.recordIcrTemplate.setValue( targetEntity.getTemplateIcr( ) );

			this.editId.setText( targetEntity.getId( ).getId( ) );
			this.editDescription.setText( targetEntity.getDescription( ) );
			this.editIcrTemplate.setText( targetEntity.getTemplateIcr( ) );
		}
		else {
			this.recordId.setValue( "" );
			this.recordDescription.setValue( "" );
			this.recordIcrTemplate.setValue( "" );

			this.editId.setRawValue( "" );
			this.editDescription.setRawValue( "" );
			this.editIcrTemplate.setRawValue( "" );
		}
	}

	@Override
	protected void updateTargetEntity( AnotoPage entity )
	{
		entity.setTemplateIcr( this.editIcrTemplate.getValue( ) );
		entity.setDescription( this.editDescription.getValue( ) );
	}

	@Override
	protected boolean validateEntity( AnotoPage entity, int operation )
	{
		return true;
	}

	@Listen( "onSelect=combobox " )
	public void onSelect( Event evt )
	{
		loadPage( 0 );
	}

	@Override
	protected Class<AnotoPageSession> getSessionClass( )
	{
		return AnotoPageSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getCreateButton( ).setVisible( false );
		getDeleteButton( ).setVisible( false );
	}

	@Override
	protected Collection<AnotoPage> getAll( int activePage )
	{
		return getSession( ).getAll( getSelectedForm( ), new DBPaging( activePage, getRows( ) ) );
	}

	private AnotoForm getSelectedForm( )
	{
		if ( this.comboForm.getSelectedItem( ) != null ) {
			return (AnotoForm) this.comboForm.getSelectedItem( ).getValue( );
		}
		else {
			return null;
		}
	}

}
