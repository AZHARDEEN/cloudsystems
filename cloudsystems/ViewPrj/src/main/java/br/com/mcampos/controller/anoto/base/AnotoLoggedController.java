package br.com.mcampos.controller.anoto.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.AnodeFacade;
import br.com.mcampos.sysutils.ServiceLocator;

public class AnotoLoggedController extends LoggedBaseController
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5967689892186603816L;
	private AnodeFacade session;
	@SuppressWarnings( "unused" )
	private static final Logger LOGGER = LoggerFactory.getLogger( AnotoLoggedController.class.getSimpleName( ) );

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
		if ( this.session == null ) {
			this.session = (AnodeFacade) this.getRemoteSession( AnodeFacade.class );
		}
		return this.session;
	}

	@Override
	protected Object getRemoteSession( @SuppressWarnings( "rawtypes" ) Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( remoteClass, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

}
