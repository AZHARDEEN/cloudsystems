package br.com.mcampos.web.controller.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.client.ClientSession;
import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.jpa.user.DocumentType;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.jpa.user.Users;
import br.com.mcampos.sysutils.CNPJ;
import br.com.mcampos.sysutils.CPF;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.renderer.ClientCompanyRenderer;

public class CompanyController extends UserController<ClientSession>
{
	private static final long serialVersionUID = -5957735242513634394L;
	private static final Logger logger = LoggerFactory.getLogger( CompanyController.class );

	@Wire
	private Textbox cnpj;

	@Override
	protected Class<ClientSession> getSessionClass( )
	{
		return ClientSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setCurrentClient( null );
		configPaging( );
		getListBox( ).setItemRenderer( new ClientCompanyRenderer( ) );
		loadListbox( );
	}

	@Override
	protected Users createEmptyEntity( )
	{
		return new Company( );
	}

	@Override
	protected List<Client> getClients( )
	{
		return getSession( ).getAllCompany( getPrincipal( ), getPaging( ) );
	}

	@Override
	protected Long getCount( )
	{
		return getSession( ).countCompany( getPrincipal( ) );
	}

	@Override
	protected void onUpdate( )
	{
		Client client = getListBox( ).getSelectedItem( ).getValue( );
		setCurrentClient( client );
		Company person = (Company) getSession( ).getUser( getPrincipal( ), client.getClient( ).getId( ) );
		show( person );
		/*
		 * if ( SysUtils.isEmpty( getCpf( ).getValue( ) ) ) { getCpf(
		 * ).setFocus( true ); } else { getName( ).setFocus( true ); }
		 */

	}

	@Override
	protected void onNew( )
	{
		setCurrentClient( null );
		show( null );
	}

	@Override
	protected boolean remove( Client client )
	{
		try {
			getSession( ).remove( getPrincipal( ), client.getId( ) );
			return true;
		}
		catch( Exception e )
		{
			e.printStackTrace( );
			return false;
		}
	}

	protected void show( Company c )
	{
		super.show( c );
		if( c != null ) {
			getCurrentClient( ).setClient( c );
			showCNPJ( c.getDocuments( ) );
		}
		else {
			getDocument( ).setRawValue( "" );
		}
	}

	protected void showCNPJ( List<UserDocument> docs )
	{
		getDocument( ).setValue( "" );
		for( UserDocument doc : docs ) {
			if( doc.getType( ).getId( ).equals( UserDocument.CNPJ ) ) {
				getDocument( ).setValue( doc.getCode( ) );
				break;
			}
		}
	}

	@Override
	protected Client update( Client client )
	{
		if( client == null ) {
			return null;
		}
		if( validate( client.getClient( ) ) ) {
			updateCompany( (Company) client.getClient( ) );
			client = getSession( ).addNewCompany( getPrincipal( ), client );
			int nIndex = getModel( ).indexOf( client );
			if( nIndex >= 0 ) {
				getModel( ).set( nIndex, client );
			}
			else {
				getModel( ).add( client );
			}
		}
		return client;
	}

	protected void updateCompany( Company c )
	{
		if( c == null ) {
			return;
		}
		super.update( c );
	}

	@Listen( "onBlur=#cnpj" )
	public void onBlur( Event evt )
	{
		String doc = getDocument( ).getValue( );
		if( SysUtils.isEmpty( doc ) ) {
			return;
		}
		Company c = getCompany( CNPJ.removeMask( doc ) );
		if( c == null ) {
			getDocument( ).setValue( doc );
			addDocument( CPF.removeMask( doc ), getDocumentType( UserDocument.CNPJ ) );
		}
		else {
			show( c );
		}
		if( evt != null ) {
			logger.info( "onBlur: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onBlur with evt null " );
		}
	}

	private Textbox getDocument( )
	{
		return cnpj;
	}

	protected Company getCompany( String doc )
	{
		Company c = (Company) getSession( ).getUser( getPrincipal( ), doc );
		return c;
	}

	@Override
	protected DocumentType getDocumentType( Integer type )
	{
		return getSession( ).getDocumentType( type );
	}

}
