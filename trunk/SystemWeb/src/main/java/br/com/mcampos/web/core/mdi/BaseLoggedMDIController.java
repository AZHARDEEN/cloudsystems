package br.com.mcampos.web.core.mdi;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.web.core.LoggedInterface;

public class BaseLoggedMDIController extends BaseMDIController implements LoggedInterface
{
	private static final long serialVersionUID = 3441410095758996757L;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
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
	public boolean isLogged( )
	{
		return getLoggedUser( ) != null;
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

}
