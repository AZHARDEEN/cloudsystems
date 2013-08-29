package br.com.mcampos.web.controller.logged;

import static br.com.mcampos.web.core.ComboboxUtils.load;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.utils.dto.PrincipalDTO;
import br.com.mcampos.web.core.BaseDBLoggedController;

public class SouthController extends BaseDBLoggedController<CollaboratorSession>
{
	private static final long serialVersionUID = 1292892431343759655L;

	private static final String lastCompany = "Collaborator.lastCompany";
	private static final Logger logger = LoggerFactory.getLogger( SouthController.class.getSimpleName( ) );

	@Wire( "#companies" )
	private Combobox companies;

	@Wire( "#labelVersion" )
	private Label version;

	@Wire( "#labelWho" )
	private Label who;

	@Wire( "#btnUnpersonify" )
	private Button btnUnpersonify;

	@Listen( "onSelect = #companies" )
	public void onSelectCompanies( )
	{
		Comboitem comboItem = companies.getSelectedItem( );
		if( comboItem != null && comboItem.getValue( ) instanceof SimpleDTO ) {
			SimpleDTO dto = (SimpleDTO) comboItem.getValue( );
			if( dto != null && dto.getId( ) != null ) {
				setCookie( lastCompany, dto.getId( ).toString( ) );
			}
		}
		setCurrentCompany( );

	}

	private void setCurrentCompany( )
	{
		Comboitem comboItem = companies.getSelectedItem( );
		if( comboItem != null && comboItem.getValue( ) instanceof SimpleDTO ) {
			SimpleDTO dto = (SimpleDTO) comboItem.getValue( );
			PrincipalDTO auth = getPrincipal( );
			auth.setCompanyID( dto.getId( ) );
			Collaborator c = getSession( ).find( auth );
			if( c != null ) {
				setCollaborator( c );
				EventQueues.lookup( IndexController.queueName, true ).publish( new CompanyEventChange( c ) );
			}
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		List<SimpleDTO> list = getSession( ).getCompanies( getPrincipal( ) );
		load( companies, list, false );
		locateLastUsedCompany( );
		if( companies.getSelectedIndex( ) != -1 ) {
			setCurrentCompany( );
		}
		version.setValue( Executions.getCurrent( ).getDesktop( ).getWebApp( ).getVersion( ) );
		if( who != null ) {
			who.setValue( "Usuário: " + getPrincipal( ) );
		}
		PrincipalDTO login = getRealLoggedUser( );
		if( btnUnpersonify != null && login != null && login.getPersonify( ) != null ) {
			btnUnpersonify.setVisible( true );
		}
		else {
			btnUnpersonify.setVisible( false );
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
		if( SysUtils.isEmpty( value ) == false )
		{
			try {
				Integer id = Integer.parseInt( value );
				for( Comboitem item : companies.getItems( ) ) {
					SimpleDTO dto = (SimpleDTO) item.getValue( );
					if( dto != null && dto.getId( ).equals( id ) ) {
						companies.setSelectedItem( item );
						break;
					}
				}
			}
			catch( Exception e )
			{
				if( companies.getItemCount( ) > 0 ) {
					companies.setSelectedIndex( 0 );
				}
			}
		}
		else {
			if( companies.getItemCount( ) > 0 ) {
				companies.setSelectedIndex( 0 );
			}
		}
	}

	@Override
	protected boolean isValidAccess( String path )
	{
		return true;
	}

	@Listen( "onClick=#btnUnpersonify" )
	public void unpersonify( MouseEvent evt )
	{
		PrincipalDTO login = getRealLoggedUser( );
		if( login != null ) {
			login.setPersonify( null );
		}
		if( btnUnpersonify != null )
			btnUnpersonify.setVisible( false );
		redirect( null );
		if( evt != null )
			evt.stopPropagation( );
	}

	private PrincipalDTO getRealLoggedUser( )
	{
		Object obj = getPrincipal( );

		if( obj instanceof PrincipalDTO ) {
			PrincipalDTO login = (PrincipalDTO) obj;
			logger.warn( "GetRealLoggedUser has been called from " + login.getUserId( ) );
			return login;
		}
		else
			return null;
	}

}
