package br.com.mcampos.ejb.inep;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
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
import br.com.mcampos.jpa.inep.InepElement;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepObserverGrade;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepSubscription;
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
	private FileUploadSessionLocal fileUploadSession;

	@EJB
	private InepMediaSessionLocal inepMediaSession;

	@EJB
	private InepOralTestSessionLocal oralTestSession;

	@EJB
	private MediaSessionBeanLocal mediaSession;

	private static final int MAX_ELEMENTS = 3;
	private static final int MAX_ORAL_GRADE = 6;

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
	public InepMedia storeUploadInformation( PrincipalDTO auth, InepSubscription subscription, MediaDTO media )
	{
		subscription = this.subscriptionSession.get( subscription.getId( ) );
		this.inepMediaSession.removeAudio( subscription );
		InepSubscription merged = this.subscriptionSession.get( subscription.getId( ) );
		InepMedia inepMedia = null;
		if ( merged != null ) {
			inepMedia = this.inepMediaSession.addAudio( merged, this.mediaSession.add( media ) );
			merged.add( inepMedia );
		}
		subscription.setStatus( 2 );
		return inepMedia;
	}

	@Override
	public void setInterviewerInformation( PrincipalDTO auth, InepSubscription subscription, int[ ] elements, int grade )
	{
		if ( auth == null || subscription == null || elements == null || elements.length != MAX_ELEMENTS ) {
			throw new InvalidParameterException( );
		}
		subscription = this.subscriptionSession.get( subscription.getId( ) );
		this.removeElements( subscription );
		for ( int id : elements ) {
			InepElement element = new InepElement( subscription, id );
			this.getEntityManager( ).persist( element );
		}

		/*
		 * Setup Interviewer Grade
		 */
		InepOralTest oralTest = this.oralTestSession.get( subscription );
		if ( oralTest == null ) {
			oralTest = this.oralTestSession.add( new InepOralTest( subscription ), false );
		}
		oralTest.setInterviewGrade( BigDecimal.valueOf( grade ) );
		subscription.setStatus( 2 );
		this.oralTestSession.setStatus( oralTest );
	}

	private void removeElements( InepSubscription subscription )
	{
		try {
			String deleteQuery = "DELETE FROM InepElement o WHERE o.subscription = ?1 ";
			Query query = this.getEntityManager( ).createQuery( deleteQuery ).setParameter( 1, subscription );
			query.executeUpdate( );
		}
		catch ( Exception e ) {
			this.storeException( e );
		}
	}

	private void removeObserverGrades( InepSubscription subscription )
	{
		try {
			String deleteQuery = "DELETE FROM InepObserverGrade o WHERE o.subscription = ?1 ";
			Query query = this.getEntityManager( ).createQuery( deleteQuery ).setParameter( 1, subscription );
			query.executeUpdate( );
		}
		catch ( Exception e ) {
			this.storeException( e );
		}

	}

	@Override
	public void setObserverInformation( PrincipalDTO auth, InepSubscription subscription, int[ ] grades )
	{

		Double grade = 0.0D;
		if ( auth == null || subscription == null || grades == null || grades.length != MAX_ORAL_GRADE ) {
			throw new InvalidParameterException( );
		}
		subscription = this.subscriptionSession.get( subscription.getId( ) );
		this.removeObserverGrades( subscription );
		int nIndex = 0;
		for ( int id : grades ) {
			InepObserverGrade element = new InepObserverGrade( subscription, ++nIndex, id );
			grade += id;
			this.getEntityManager( ).persist( element );
		}
		grade /= ( (double) MAX_ORAL_GRADE );
		/*
		 * Setup Interviewer Grade
		 */
		InepOralTest oralTest = this.oralTestSession.get( subscription );
		if ( oralTest == null ) {
			oralTest = this.oralTestSession.add( new InepOralTest( subscription ), false );
		}
		oralTest.setObserverGrade( BigDecimal.valueOf( grade ) );
		this.oralTestSession.setStatus( oralTest );
		subscription.setStatus( 2 );
	}

	@Override
	public List<InepMedia> lookupForName( PrincipalDTO auth, InepSubscription subscription, String mediaName )
	{
		return this.inepMediaSession.findByNamedQuery( InepMedia.LookupForMediaName, subscription.getEvent( ), mediaName );
	}

	@Override
	public void reset( PrincipalDTO auth, InepSubscription subscription )
	{
		subscription = this.subscriptionSession.get( subscription.getId( ) );
		if ( subscription != null ) {
			Query query = this.getEntityManager( ).createQuery( "Delete from InepOralTest o where o.subscription = ?1" ).setParameter( 1, subscription );
			query.executeUpdate( );
			this.removeElements( subscription );
			this.removeObserverGrades( subscription );
			this.inepMediaSession.removeAudio( subscription );
			subscription.setStatus( 1 );
		}
	}

	@Override
	public void setMissing( PrincipalDTO auth, InepSubscription subscription )
	{
		subscription = this.subscriptionSession.get( subscription.getId( ) );
		if ( subscription != null ) {
			this.reset( auth, subscription );
			subscription.setStatus( 3 );
		}
	}

	@Override
	public StationGradeDTO getStationGrade( PrincipalDTO auth, InepSubscription subscription )
	{
		StationGradeDTO dto = new StationGradeDTO( );

		List<InepObserverGrade> observerGrades = this.getObserverGrades( subscription );
		if ( !SysUtils.isEmpty( observerGrades ) ) {
			int nIndex = 0;
			for ( InepObserverGrade item : observerGrades ) {
				dto.getObserverGrade( )[ nIndex++ ] = item.getGrade( );
			}
		}
		InepOralTest oralTest = this.oralTestSession.get( subscription );
		if ( oralTest != null && oralTest.getInterviewGrade( ) != null ) {
			dto.setInterviewerGrade( oralTest.getInterviewGrade( ).intValue( ) );
		}
		List<InepElement> elements = this.getElements( subscription );
		if ( !SysUtils.isEmpty( elements ) ) {
			int nIndex = 0;
			for ( InepElement item : elements ) {
				dto.getElements( )[ nIndex++ ] = item.getId( ).getId( );
			}
		}
		return dto;
	}

	@SuppressWarnings( "unchecked" )
	private List<InepObserverGrade> getObserverGrades( InepSubscription subscription )
	{
		List<InepObserverGrade> observerGrades = null;

		try {
			Query query = this.getEntityManager( ).createQuery( "select o from InepObserverGrade o where o.subscription = ?1 order by o.id " );
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
			Query query = this.getEntityManager( ).createQuery( "select o from InepElement o where o.subscription = ?1 order by o.id " );
			query.setParameter( 1, subscription );
			items = query.getResultList( );
			return items;
		}
		catch ( Exception e ) {
			return items;
		}
	}
}
