package br.com.mcampos.web.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.utils.dto.PrincipalDTO;
import br.com.mcampos.web.controller.admin.security.renderer.LoginItemRenderer;
import br.com.mcampos.web.core.BaseDBLoggedController;

public class AdminLoginStatusController extends BaseDBLoggedController<LoginSession>
{
	private static final Logger logger = LoggerFactory.getLogger( AdminLoginStatusController.class.getSimpleName( ) );
	private static final long serialVersionUID = -2690927216690469605L;
	@Wire
	private Combobox cmbField;
	@Wire
	private Textbox txtValue;
	@Wire
	private Listbox listTable;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		if ( getCombobox( ).getItemCount( ) > 0 ) {
			getCombobox( ).setSelectedIndex( 0 ); // Name is default
			getTextbox( ).setFocus( true );
		}
		getListbox( ).setItemRenderer( new LoginItemRenderer( ) );
		getListbox( ).setModel( new ListModelList<Login>( ) );
	}

	private Combobox getCombobox( )
	{
		return cmbField;
	}

	private Textbox getTextbox( )
	{
		return txtValue;
	}

	private Listbox getListbox( )
	{
		return listTable;
	}

	@Listen( "onSelect=#cmbField" )
	public void onSelect( Event evt )
	{
		getTextbox( ).setFocus( true );
		if ( evt != null )
			evt.stopPropagation( );
	}

	@Listen( "onOK = #txtValue" )
	public void onOk( Event evt )
	{
		doSearch( );
		if ( evt != null )
			evt.stopPropagation( );
	}

	@Listen( "onClick = #btnSearch" )
	public void onSearch( Event evt )
	{
		doSearch( );
		if ( evt != null )
			evt.stopPropagation( );
	}

	private void doSearch( )
	{
		@SuppressWarnings( "unchecked" )
		ListModelList<Login> model = (ListModelList<Login>) getModel( getListbox( ) );
		if ( model != null ) {
			model.clear( );
		}
		else {
			getListbox( ).getItems( ).clear( );
		}
		if ( cmbField.getSelectedItem( ) == null || SysUtils.isEmpty( txtValue.getValue( ) ) == true )
			return;
		List<Login> login = getSession( ).search( (String) cmbField.getSelectedItem( ).getValue( ), txtValue.getValue( ) );
		if ( SysUtils.isEmpty( login ) ) {
			Messagebox.show( "Nenhum login encontrado", "Localizar Login", Messagebox.OK, Messagebox.INFORMATION );
			return;
		}
		model.addAll( login );
	}

	@Override
	protected Class<LoginSession> getSessionClass( )
	{
		return LoginSession.class;
	}

	@Listen( "onClick=#resetAll" )
	public void onResetAll( Event evt )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item == null ) {
			Messagebox.show( "Por favor, selecione um item da lista primeiro", "Reset de Login", Messagebox.OK, Messagebox.INFORMATION );
			return;
		}
		Login login = (Login) item.getValue( );
		if ( login == null )
			return;
		getSession( ).resetLogin( getPrincipal( ), login, getCredential( ) );
		if ( evt != null )
			evt.stopPropagation( );
	}

	@Listen( "onClick=#personify" )
	public void onPersonify( Event evt )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item == null ) {
			Messagebox.show( "Por favor, selecione um item da lista primeiro", "Reset de Login", Messagebox.OK, Messagebox.INFORMATION );
			return;
		}
		logger.warn( "Personify has been called from Login id: " + getPrincipal( ).getUserId( ) );
		Login login = (Login) item.getValue( );
		if ( login == null )
			return;
		getPrincipal( ).setPersonify( new PrincipalDTO( login.getId( ), login.getPerson( ).getFriendlyName( ) ) );
		logger.warn( "Impersonating " + login.getPerson( ).getName( ) + "Login id: " + login.getId( ) );
		redirect( null );
	}

}
