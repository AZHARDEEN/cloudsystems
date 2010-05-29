package br.com.mcampos.controller.admin.security;


import br.com.mcampos.controller.core.BasicCRUDController;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleFacade;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tree;

public abstract class SecutityBaseController extends BasicCRUDController
{
	private RoleFacade securitySession;
	private Tree tree;

	protected abstract void showRecord( Object record );

	protected abstract void clearRecord();

	public SecutityBaseController( char c )
	{
		super( c );
	}

	public SecutityBaseController()
	{
		super();
	}

	protected RoleFacade getSession()
	{
		if ( securitySession == null )
			securitySession = ( RoleFacade )getRemoteSession( RoleFacade.class );
		return securitySession;
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		if ( getTree() != null )
			refresh();
	}

	public void onSelect$tree()
	{
		Object record = getCurrentRecord();
		if ( record != null )
			showRecord( record );
		else
			clearRecord();
	}

	protected void setTree( Tree tree )
	{
		this.tree = tree;
	}

	protected Tree getTree()
	{
		return tree;
	}

	@Override
	protected Object getCurrentRecord()
	{
		Object record = null;
		if ( tree.getSelectedItem() != null )
			record = tree.getSelectedItem().getValue();
		return record;
	}
}
