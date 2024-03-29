package br.com.mcampos.web.inep.utils;

import java.util.List;

import javax.naming.NamingException;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.MediaUtil;
import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.jpa.system.Media;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;

public class InepOralTestEventListener implements EventListener<Event>
{
	private InepOralFacade session;

	@Override
	public void onEvent( Event event ) throws Exception
	{
		if ( event != null ) {
			event.stopPropagation( );
			InepOralDistribution obj = (InepOralDistribution) event.getTarget( ).getAttribute( InepOralDistribution.class.getSimpleName( ) );
			List<Media> medias = this.getSession( ).getAudios( obj.getTest( ).getSubscription( ) );
			if ( SysUtils.isEmpty( medias ) ) {
				Messagebox.show( "Não existe audio da inscrição " + obj.getId( ).getSubscriptionId( ) + " no banco de dados ",
						"Download de Audio", Messagebox.OK, Messagebox.ERROR );
				return;
			}
			byte[ ] byteArray = MediaUtil.getObject( medias.get( 0 ) );
			if ( byteArray != null ) {
				Filedownload.save( byteArray, medias.get( 0 ).getMimeType( ), medias.get( 0 ).getName( ) );
			}
			else {
				Messagebox.show( "Audio não está disponível" );
			}
		}
	}

	public InepOralFacade getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (InepOralFacade) ServiceLocator.getInstance( ).getRemoteSession( InepOralFacade.class, ServiceLocator.EJB_NAME[ 0 ] );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

}
