package br.com.mcampos.web.controller.client;

import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.ejb.user.person.PersonSession;

public class MyRecordController extends BasePersonController<PersonSession>
{
	private static final long serialVersionUID = 8899739187504552446L;

	@Override
	protected Class<PersonSession> getSessionClass( )
	{
		return PersonSession.class;
	}

	@Override
	protected void onOk( )
	{
		if ( validate( getCurrentCollaborator( ).getPerson( ) ) ) {
			updatePerson( getCurrentCollaborator( ).getPerson( ) );
			getSession( ).merge( getCurrentCollaborator( ).getPerson( ) );
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
		return getCurrentCollaborator( ).getPerson( );
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
		show( getCurrentCollaborator( ).getPerson( ) );
		getName( ).setFocus( true );
	}

}
