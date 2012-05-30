package br.com.mcampos.web.core.mdi;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.ejb.security.Login;

public class BaseLoggedMDIController extends BaseMDIController
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

	private boolean isLogged ()
	{
		return getLoggedUser( ) != null;
	}

	protected Login getLoggedUser( )
	{
		return (Login) getSessionParameter( userSessionParamName );
	}


	protected Authentication getAuthentication ()
	{
		Authentication auth = ( Authentication) getSessionParameter( authenticationParamName );
		if ( auth == null )
		{
			auth = new Authentication( );
			auth.setUserId( getLoggedUser( ).getId( ) );
			setSessionParameter( authenticationParamName, auth );
		}
		return auth;
	}

	protected void setCurrentCompany ( SimpleDTO company )
	{
		Authentication auth = getAuthentication( );
		auth.setCompanyId( company.getId( ) );
	}

}
