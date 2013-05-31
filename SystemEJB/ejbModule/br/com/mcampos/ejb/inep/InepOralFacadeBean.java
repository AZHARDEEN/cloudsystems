package br.com.mcampos.ejb.inep;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.dto.inep.InepOralTeamDTO;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.distribution.InepOralDistributionLocal;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;
import br.com.mcampos.ejb.inep.entity.InepMedia;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepSubscriptionPK;
import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.team.TeamSessionLocal;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class InepOralFacadeBean
 */
@Stateless( name = "InepOralFacade", mappedName = "InepOralFacade" )
@LocalBean
public class InepOralFacadeBean implements InepOralFacade
{
	@EJB
	InepOralTestSessionLocal oralTestSession;
	@EJB
	TeamSessionLocal teamSession;
	@EJB
	InepOralDistributionLocal oralDistributionSession;
	@EJB
	DistributionStatusSessionLocal statusSession;
	@EJB
	InepSubscriptionSessionLocal subscriptionSession;
	@EJB
	MediaSessionBeanLocal mediaSession;
	@EJB
	InepMediaSessionLocal inepMediaSession;

	@Override
	public List<InepOralTest> getVarianceOralOnly( Collaborator c, InepPackage pack )
	{
		return oralTestSession.getVarianceOralOnly( pack );
	}

	@Override
	public List<InepPackage> getEvents( Collaborator auth )
	{
		return teamSession.getEvents( auth );
	}

	@Override
	public InepRevisor getRevisor( InepPackage event, Collaborator auth )
	{
		return teamSession.getRevisor( event, auth );
	}

	@Override
	public List<InepOralTeamDTO> getOralTeamToChoice( InepPackage event, Collaborator auth )
	{
		List<InepRevisor> list = teamSession.getOralTeam( event );
		ArrayList<InepOralTeamDTO> retList = null;

		if ( list != null ) {
			retList = new ArrayList<InepOralTeamDTO>( list.size( ) );
			for ( InepRevisor item : list ) {
				InepOralTeamDTO dto = new InepOralTeamDTO( item );
				Integer value = oralDistributionSession.count( " t.id.companyId = ?1 and t.id.eventId = ?2 and t.id.collaboratorId = ?3",
						item.getId( ).getCompanyId( ), item.getId( ).getEventId( ), item.getId( ).getSequence( ) );
				dto.setTests( value );
				retList.add( dto );
			}
		}
		return retList;
	}

	@Override
	public void distribute( InepPackage event, Collaborator auth, InepRevisor r1, InepRevisor r2, Set<InepOralTest> tests )
	{
		if ( event == null || auth == null || r1 == null || r2 == null || SysUtils.isEmpty( tests ) )
			throw new InvalidParameterException( );

		DistributionStatus status = statusSession.get( DistributionStatus.statusDistributed );
		for ( InepOralTest test : tests ) {
			InepOralTest merged = oralTestSession.merge( test );
			merged.setStatus( status );
			oralDistributionSession.merge( new InepOralDistribution( merged, r1, status ) );
			oralDistributionSession.merge( new InepOralDistribution( merged, r2, status ) );
		}
	}

	@Override
	public List<InepOralDistribution> getOralTests( InepRevisor revisor )
	{
		if ( revisor != null )
			return oralDistributionSession.getOralTests( revisor );
		return Collections.emptyList( );
	}

	@Override
	public List<Media> getAudios( InepSubscription subscription )
	{
		ArrayList<Media> medias = null;

		InepSubscription merged = subscriptionSession.get( subscription.getId( ) );
		if ( merged == null || SysUtils.isEmpty( merged.getMedias( ) ) )
			return medias;
		medias = new ArrayList<Media>( );
		for ( InepMedia media : merged.getMedias( ) ) {
			if ( media.getTask( ) == null )
				medias.add( media.getMedia( ) );
		}
		return medias;
	}

	@Override
	public boolean uploadAudio( Integer companyId, Integer eventId, String isc, MediaDTO obj )
	{
		InepSubscriptionPK key = new InepSubscriptionPK( companyId, eventId, isc );
		InepSubscription merged = subscriptionSession.get( key );
		if ( merged == null )
			return false;
		Media media = mediaSession.add( obj );
		InepMedia inepMedia = inepMediaSession.addAudio( merged, media );
		merged.add( inepMedia );
		return true;
	}
}
