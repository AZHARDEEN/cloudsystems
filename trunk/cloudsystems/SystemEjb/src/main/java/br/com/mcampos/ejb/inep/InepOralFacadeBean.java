package br.com.mcampos.ejb.inep;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.distribution.InepOralDistributionLocal;
import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.team.TeamSessionLocal;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.jpa.inep.DistributionStatus;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.jpa.inep.InepOralDistributionPK;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;
import br.com.mcampos.jpa.system.Media;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class InepOralFacadeBean
 */
@Stateless( name = "InepOralFacade", mappedName = "InepOralFacade" )
@LocalBean
public class InepOralFacadeBean extends BaseSessionBean implements InepOralFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3986248629620631049L;
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
	@EJB
	InepRevisorSessionLocal revisorSession;
	@EJB
	InepPackageSessionLocal eventSession;

	@Override
	public List<InepOralTest> getVarianceOralOnly( PrincipalDTO c, InepEvent pack )
	{
		return this.oralTestSession.getVarianceOralOnly( pack );
	}

	@Override
	public List<InepEvent> getEvents( PrincipalDTO auth )
	{
		return this.eventSession.getAvailable( auth );
	}

	@Override
	public InepRevisor getRevisor( InepEvent event, PrincipalDTO auth )
	{
		return this.teamSession.getRevisor( event, auth );
	}

	@Override
	public List<InepOralTeamDTO> getOralTeamToChoice( InepEvent event, PrincipalDTO auth )
	{
		List<InepRevisor> list = this.teamSession.getOralTeam( event );
		ArrayList<InepOralTeamDTO> retList = null;

		if ( list != null ) {
			retList = new ArrayList<InepOralTeamDTO>( list.size( ) );
			for ( InepRevisor item : list ) {
				InepOralTeamDTO dto = new InepOralTeamDTO( item );
				Integer value = this.oralDistributionSession.count( auth, " t.id.companyId = ?1 and t.id.eventId = ?2 and t.id.collaboratorId = ?3",
						item.getId( ).getCompanyId( ), item.getId( ).getEventId( ), item.getId( ).getSequence( ) );
				dto.setTests( value );
				retList.add( dto );
			}
		}
		return retList;
	}

	@Override
	public void distribute( InepEvent event, PrincipalDTO auth, InepRevisor r1, InepRevisor r2, Set<InepOralTest> tests )
	{
		if ( event == null || auth == null || r1 == null || r2 == null || SysUtils.isEmpty( tests ) ) {
			throw new InvalidParameterException( );
		}

		DistributionStatus status = this.statusSession.get( DistributionStatus.statusDistributed );
		for ( InepOralTest test : tests ) {
			InepOralTest merged = this.oralTestSession.merge( test );
			merged.setStatusId( 3 );
			this.oralDistributionSession.merge( new InepOralDistribution( merged, r1, status ) );
			this.oralDistributionSession.merge( new InepOralDistribution( merged, r2, status ) );
		}
	}

	@Override
	public List<InepOralDistribution> getOralTests( InepRevisor revisor )
	{
		return this.oralDistributionSession.getOralTests( revisor );
	}

	@Override
	public List<Media> getAudios( InepSubscription subscription )
	{
		ArrayList<Media> medias = null;

		InepSubscription merged = this.subscriptionSession.get( subscription.getId( ) );
		if ( merged == null || SysUtils.isEmpty( merged.getMedias( ) ) ) {
			return medias;
		}
		medias = new ArrayList<Media>( );
		for ( InepMedia media : merged.getMedias( ) ) {
			if ( media.getTask( ) == null && media.getMedia( ) != null ) {
				media.getMedia( ).getObject( );
				medias.add( media.getMedia( ) );
			}
		}
		return medias;
	}

	@Override
	public boolean uploadAudio( Integer companyId, Integer eventId, String isc, MediaDTO obj )
	{
		InepSubscriptionPK key = new InepSubscriptionPK( companyId, eventId, isc );
		InepSubscription merged = this.subscriptionSession.get( key );
		if ( merged == null ) {
			return false;
		}
		List<Media> audios = this.getAudios( merged );
		if ( SysUtils.isEmpty( audios ) ) {
			Media media = this.mediaSession.add( obj );
			InepMedia inepMedia = this.inepMediaSession.addAudio( merged, media );
			merged.add( inepMedia );
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void updateGrade( InepOralDistribution item, int grade )
	{
		InepOralDistribution merged = this.oralDistributionSession.get( item.getId( ) );
		if ( merged == null ) {
			throw new InvalidParameterException( "Item não existe (InepOralDistribution)" );
		}
		merged.setNota( grade );
		merged.setStatus( this.statusSession.get( DistributionStatus.statusRevised ) );
		/*
		 * Cuidado, a posicao da linha abaixo e importante!!!!
		 */
		this.oralTestSession.setAgreementGrade( merged.getTest( ), grade, item.getRevisor( ).isCoordenador( ) );
		if ( item.getRevisor( ).isCoordenador( ) == false ) {
			InepOralDistribution other = this.oralDistributionSession.findOther( merged );
			if ( other != null && !merged.getNota( ).equals( other.getNota( ) ) ) {
				throw new RuntimeException( "As notas da prova oral não poder ser diferentes entre os corretores" );
			}
			if ( other == null && !merged.getTest( ).getStatusId( ).equals( 4 ) ) {
				List<InepRevisor> coordinators = this.revisorSession.getOralCoordinator( item.getTest( ).getSubscription( ).getEvent( ) );
				DistributionStatus st = this.statusSession.get( DistributionStatus.statusDistributed );
				for ( InepRevisor c : coordinators ) {
					InepOralDistributionPK key = new InepOralDistributionPK( );
					key.setCompanyId( merged.getId().getCompanyId( ) );
					key.setEventId( merged.getId( ).getEventId( ) );
					key.setSubscriptionId( merged.getId( ).getSubscriptionId( ) );
					key.setCollaboratorId( c.getId( ).getSequence( ) );
					InepOralDistribution coordinatorDistribution = oralDistributionSession.get( key  );
					if ( coordinatorDistribution == null ) {
						this.oralDistributionSession.add( new InepOralDistribution( merged.getTest( ), c, st ) );
					}
				}
			}
		}
	}
}
