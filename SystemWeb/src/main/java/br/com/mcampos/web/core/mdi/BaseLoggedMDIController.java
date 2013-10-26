package br.com.mcampos.web.core.mdi;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Window;

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
		if ( this.isLogged( ) ) {
			return super.doBeforeCompose( page, parent, compInfo );
		}
		else {
			this.redirect( "/index.zul" );
			return null;
		}
	}

	@Override
	public boolean isLogged( )
	{
		return this.getPrincipal( ) != null;
	}
}
