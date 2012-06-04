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

	@Listen( "onSelect = #companies" )
	public void onSelectCompanies( )
	{
		Comboitem comboItem = this.companies.getSelectedItem( );
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
		Comboitem comboItem = this.companies.getSelectedItem( );
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
		load( this.companies, list, false );
		locateLastUsedCompany( );
		if ( this.companies.getSelectedIndex( ) != -1 ) {
			setCurrentCompany( );
		}
		this.version.setValue( Executions.getCurrent( ).getDesktop( ).getWebApp( ).getVersion( ) );
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
				for ( Comboitem item : this.companies.getItems( ) ) {
					SimpleDTO dto = (SimpleDTO) item.getValue( );
					if ( dto != null && dto.getId( ).equals( id ) ) {
						this.companies.setSelectedItem( item );
						break;
					}
				}
			}
			catch ( Exception e )
			{
				if ( this.companies.getItemCount( ) > 0 ) {
					this.companies.setSelectedIndex( 0 );
				}
			}
		}
		else {
			if ( this.companies.getItemCount( ) > 0 ) {
				this.companies.setSelectedIndex( 0 );
			}
		}
	}
}
