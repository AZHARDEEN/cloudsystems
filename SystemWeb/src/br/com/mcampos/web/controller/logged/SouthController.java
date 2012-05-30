package br.com.mcampos.web.controller.logged;

import static br.com.mcampos.web.core.ComboboxUtils.load;

import java.util.List;

import javax.naming.NamingException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSession;
import br.com.mcampos.web.core.BaseLoggedController;
import br.com.mcampos.web.locator.ServiceLocator;

public class SouthController extends BaseLoggedController<Window>
{
	private static final long serialVersionUID = 1292892431343759655L;
	private CollaboratorSession session = null;

	@Wire( "#companies" )
	Combobox companies;

	@Wire( "#labelVersion" )
	Label version;

	@Listen( "onSelect = #companies" )
	public void onSelectCompanies( )
	{
		Comboitem comboItem = this.companies.getSelectedItem( );
		if ( comboItem != null ) {
			Authentication auth = getAuthentication( );
			setCurrentCompany( (SimpleDTO) comboItem.getValue( ) );
			EventQueues.lookup( IndexController.queueName, true ).publish( new CompanyEventChange( auth ) );
		}
	}


	private CollaboratorSession getSession()
	{
		try {
			if ( this.session == null ) {
				this.session = ( CollaboratorSession ) ServiceLocator.getInstance().getRemoteSession( CollaboratorSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace();
		}
		return this.session;
	}


	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		List<SimpleDTO> list = getSession().getCompanies( getAuthentication( ) );
		load( this.companies, list, true );
		if ( this.companies.getSelectedIndex( ) != -1 ) {
			onSelectCompanies( );
		}
		this.version.setValue( Executions.getCurrent( ).getDesktop( ).getWebApp().getVersion() );
	}

}
