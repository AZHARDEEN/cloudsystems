package br.com.mcampos.ejb.inep;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.StationGradeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.ejb.system.fileupload.FileUploadSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.jpa.inep.InepElement;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepObserverGrade;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepStationReponsable;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.user.Collaborator;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Brief Session Bean implementation class StationSessionBean Esta classe é o facade das telas do posto aplicador.
 */
@Stateless( name = "StationSession", mappedName = "StationSession" )
public class StationSessionBean extends BaseSessionBean implements StationSession, StationSessionLocal
{
	private static final long serialVersionUID = 7677179078573223942L;
	@EJB
	private InepPackageSessionLocal eventSession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionSession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionEvent;

	@EJB
	private FileUploadSessionLocal fileUploadSession;

	@EJB
	private InepMediaSessionLocal inepMediaSession;

	@EJB
	private InepOralTestSessionLocal oralTestSession;

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@EJB
	private MediaSessionBeanLocal mediaSession;

	private static final int MAX_ELEMENTS = 3;
	private static final int MAX_ORAL_GRADE = 6;

	private static final double ORAL_GRADE_WEIGHT_PART1 = 0.50D;
	private static final double ORAL_GRADE_WEIGHT_PART2 = 0.42D;
	private static final double ORAL_GRADE_WEIGHT_PART3 = 0.08D;

	private static final double PART1_ELEMENTS = 3.0D;
	private static final double PART2_ELEMENTS = 2.0D;

	@Override
	/**
	 * Brief Obtem o evento ativo e corrente para a empresa atual
	 * É esperado que exista apenas um evento ativo no período
	 * @Param auth Todo e qualquer tipo principal é o usuário logado no sistema.
	 */
	public InepEvent getCurrentEvent( PrincipalDTO auth )
	{
		List<InepEvent> events;

		events = eventSession.getAvailable( auth );
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
		Collaborator collaborator = collaboratorSession.find( auth );
		if ( collaborator == null ) {
			throw new InvalidParameterException( "Authorization error" );
		}
		List<InepStationReponsable> stations = getStations( collaborator, evt );
		if ( SysUtils.isEmpty( stations ) ) {
			return subscriptionSession.getAll( auth, evt, part );
		}
		else {
			List<Integer> ids = new ArrayList<Integer>( stations.size( ) );
			for ( InepStationReponsable item : stations ) {
				ids.add( item.getStation( ).getId( ).getSequence( ) );
			}
			return subscriptionSession.getAll( auth, evt, part, ids );
		}
	}

	@SuppressWarnings( "unchecked" )
	private List<InepStationReponsable> getStations( Collaborator collaborator, InepEvent event )
	{
		List<InepStationReponsable> stations = Collections.emptyList( );
		try {
			Query query = getEntityManager( ).createNamedQuery( InepStationReponsable.GET_ALL_FROM_COLLABORATOR );
			query.setParameter( 1, collaborator );
			query.setParameter( 2, event );
			stations = query.getResultList( );
		}
		catch ( Exception e ) {
			storeException( e );
		}
		return stations;
	}

	@Override
	public InepMedia storeUploadInformation( PrincipalDTO auth, InepSubscription subscription, MediaDTO media )
	{
		subscription = subscriptionSession.get( subscription.getId( ) );
		inepMediaSession.removeAudio( subscription );
		InepSubscription merged = subscriptionSession.get( subscription.getId( ) );
		InepMedia inepMedia = null;
		if ( merged != null ) {
			inepMedia = inepMediaSession.addAudio( merged, mediaSession.add( media ) );
			merged.add( inepMedia );
		}
		subscription.setStatus( 2 );
		return inepMedia;
	}

	@Override
	public void setInterviewerInformation( PrincipalDTO auth, InepSubscription subscription, int[ ] elements, int grade )
	{
		if ( auth == null || subscription == null ) {
			throw new InvalidParameterException( "Invalid parameters for setInterviewerInformation" );
		}
		subscription = subscriptionSession.get( subscription.getId( ) );

		if ( elements != null ) {
			if ( elements.length != MAX_ELEMENTS ) {
				throw new InvalidParameterException( "Invalid elements for subscription " + subscription.getId( ).getId( ) );
			}
			removeElements( subscription );
			for ( int id : elements ) {
				InepElement element = new InepElement( subscription, id );
				getEntityManager( ).persist( element );
			}
		}

		/*
		 * Setup Interviewer Grade
		 */
		InepOralTest oralTest = oralTestSession.get( subscription );
		if ( oralTest == null ) {
			oralTest = oralTestSession.add( new InepOralTest( subscription ), false );
		}
		oralTest.setInterviewGrade( BigDecimal.valueOf( grade ) );
		subscription.setStatus( 2 );
		oralTestSession.setStatus( oralTest );
	}

	private void removeElements( InepSubscription subscription )
	{
		try {
			String deleteQuery = "DELETE FROM InepElement o WHERE o.subscription = ?1 ";
			Query query = getEntityManager( ).createQuery( deleteQuery ).setParameter( 1, subscription );
			query.executeUpdate( );
		}
		catch ( Exception e ) {
			storeException( e );
		}
	}

