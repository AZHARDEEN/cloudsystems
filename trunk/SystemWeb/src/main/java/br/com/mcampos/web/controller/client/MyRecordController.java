package br.com.mcampos.web.controller.client;

import java.util.List;

import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.person.PersonSession;
import br.com.mcampos.entity.user.Client;
import br.com.mcampos.entity.user.DocumentType;
import br.com.mcampos.entity.user.Person;
import br.com.mcampos.entity.user.Users;

public class MyRecordController extends BasePersonController<PersonSession>
{
	private static final long serialVersionUID = 8899739187504552446L;

	private Person person;

	@Override
	protected Class<PersonSession> getSessionClass( )
	{
		return PersonSession.class;
	}

	@Override
	protected boolean onOk( )
	{

		updatePerson( getPerson( null ) );
		if ( validate( getPerson( null ) ) ) {
			getSession( ).merge( getPerson( null ) );
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void onCancel( )
	{
		unloadMe( );
	}

	@Override
	protected Person getPerson( String doc )
	{
		if ( person == null ) {
			person = getSession( ).get( getPrincipal( ).getUserId( ) );
		}
		return person;
	}

	@Override
	protected DocumentType getDocumentType( Integer type )
	{
		return null;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		// TODO Auto-generated method stub
		super.doAfterCompose( comp );
		getBornState( ).load( );
		getMaritalStatus( ).load( );
		getGender( ).load( );
		show( getPerson( null ) );
		getName( ).setFocus( true );
	}

	@Override
	protected Users createEmptyEntity( )
	{
		return null;
	}

	@Override
	protected List<Client> getClients( )
	{
		return null;
	}

	@Override
	protected Long getCount( )
	{
		return null;
	}

	@Override
	protected void onUpdate( )
	{

	}

	@Override
	protected void onNew( )
	{

	}

	@Override
	protected boolean remove( Client client )
	{
		return false;
	}

	@Override
	protected Client update( Client client )
	{
		// TODO Auto-generated method stub
		return null;
	}

}
