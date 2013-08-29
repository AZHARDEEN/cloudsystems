package br.com.mcampos.web.core;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.utils.dto.PrincipalDTO;

public abstract class BaseLoggedController<T extends Component> extends BaseController<T>
{
	private static final long serialVersionUID = 2513737533097076034L;

	private boolean isLogged( )
	{
		PrincipalDTO login = getPrincipal( );
		return login != null;
	}

	@Override
	public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
	{
		if( isLogged( ) ) {
			return super.doBeforeCompose( page, parent, compInfo );
		}
		else {
			redirect( "/index.zul" );
			return null;
		}
	}

	protected PrincipalDTO getPrincipal( )
	{
		Object obj = getSessionParameter( LoggedInterface.currentPrincipal );

		if( obj instanceof Login ) {
			PrincipalDTO login = (PrincipalDTO) obj;
			if( login != null && login.getPersonify( ) != null )
				return login.getPersonify( );
			return login;
		}
		else
			return null;
	}
}
