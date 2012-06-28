package br.com.mcampos.web.core;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

public abstract class BaseDBLoggedController<BEAN> extends BaseDBController<BEAN> implements LoggedInterface
{
	private static final long serialVersionUID = 3928960337564242027L;

	@Override
	public boolean isLogged( )
	{
		Login login = getLoggedUser( );
		return login != null;
	}

	@Override
	public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
	{
		if ( isLogged( ) ) {
			return super.doBeforeCompose( page, parent, compInfo );
		}
		else {
			redirect( "/index.zul" );
			return null;
		}
	}

	@Override
	public Login getLoggedUser( )
	{
		return (Login) getSessionParameter( userSessionParamName );
	}

	@Override
	public Collaborator getCurrentCollaborator( )
	{
		Collaborator c = (Collaborator) getSessionParameter( currentCollaborator );
		Login l = getLoggedUser( );
		if ( c.getPerson( ).equals( l.getPerson( ) ) == false ) {
			return null;
		}
		return c;
	}

	protected void setCollaborator( Collaborator c )
	{
		setSessionParameter( currentCollaborator, c );
	}
}
