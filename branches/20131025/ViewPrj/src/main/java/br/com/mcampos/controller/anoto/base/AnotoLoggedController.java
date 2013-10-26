package br.com.mcampos.controller.anoto.base;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.AnodeFacade;
import br.com.mcampos.sysutils.ServiceLocator;

public class AnotoLoggedController extends LoggedBaseController
{
	private AnodeFacade session;

	public AnotoLoggedController( char c )
	{
		super( c );
	}

	public AnotoLoggedController( )
	{
		super( );
	}

	protected AnodeFacade getSession( )
	{
		if ( session == null )
			session = (AnodeFacade) getRemoteSession( AnodeFacade.class );
		return session;
	}

	@Override
	protected Object getRemoteSession( Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( remoteClass, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

}
