package br.com.mcampos.web.inep.utils;

import java.util.List;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.locator.ServiceLocator;

public class InepOralTestEventListener implements EventListener<Event>
{
	private InepOralFacade session;

	@Override
	public void onEvent( Event event ) throws Exception
	{
		if ( event != null ) {
			event.stopPropagation( );
			InepOralDistribution obj = (InepOralDistribution) event.getTarget( ).getAttribute( InepOralDistribution.class.getSimpleName( ) );
			List<Media> medias = getSession( ).getAudios( obj.getTest( ).getSubscription( ) );
			if ( SysUtils.isEmpty( medias ) ) {
				Messagebox.show( "Não existe audio da inscrição " + obj.getId( ).getSubscriptionId( ) + " no banco de dados ",
						"Download de Audio", Messagebox.OK, Messagebox.ERROR );
				return;
			}

		}
	}

	public InepOralFacade getSession( )
	{
		try {
			if ( session == null ) {
				Object obj = ServiceLocator.getInstance( ).getRemoteSession( InepOralFacade.class );
				session = (InepOralFacade) PortableRemoteObject.narrow( obj, InepOralFacade.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return session;
	}

}
