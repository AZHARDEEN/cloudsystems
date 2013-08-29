package br.com.mcampos.web.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.client.Client;
import br.com.mcampos.ejb.user.client.ClientSession;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.report.ReportItem;

public class PersonController extends BasePersonController<ClientSession>
{
	private static final long serialVersionUID = -2213603323351941998L;
	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( PersonController.class );

	@Override
	protected Class<ClientSession> getSessionClass( )
	{
		return ClientSession.class;
	}

	@Override
	protected void show( Person person )
	{
		super.show( person );
		if ( person != null ) {
			getCurrentClient( ).setClient( person );
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setCurrentClient( null );
		configPaging( );
		getListBox( ).setItemRenderer( new ClientPersonRenderer( ) );
		loadListbox( );
	}

	@Override
	protected Client update( Client client )
	{
		if ( client == null ) {
			return null;
		}
		if ( validate( client.getClient( ) ) ) {
			updatePerson( (Person) client.getClient( ) );
			client = getSession( ).addNewPerson( getPrincipal( ), client );
			int nIndex = getModel( ).indexOf( client );
			if ( nIndex >= 0 ) {
				getModel( ).set( nIndex, client );
			}
			else {
				getModel( ).add( client );
			}
		}
		return client;
	}

	@Override
	protected void onUpdate( )
	{
		Client client = getListBox( ).getSelectedItem( ).getValue( );
		setCurrentClient( client );
		Person person = (Person) getSession( ).getUser( getPrincipal( ), client.getClient( ).getId( ) );
		show( person );
		if ( SysUtils.isEmpty( getCpf( ).getValue( ) ) ) {
			getCpf( ).setFocus( true );
		}
		else {
			getName( ).setFocus( true );
		}
	}

	@Override
	protected void onNew( )
	{
		setCurrentClient( null );
		show( null );
	}

	@Override
	protected Person getPerson( String doc )
	{
		Person person = (Person) getSession( ).getUser( getPrincipal( ), doc );
		return person;
	}

	@Override
	protected List<ReportItem> getReports( )
	{
		List<ReportItem> list = new ArrayList<ReportItem>( );
		ReportItem item;
		item = new ReportItem( "Listagem de Clientes" );
		item.setReportUrl( "/reports/client_report" );
		item.setParams( configReportParams( ) );
		list.add( item );
		return list;
	}

	@Override
	protected Users createEmptyEntity( )
	{
		return new Person( );
	}

	@Override
	protected List<Client> getClients( )
	{
		return getSession( ).getAllPerson( getPrincipal( ), getPaging( ) );
	}

	@Override
	protected Long getCount( )
	{
		return getSession( ).countPerson( getPrincipal( ) );
	}

	@Override
	protected boolean remove( Client client )
	{
		try {
			getSession( ).remove( client );
			return true;
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
			return false;
		}
	}

	@Override
	protected DocumentType getDocumentType( Integer type )
	{
		return getSession( ).getDocumentType( UserDocument.typeCPF );
	}

}
