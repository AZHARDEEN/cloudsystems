package br.com.mcampos.controller.logged;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.facade.CollaboratorFacade;

public class SouthLoggedController extends LoggedBaseController
{
	private Label labelCopyright;
	private Label labelVersion;
	private Combobox companies;
	private Combobox cmbThemes;

	private CollaboratorFacade session;

	public SouthLoggedController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLabels( );
		List<CompanyDTO> list = getSession( ).getCompanies( getLoggedInUser( ) );
		loadCombobox( companies, list );
		if ( list.size( ) > 0 ) {
			companies.setSelectedIndex( 0 );
			onSelect$companies( );
		}
		labelVersion.setValue( Executions.getCurrent( ).getDesktop( ).getWebApp( ).getVersion( ) );
	}

	private void setLabels( )
	{
		setLabel( labelCopyright );
	}

	public CollaboratorFacade getSession( )
	{
		if ( session == null )
			session = (CollaboratorFacade) getRemoteSession( CollaboratorFacade.class );
		return session;
	}

	public void onSelect$companies( )
	{
		Comboitem comboItem = companies.getSelectedItem( );
		if ( comboItem != null ) {
			CompanyDTO dto = (CompanyDTO) comboItem.getValue( );
			AuthenticationDTO auth = getLoggedInUser( );
			auth.setCurrentCompany( dto.getId( ) );
			setLoggedInUser( auth );
			Events.postEvent( new Event( Events.ON_NOTIFY, null, dto ) );
		}
	}
}
