package br.com.mcampos.web.controller.client;

import java.util.List;

import org.zkoss.zul.Window;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.user.person.PersonSession;
import br.com.mcampos.jpa.security.UserStatus;
import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.DocumentType;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.Users;

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

		this.updatePerson( this.getPerson( null ) );
		if ( this.validate( this.getPerson( null ) ) ) {
			PrincipalDTO dto = this.getPrincipal( );
			this.getSession( ).update( dto, this.getPerson( null ) );
			if ( dto.getLoginStatus( ).equals( UserStatus.statusFullfillRecord ) ) {
				dto.setLoginStatus( UserStatus.statusOk );
			}
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void onCancel( )
	{
		this.unloadMe( );
	}

	@Override
	protected Person getPerson( String doc )
	{
		if ( this.person == null ) {
			this.person = this.getSession( ).get( this.getPrincipal( ).getUserId( ) );
		}
		return this.person;
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
		this.getBornState( ).load( );
		this.getMaritalStatus( ).load( );
		this.getGender( ).load( );
		this.show( this.getPerson( null ) );
		this.getName( ).setFocus( true );
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
