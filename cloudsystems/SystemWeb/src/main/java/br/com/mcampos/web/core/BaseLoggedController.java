package br.com.mcampos.web.core;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import br.com.mcampos.dto.core.PrincipalDTO;

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
		if ( isLogged( ) ) {
			return super.doBeforeCompose( page, parent, compInfo );
		}
		else {
			redirect( "/index.zul" );
			return null;
		}
	}
}
