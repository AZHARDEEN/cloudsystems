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
import br.com.mcampos.web.core.BaseDBLoggedController;

public class SouthController extends BaseDBLoggedController<CollaboratorSession>
{
	private static final long serialVersionUID = 1292892431343759655L;

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
			Collaborator c = getSession( ).find( getLoggedUser( ), dto.getId( ) );
			setCollaborator( c );
			EventQueues.lookup( IndexController.queueName, true ).publish( new CompanyEventChange( c ) );
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		List<SimpleDTO> list = getSession( ).getCompanies( getLoggedUser( ) );
		load( this.companies, list, true );
		if ( this.companies.getSelectedIndex( ) != -1 ) {
			onSelectCompanies( );
		}
		this.version.setValue( Executions.getCurrent( ).getDesktop( ).getWebApp( ).getVersion( ) );
	}

	@Override
	protected Class<CollaboratorSession> getSessionClass( )
	{
		return CollaboratorSession.class;
	}

}
