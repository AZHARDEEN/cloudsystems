package br.com.mcampos.ejb.inep;

import java.security.InvalidParameterException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.system.fileupload.FileUPloadSessionLocal;
import br.com.mcampos.jpa.inep.InepElement;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.system.FileUpload;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Brief Session Bean implementation class StationSessionBean Esta classe é o facade das telas do posto aplicador.
 */
@Stateless( name = "StationSession", mappedName = "StationSession" )
public class StationSessionBean extends BaseSessionBean implements StationSession
{
	private static final long serialVersionUID = 7677179078573223942L;
	@EJB
	private InepPackageSessionLocal eventSession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionSession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionEvent;

	@EJB
	private FileUPloadSessionLocal fileUploadSession;

	@EJB
	private InepMediaSessionLocal inepMediaSession;

	@Override
	/**
	 * Brief Obtem o evento ativo e corrente para a empresa atual
	 * É esperado que exista apenas um evento ativo no período
	 * @Param auth Todo e qualquer tipo principal é o usuário logado no sistema.
	 */
	public InepEvent getCurrentEvent( PrincipalDTO auth )
	{
		List<InepEvent> events;

		events = this.eventSession.getAvailable( auth );
		if ( SysUtils.isEmpty( events ) ) {
			return null;
		}
		if ( events.size( ) > 1 ) {
			throw new RuntimeException( "Too Many Events" );
		}
		return events.get( 0 );
	}

	@Override
	public List<InepSubscription> getSubscriptions( PrincipalDTO auth, InepEvent evt, String part )
	{
		return this.subscriptionSession.getAll( auth, evt, part );
	}

	@Override
	public FileUpload storeUploadInformation( PrincipalDTO auth, InepSubscription subscription, MediaDTO media )
	{
		FileUpload uploaded = this.fileUploadSession.addNewFile( auth, media );

		InepSubscription merged = this.subscriptionSession.get( subscription.getId( ) );
		if ( merged == null ) {
			return null;
		}
		InepMedia inepMedia = this.inepMediaSession.addAudio( merged, uploaded.getMedia( ) );
		merged.add( inepMedia );
		return uploaded;
	}

	@Override
	public void addElements( PrincipalDTO auth, InepSubscription subscription, int[ ] elements )
	{
		if ( auth == null || subscription == null || elements == null || elements.length != 3 ) {
			throw new InvalidParameterException( );
		}
		try {
			String deleteQuery = "DELETE FROM InepElement o WHERE o.subscription = ?1 ";
			Query query = this.getEntityManager( ).createQuery( deleteQuery ).setParameter( 1, subscription );
			query.executeUpdate( );
		}
		catch ( Exception e ) {
			e = null;
		}
		for ( int id : elements ) {
			InepElement element = new InepElement( subscription, id );
			this.getEntityManager( ).persist( element );
		}
	}
}