	private void removeObserverGrades( InepSubscription subscription )
	{
		try {
			String deleteQuery = "DELETE FROM InepObserverGrade o WHERE o.subscription = ?1 ";
			Query query = getEntityManager( ).createQuery( deleteQuery ).setParameter( 1, subscription );
			query.executeUpdate( );
		}
		catch ( Exception e ) {
			storeException( e );
		}

	}

	@Override
	public void setObserverInformation( PrincipalDTO auth, InepSubscription subscription, Integer[ ] grades )
	{

		Double grade = 0.0D;
		Double grade_part_1 = 0.0D;
		Double grade_part_2 = 0.0D;
		Double grade_part_3 = 0.0D;

		if ( auth == null || subscription == null || grades == null || grades.length != MAX_ORAL_GRADE ) {
			throw new InvalidParameterException( );
		}
		subscription = subscriptionSession.get( subscription.getId( ) );
		removeObserverGrades( subscription );
		int nIndex = 0;
		for ( int id : grades ) {
			InepObserverGrade element = new InepObserverGrade( subscription, ++nIndex, id );
			switch ( nIndex ) {
			case 1:
			case 2:
			case 3:
				grade_part_1 += id;
				break;
			case 4:
			case 5:
				grade_part_2 += id;
				break;
			case 6:
				grade_part_3 += id;
			}
			getEntityManager( ).persist( element );
		}
		grade = ( ( grade_part_1 / PART1_ELEMENTS * ORAL_GRADE_WEIGHT_PART1 )
				+ ( grade_part_2 / PART2_ELEMENTS * ORAL_GRADE_WEIGHT_PART2 )
				+ ( grade_part_3 * ORAL_GRADE_WEIGHT_PART3 ) );
		/*
		 * Setup Oral Grade
		 */
		InepOralTest oralTest = oralTestSession.get( subscription );
		if ( oralTest == null ) {
			oralTest = oralTestSession.add( new InepOralTest( subscription ), false );
		}
		BigDecimal obGrade = BigDecimal.valueOf( grade );
		try {
			obGrade.setScale( 2 );
		}
		catch( Exception e ) {
			obGrade = BigDecimal.valueOf( grade );
		}
		oralTest.setObserverGrade( obGrade );
		oralTestSession.setStatus( oralTest );
		subscription.setStatus( 2 );
	}

	@Override
	public List<InepMedia> lookupForName( PrincipalDTO auth, InepSubscription subscription, String mediaName )
	{
		return inepMediaSession.findByNamedQuery( InepMedia.LookupForMediaName, subscription.getEvent( ), mediaName );
	}

	@Override
	public void reset( PrincipalDTO auth, InepSubscription subscription )
	{
		subscription = subscriptionSession.get( subscription.getId( ) );
		if ( subscription != null ) {
			Query query = getEntityManager( ).createQuery( "Delete from InepOralTest o where o.subscription = ?1" ).setParameter( 1, subscription );
			query.executeUpdate( );
			removeElements( subscription );
			removeObserverGrades( subscription );
			inepMediaSession.removeAudio( subscription );
			subscription.setStatus( 1 );
		}
	}

	@Override
	public void setMissing( PrincipalDTO auth, InepSubscription subscription )
	{
		subscription = subscriptionSession.get( subscription.getId( ) );
		if ( subscription != null ) {
			reset( auth, subscription );
			subscription.setStatus( 3 );
		}
	}

	@Override
	public StationGradeDTO getStationGrade( PrincipalDTO auth, InepSubscription subscription )
	{
		StationGradeDTO dto = new StationGradeDTO( );

		List<InepObserverGrade> observerGrades = getObserverGrades( subscription );
		if ( !SysUtils.isEmpty( observerGrades ) ) {
			int nIndex = 0;
			for ( InepObserverGrade item : observerGrades ) {
				dto.getObserverGrade( )[ nIndex++ ] = item.getGrade( );
			}
		}
		InepOralTest oralTest = oralTestSession.get( subscription );
		if ( oralTest != null && oralTest.getInterviewGrade( ) != null ) {
			dto.setInterviewerGrade( oralTest.getInterviewGrade( ).intValue( ) );
		}
		List<InepElement> elements = getElements( subscription );
		if ( !SysUtils.isEmpty( elements ) ) {
			int nIndex = 0;
			for ( InepElement item : elements ) {
				dto.getElements( )[ nIndex++ ] = item.getId( ).getId( );
			}
		}
		dto.setSubscription( subscription.getId( ).getId( ) );
		return dto;
	}

	@SuppressWarnings( "unchecked" )
	private List<InepObserverGrade> getObserverGrades( InepSubscription subscription )
	{
		List<InepObserverGrade> observerGrades = null;

		try {
			Query query = getEntityManager( ).createQuery( "select o from InepObserverGrade o where o.subscription = ?1 order by o.id " );
			query.setParameter( 1, subscription );
			observerGrades = query.getResultList( );
			return observerGrades;
		}
		catch ( Exception e ) {
			return observerGrades;
		}
	}

	@SuppressWarnings( "unchecked" )
	private List<InepElement> getElements( InepSubscription subscription )
	{
		List<InepElement> items = null;

		try {
			Query query = getEntityManager( ).createQuery( "select o from InepElement o where o.subscription = ?1 order by o.id " );
			query.setParameter( 1, subscription );
			items = query.getResultList( );
			return items;
		}
		catch ( Exception e ) {
			return items;
		}
	}
}
