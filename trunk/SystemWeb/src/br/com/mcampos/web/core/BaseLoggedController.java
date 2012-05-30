package br.com.mcampos.web.core;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.security.Login;

public abstract class BaseLoggedController<T extends Component> extends BaseController<T>
{
	private static final long serialVersionUID = 2513737533097076034L;

	private boolean isLogged( )
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

	protected Login getLoggedUser( )
	{
		return (Login) getSessionParameter( userSessionParamName );
	}

	protected Authentication getAuthentication( )
	{
		Authentication auth = (Authentication) getSessionParameter( authenticationParamName );
		if ( auth == null )
		{
			auth = new Authentication( );
			auth.setUserId( getLoggedUser( ).getId( ) );
			setSessionParameter( authenticationParamName, auth );
		}
		return auth;
	}

	protected void setCurrentCompany( SimpleDTO company )
	{
		Authentication auth = getAuthentication( );
		auth.setCompanyId( company.getId( ) );
	}

}
