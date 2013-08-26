package br.com.mcampos.web.controller.logged;

import static br.com.mcampos.web.core.ComboboxUtils.load;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;

public class SouthController extends BaseDBLoggedController<CollaboratorSession>
{
	private static final long serialVersionUID = 1292892431343759655L;

	private static final String lastCompany = "Collaborator.lastCompany";

	@Wire( "#companies" )
	Combobox companies;

	@Wire( "#labelVersion" )
	Label version;

	@Wire( "#labelWho" )
	Label who;

	@Listen( "onSelect = #companies" )
	public void onSelectCompanies( )
	{
		Comboitem comboItem = companies.getSelectedItem( );
		if ( comboItem != null && comboItem.getValue( ) instanceof SimpleDTO ) {
			SimpleDTO dto = (SimpleDTO) comboItem.getValue( );
			if ( dto != null && dto.getId( ) != null ) {
				setCookie( lastCompany, dto.getId( ).toString( ) );
			}
		}
		setCurrentCompany( );

	}

	private void setCurrentCompany( )
	{
		Comboitem comboItem = companies.getSelectedItem( );
		if ( comboItem != null && comboItem.getValue( ) instanceof SimpleDTO ) {
			SimpleDTO dto = (SimpleDTO) comboItem.getValue( );
			Collaborator c = getSession( ).find( getLoggedUser( ), dto.getId( ) );
			if ( c != null ) {
				setCollaborator( c );
				EventQueues.lookup( IndexController.queueName, true ).publish( new CompanyEventChange( c ) );
			}
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		List<SimpleDTO> list = getSession( ).getCompanies( getLoggedUser( ) );
		load( companies, list, false );
		locateLastUsedCompany( );
		if ( companies.getSelectedIndex( ) != -1 ) {
			setCurrentCompany( );
		}
		version.setValue( Executions.getCurrent( ).getDesktop( ).getWebApp( ).getVersion( ) );
		if ( who != null ) {
			String strWho = getLoggedUser( ).getPerson( ).getFirstName( );
			if ( SysUtils.isEmpty( strWho ) )
				strWho = getLoggedUser( ).getPerson( ).getName( );
			who.setValue( "Usuário: " + strWho );
		}
	}

	@Override
	protected Class<CollaboratorSession> getSessionClass( )
	{
		return CollaboratorSession.class;
	}

	private void locateLastUsedCompany( )
	{
		String value = getCookie( lastCompany );
		if ( SysUtils.isEmpty( value ) == false )
		{
			try {
				Integer id = Integer.parseInt( value );
				for ( Comboitem item : companies.getItems( ) ) {
					SimpleDTO dto = (SimpleDTO) item.getValue( );
					if ( dto != null && dto.getId( ).equals( id ) ) {
						companies.setSelectedItem( item );
						break;
					}
				}
			}
			catch ( Exception e )
			{
				if ( companies.getItemCount( ) > 0 ) {
					companies.setSelectedIndex( 0 );
				}
			}
		}
		else {
			if ( companies.getItemCount( ) > 0 ) {
				companies.setSelectedIndex( 0 );
			}
		}
	}

	@Override
	protected boolean isValidAccess( String path )
	{
		return true;
	}
}
