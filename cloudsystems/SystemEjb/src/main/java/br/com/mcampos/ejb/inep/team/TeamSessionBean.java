package br.com.mcampos.ejb.inep.team;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.dto.inep.StationDTO;
import br.com.mcampos.dto.inep.TaskGrade;
import br.com.mcampos.dto.inep.reporting.BaseSubscriptionDTO;
import br.com.mcampos.dto.inep.reporting.NotasConsenso;
import br.com.mcampos.dto.inep.reporting.NotasFinaisDTO;
import br.com.mcampos.dto.inep.reporting.NotasIndividuaisCorretorDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.InepStationSessionLocal;
import br.com.mcampos.ejb.inep.distribution.DistributionSessionLocal;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.distribution.InepOralDistributionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.inep.test.InepTestSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginPropertySessionLocal;
import br.com.mcampos.jpa.inep.DistributionStatus;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepDistributionPK;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.inep.InepTestPK;
import br.com.mcampos.jpa.inep.SubscriptionGradeView;
import br.com.mcampos.jpa.security.LoginProperty;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class TeamSessionBean
 */
@Stateless( name = "TeamSession", mappedName = "TeamSession" )
@LocalBean
public class TeamSessionBean extends SimpleSessionBean<InepRevisor> implements TeamSession, TeamSessionLocal
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6750178113226658138L;

	private static final Logger logger = LoggerFactory.getLogger( TeamSessionBean.class.getSimpleName( ) );

	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	private DistributionSessionLocal distributionSession;

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@EJB
	private InepRevisorSessionLocal revisorSession;

	@EJB
	private InepTestSessionLocal testSession;

	@EJB
	private LoginPropertySessionLocal propertySession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionSession;

	@EJB
	private InepOralTestSessionLocal oralTestSession;

	@EJB
	private DistributionStatusSessionLocal statusSession;

	@EJB
	private InepOralDistributionLocal oralDistributionSession;

	@EJB
	private InepStationSessionLocal stationSession;

	@EJB
	private InepPackageSessionLocal eventSession;

	public TeamSessionBean( )
	{
	}

	@Override
	protected Class<InepRevisor> getEntityClass( )
	{
		return InepRevisor.class;
	}

	@Override
	public List<InepTask> getTasks( PrincipalDTO auth )
	{
		return (List<InepTask>) this.taskSession.getAll( auth );
	}

	@Override
	public List<InepTask> getTasks( InepEvent evt )
	{
		List<InepTask> list = this.taskSession.getAll( evt );
		return list;
	}

	@Override
	public List<InepRevisor> getTeam( InepTask task )
	{
		InepTask entityTask = this.taskSession.get( task.getId( ) );
		if ( entityTask == null ) {
			return Collections.emptyList( );
		}
		List<InepRevisor> team = this.findByNamedQuery( InepRevisor.getAllTeamByEventAndTask, entityTask );
		return team;
	}

	@Override
	public List<InepEvent> getPackages( InepRevisor rev )
	{
		List<InepEvent> packages = Collections.emptyList( );
		return packages;
	}

	@Override
	public InepRevisor getRevisor( InepEvent event, PrincipalDTO auth )
	{
		if ( auth != null && event != null ) {
			return this.getRevisorSession( ).get( event, auth );
		}
		else {
			return null;
		}
	}

	@Override
	public List<InepDistribution> getTests( InepRevisor revisor, Integer status )
	{
		if ( revisor == null ) {
			return Collections.emptyList( );
		}
		if ( status == null || status.intValue( ) < 1 ) {
			status = 1;
		}
		return this.distributionSession.get( revisor, status );
	}

	@Override
	public InepDistribution getRevision( InepRevisor rev, InepDistribution t )
	{
		String ql = "from InepRevision o where o.id.packageId = ?1 " +
				"and o.id.taskId = ?2 and o.id.companyId = ?3 and o.id.collaboratorId = ?4 and o.id.testId = ?5";
		Query query = this.getEntityManager( ).createQuery( ql );
		query.setParameter( 1, t.getId( ).getEventId( ) );
		query.setParameter( 2, t.getId( ).getTaskId( ) );
		query.setParameter( 3, t.getId( ).getCompanyId( ) );
		query.setParameter( 4, rev.getId( ).getSequence( ) );
		try {
			return (InepDistribution) query.getSingleResult( );
		}
		catch ( NoResultException e ) {
			e = null;
			return null;
		}

	}

	private DistributionStatus getStatus( Integer status )
	{
		return this.getEntityManager( ).find( DistributionStatus.class, status );
	}

	@Override
	public InepDistribution updateRevision( InepDistribution dist )
	{
		InepDistribution entity = this.getEntityManager( ).find( InepDistribution.class, dist.getId( ) );
		if ( entity != null ) {
			if ( entity.getStatus( ).getId( ).equals( DistributionStatus.statusDistributed ) == false
					&& entity.getRevisor( ).isCoordenador( ) == false ) {
				throw new RuntimeException( "Uma nota já foi atribuída para esta prova. Nota atribuida anteriormente foi: " + entity.getNota( ) );
			}
			dist = this.getEntityManager( ).merge( dist );
		}
		else {
			this.getEntityManager( ).persist( dist );
		}
		if ( dist.getRevisor( ).isCoordenador( ) == false ) {
			dist.setStatus( this.getStatus( DistributionStatus.statusRevised ) );
			if ( this.hasVariance( dist ) ) {
				this.variance( dist, this.getStatus( DistributionStatus.statusVariance ) );
			}
		}
		else {
			dist.setStatus( this.getStatus( DistributionStatus.statusFinalRevised ) );
			this.variance( dist, this.getStatus( DistributionStatus.statusFinalRevised ) );
			this.testSession.setGrade( dist.getTest( ), dist.getNota( ) <= 5 ? dist.getNota( ) : 0 );
		}
		return dist;
	}

	private void variance( InepDistribution entity, DistributionStatus status )
	{
		List<InepRevisor> coordinators;
		InepDistribution varianceDistribution;
		if ( this.sendToAllFromTask( ) == false ) {
			coordinators = this.revisorSession.findByNamedQuery( InepRevisor.getAllCoordinatorsToTask,
					entity.getRevisor( ).getTask( ) );
		}
		else {
			coordinators = this.revisorSession.findByNamedQuery( InepRevisor.getAllTeamByEventAndTask,
					entity.getRevisor( ).getTask( ) );
		}
		for ( InepRevisor otherRevisor : coordinators )
		{
			varianceDistribution = this.distributionSession.get( new InepDistributionPK( otherRevisor, entity.getTest( ) ) );
			if ( varianceDistribution == null ) {
				varianceDistribution = new InepDistribution( otherRevisor, entity.getTest( ) );
			}
			varianceDistribution.setStatus( status );
			varianceDistribution = this.distributionSession.merge( varianceDistribution );
		}
	}

	private boolean hasVariance( InepDistribution entity )
	{

		boolean bRet = false;
		if ( entity == null || entity.getRevisor( ).isCoordenador( ) ) {
			return bRet;
		}
		List<InepDistribution> others;
		Integer threshold;
		int variance;

		others = this.distributionSession.findByNamedQuery( InepDistribution.getAllFromTest, entity.getTest( ) );
		threshold = this.getTreshold( );
		for ( InepDistribution other : others ) {
			if ( other.equals( entity ) || other.getNota( ) == null ) {
				continue;
			}
			variance = other.getNota( ).intValue( ) - entity.getNota( ).intValue( );
			if ( variance < 0 ) {
				variance *= -1;
			}
			if ( variance > threshold || ( other.getNota( ) > 5 && entity.getNota( ).equals( other.getNota( ) ) == false ) ) {
				bRet = true;
				other.setStatus( this.getStatus( DistributionStatus.statusVariance ) );
			}
			else {
				double grade = 0.0;
				int n = other.getNota( );
				if ( n > 5 ) {
					n = 0;
				}
				grade += n;
				n = entity.getNota( );
				if ( n > 5 ) {
					n = 0;
				}
				grade += n;
				grade /= 2.0;
				this.testSession.setGrade( other.getTest( ), grade );
			}
		}
		if ( bRet == true ) {
			entity.setStatus( this.getStatus( DistributionStatus.statusVariance ) );
		}
		return bRet;
	}

	private Boolean sendToAllFromTask( )
	{
		Boolean ret = this.getProperty( ).getBool( "inep.varianceSendToAll" );
		if ( ret == null ) {
			ret = true;
			String description;

			description = "Envia o teste com variancia para todos os corretores da tarefa (Senao envia somente coordenadores)";
			this.getProperty( ).setBool( "inep.varianceSendToAll", description, true );
		}
		return ret;
	}

	private Integer getTreshold( )
	{
		Integer threshold = this.getProperty( ).getInt( "inep.threshold" );
		if ( threshold == null ) {
			threshold = 1;
			this.getProperty( ).setInt( "inep.threshold", "Valor máximo admitido de discrepância", threshold );
		}
		return threshold;
	}

	@Override
	public List<InepDistribution> getOtherDistributions( InepTest test )
	{
		List<InepDistribution> others = this.distributionSession.findByNamedQuery( InepDistribution.getOtherDistribution, test );

		return others;
	}

	public CollaboratorSessionLocal getCollaboratorSession( )
	{
		return this.collaboratorSession;
	}

	public InepRevisorSessionLocal getRevisorSession( )
	{
		return this.revisorSession;
	}

	@Override
	public List<InepEvent> getEvents( PrincipalDTO auth )
	{
		if ( auth != null ) {
			return this.taskSession.getEvents( auth );
		}
		else {
			return null;
		}
	}

	public List<InepEvent> getAvailableEvents( PrincipalDTO auth )
	{
		if ( auth != null ) {
			return this.eventSession.getAvailable( );
		}
		else {
			return null;
		}
	}

	@Override
	public byte[ ] getMedia( InepDistribution item )
	{
		byte[ ] obj = null;

		InepDistribution merged = this.distributionSession.get( item.getId( ) );
		if ( merged == null || merged.getTest( ).getTask( ) == null ) {
			return null;
		}
		for ( InepMedia inepMedia : merged.getTest( ).getSubscription( ).getMedias( ) ) {
			if ( inepMedia.getTask( ) == null ) {
				continue;
			}
			if ( inepMedia.getTask( ).equals( merged.getTest( ).getTask( ).getId( ).getId( ) ) ) {
				obj = inepMedia.getMedia( ).getObject( );
				break;
			}
		}
		merged.setStartDate( new Date( ) );
		return obj;
	}

	@Override
	public InepTaskCounters getCounters( InepRevisor rev )
	{
		InepTaskCounters dto = new InepTaskCounters( );
		if ( rev != null ) {
			Query query;

			if ( rev.isCoordenador( ) ) {
				query = this.getEntityManager( ).createNamedQuery( InepDistribution.getCoordCounter );
				query.setParameter( 1, rev.getTask( ) );
			}
			else {
				query = this.getEntityManager( ).createNamedQuery( InepDistribution.getRevisorCounter );
				query.setParameter( 1, rev );
			}
			try {
				@SuppressWarnings( "unchecked" )
				List<Object[ ]> values = query.getResultList( );
				if ( values != null ) {
					for ( Object[ ] obj : values )
					{
						int status = ( (Integer) obj[ 0 ] );
						Long count = ( (Long) obj[ 1 ] );
						switch ( status )
						{
						case 1:
							dto.setTasks( count.intValue( ) );
							break;
						case 2:
							dto.setRevised( count.intValue( ) );
							break;
						case 4:
						case 3:
							dto.setVariance( dto.getVariance( ) + count.intValue( ) );
							break;
						}
					}
				}
			}
			catch ( Exception e )
			{
				e = null;
			}
		}
		return dto;
	}

	@Override
	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepRevisor revisor )
	{
		final long startTime = System.nanoTime( );
		List<InepAnaliticoCorrecao> list = new ArrayList<InepAnaliticoCorrecao>( );
		String lastSubscription = "";

		try {
			InepAnaliticoCorrecao analitico = null;
			List<InepTest> tests = this.testSession.getTests( revisor );
			for ( InepTest test : tests )
			{
				if ( test.getSubscription( ).getId( ).getId( ).equals( lastSubscription ) == false )
				{
					analitico = new InepAnaliticoCorrecao( );
					lastSubscription = test.getSubscription( ).getId( ).getId( );
					analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
					list.add( analitico );
				}
				this.loadGrades( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
			}
		}
		catch ( Exception e )
		{
			e = null;
		}
		final long endTime = System.nanoTime( ) - startTime;
		logger.info( "Duração: " + endTime / 100.0 );
		return list;
	}

	@Override
	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepTask task )
	{
		final long startTime = System.nanoTime( );
		List<InepAnaliticoCorrecao> list = new ArrayList<InepAnaliticoCorrecao>( );
		String lastSubscription = "";

		try {
			InepAnaliticoCorrecao analitico = null;
			List<InepTest> tests = this.testSession.getTests( task );
			for ( InepTest test : tests )
			{
				if ( test.getSubscription( ).getId( ).getId( ).equals( lastSubscription ) == false )
				{
					analitico = new InepAnaliticoCorrecao( );
					lastSubscription = test.getSubscription( ).getId( ).getId( );
					analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
					list.add( analitico );
				}
				this.loadGrades( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
			}
		}
		catch ( Exception e )
		{
			e = null;
		}
		final long endTime = System.nanoTime( ) - startTime;
		logger.info( "Duração: " + endTime / 100.0 );
		return list;
	}

	@Override
	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepEvent event )
	{
		final long startTime = System.nanoTime( );
		List<InepAnaliticoCorrecao> list = new ArrayList<InepAnaliticoCorrecao>( );
		String lastSubscription = "";

		try {
			InepAnaliticoCorrecao analitico = null;
			List<InepTest> tests = this.testSession.getTests( event );
			for ( InepTest test : tests )
			{
				if ( test.getSubscription( ).getId( ).getId( ).equals( lastSubscription ) == false )
				{
					analitico = new InepAnaliticoCorrecao( );
					lastSubscription = test.getSubscription( ).getId( ).getId( );
					analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
					list.add( analitico );
				}
				this.loadGrades( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
			}
		}
		catch ( Exception e )
		{
			e = null;
		}
		final long endTime = System.nanoTime( ) - startTime;
		logger.info( "Duração: " + endTime / 100.0 );
		return list;
	}

	private void loadGrades( InepTest s, TaskGrade grade )
	{
		if ( s == null || grade == null ) {
			return;
		}
		List<InepDistribution> distributions = this.distributionSession.get( s );
		for ( InepDistribution item : distributions )
		{
			if ( item.getNota( ) == null ) {
				continue;
			}
			if ( item.getRevisor( ).isCoordenador( ) == false ) {
				if ( grade.getFirstGrade( ) == null ) {
					grade.setFirstGrade( item.getNota( ) );
				}
				else {
					grade.setSecondGrade( item.getNota( ) );
				}
			}
			else {
				grade.setThirdGrade( item.getNota( ) );
			}
		}
	}

	private void loadGradesWithVariance( InepTest s, TaskGrade grade )
	{
		if ( s == null || grade == null ) {
			return;
		}
		List<InepDistribution> distributions = this.distributionSession.getVariance( s );
		for ( InepDistribution item : distributions )
		{
			if ( item.getNota( ) == null ) {
				continue;
			}
			if ( item.getRevisor( ).isCoordenador( ) == false ) {
				if ( grade.getFirstGrade( ) == null ) {
					grade.setFirstGrade( item.getNota( ) );
				}
				else {
					grade.setSecondGrade( item.getNota( ) );
				}
			}
			else {
				grade.setThirdGrade( item.getNota( ) );
			}
		}
	}

	@Override
	public List<BaseSubscriptionDTO> report( InepEvent event, Integer report )
	{
		List<BaseSubscriptionDTO> list = Collections.emptyList( );
		switch ( report )
		{
		case 1:
			break;
		case 2:
			list = this.notasFinais( event );
			break;
		case 3:
			list = this.notaConsenso( event );
			break;
		case 4:
			list = this.notasIndividuais( event );
			break;
		}
		return list;
	}

	private List<BaseSubscriptionDTO> notasIndividuais( InepEvent event )
	{
		List<BaseSubscriptionDTO> list = new ArrayList<BaseSubscriptionDTO>( );
		List<InepDistribution> dists = this.distributionSession.getAllforReport( event );
		for ( InepDistribution item : dists )
		{
			NotasIndividuaisCorretorDTO dto = new NotasIndividuaisCorretorDTO( );
			dto.setCpf( this.getCpf( item.getRevisor( ).getCollaborator( ).getPerson( ) ) );
			dto.setNota( item.getNota( ) );
			dto.setSubscription( item.getTest( ).getSubscription( ).getId( ).getId( ) );
			dto.setTarefa( item.getTest( ).getTask( ).getId( ).getId( ) );
			list.add( dto );
		}
		return list;
	}

	@SuppressWarnings( "unchecked" )
	private List<BaseSubscriptionDTO> notasFinais( InepEvent event )
	{
		List<BaseSubscriptionDTO> list = new ArrayList<BaseSubscriptionDTO>( );
		List<SubscriptionGradeView> dists;
		Query query = this.getEntityManager( ).createNamedQuery( SubscriptionGradeView.getAllByEvent );
		query.setParameter( 1, event.getId( ).getCompanyId( ) );
		query.setParameter( 2, event.getId( ).getId( ) );
		try {
			dists = query.getResultList( );
			for ( SubscriptionGradeView item : dists )
			{
				NotasFinaisDTO dto = new NotasFinaisDTO( );
				dto.setSubscription( item.getId( ).getId( ) );
				dto.setMediaTarefa1( item.getTarefa1( ) );
				dto.setMediaTarefa2( item.getTarefa2( ) );
				dto.setMediaTarefa3( item.getTarefa3( ) );
				dto.setMediaTarefa4( item.getTarefa4( ) );
				list.add( dto );
			}
		}
		catch ( Exception e )
		{
			e = null;
		}
		return list;
	}

	private List<BaseSubscriptionDTO> notaConsenso( InepEvent event )
	{
		List<BaseSubscriptionDTO> list = new ArrayList<BaseSubscriptionDTO>( );
		List<InepDistribution> dists = this.distributionSession.getVarianceForReport( event );
		for ( InepDistribution item : dists )
		{
			NotasConsenso dto = new NotasConsenso( );
			dto.setNotaConsenso( item.getNota( ) );
			dto.setSubscription( item.getTest( ).getSubscription( ).getId( ).getId( ) );
			dto.setTarefa( item.getTest( ).getTask( ).getId( ).getId( ) );
			list.add( dto );
		}
		return list;
	}

	private String getCpf( Person person )
	{
		String cpf = "";

		for ( UserDocument doc : person.getDocuments( ) )
		{
			if ( doc.getType( ).getId( ).equals( UserDocument.CPF ) ) {
				cpf = doc.getCode( );
				break;
			}
		}
		return cpf;
	}

	@Override
	public List<InepAnaliticoCorrecao> getAnaliticoDivergencia( InepEvent event )
	{
		final long startTime = System.nanoTime( );
		List<InepAnaliticoCorrecao> list = new ArrayList<InepAnaliticoCorrecao>( );
		try {
			InepAnaliticoCorrecao analitico = null;
			List<InepTest> tests = this.testSession.getTestsWithVariance( event );
			for ( InepTest test : tests )
			{
				analitico = new InepAnaliticoCorrecao( );
				test.getSubscription( ).getId( ).getId( );
				analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
				list.add( analitico );
				this.loadGradesWithVariance( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
			}
		}
		catch ( Exception e )
		{
			e = null;
		}
		final long endTime = System.nanoTime( ) - startTime;
		logger.info( "Duração: " + endTime / 100.0 );
		return list;
	}

	@Override
	public List<InepAnaliticoCorrecao> getAnaliticoDivergencia( InepTask task )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[ ]> getWorkStatus( InepEvent event )
	{
		Query query = this.getEntityManager( ).createNamedQuery( InepDistribution.getWorkStatus );
		query.setParameter( 1, event );
		@SuppressWarnings( "unchecked" )
		List<Object[ ]> list = query.getResultList( );
		return list;
	}

	@Override
	public LoginProperty getProperty( PrincipalDTO collaborator, String propertyName )
	{
		return this.propertySession.getProperty( collaborator, propertyName );
	}

	@Override
	public void setProperty( PrincipalDTO collaborator, String propertyName, String Value )
	{
		this.propertySession.setProperty( collaborator, propertyName, Value );
	}

	@Override
	public LoginProperty remove( PrincipalDTO collaborator, String propertyName )
	{
		return this.propertySession.remove( collaborator, propertyName );
	}

	@Override
	public List<InepSubscription> getSubscriptions( PrincipalDTO collaborator, InepEvent event, String part )
	{
		return this.subscriptionSession.getAll( collaborator, event, part );
	}

	@Override
	public List<InepDistribution> getDistribution( InepSubscription e )
	{
		return this.distributionSession.getAll( e );
	}

	@Override
	public byte[ ] getMedia( InepSubscription s, InepTask t )
	{
		InepTestPK key = new InepTestPK( );

		key.set( t, s );
		byte[ ] obj = null;

		InepTest merged = this.testSession.get( key );
		if ( merged == null ) {
			return null;
		}
		for ( InepMedia inepMedia : merged.getSubscription( ).getMedias( ) ) {
			if ( inepMedia.getTask( ) == null ) {
				continue;
			}
			if ( inepMedia.getTask( ).equals( t.getId( ).getId( ) ) ) {
				obj = inepMedia.getMedia( ).getObject( );
				break;
			}
		}
		return obj;
	}

	@Override
	public List<InepRevisor> getOralTeam( InepEvent event )
	{
		return this.findByNamedQuery( InepRevisor.getAllOralTeamByEvent, event.getId( ).getCompanyId( ), event.getId( ).getId( ) );
	}

	@Override
	public List<InepTest> getTests( PrincipalDTO auth, InepSubscription s )
	{
		List<InepTest> tests = (List<InepTest>) this.testSession.getAll( auth, " t.subscription = ?1", null, s );
		return tests;
	}

	@Override
	public InepOralTest getOralTest( InepSubscription s )
	{
		return this.oralTestSession.get( s );
	}

	@Override
	public void resetTasks( PrincipalDTO auth, InepSubscription s, List<InepTask> tasks )
	{
		InepSubscription merged = this.subscriptionSession.get( s.getId( ) );
		if ( merged == null ) {
			throw new InvalidParameterException( "Não existe a inscrição" );
		}
		for ( InepTask task : tasks ) {
			InepTestPK key = new InepTestPK( task, merged );
			InepTest test = this.testSession.get( key );
			this.resetTest( auth, test );
		}
		merged.setWrittenGrade( null );
		merged.setAgreementGrade( null );
		merged.setFinalGrade( null );
	}

	private void resetTest( PrincipalDTO auth, InepTest test )
	{
		if ( test == null ) {
			return;
		}
		List<InepDistribution> list = this.distributionSession.findByNamedQuery( InepDistribution.getAllFromTest, test );
		if ( SysUtils.isEmpty( list ) ) {
			return;
		}
		for ( InepDistribution item : list ) {
			switch ( item.getStatus( ).getId( ) ) {
			case 2:
				item.setStatus( this.statusSession.get( DistributionStatus.statusDistributed ) );
				item.setNota( null );
				break;
			case 3:
				if ( item.getNota( ) == null ) {
					this.distributionSession.remove( auth, item.getId( ) );
				}
				else {
					item.setStatus( this.statusSession.get( DistributionStatus.statusDistributed ) );
					item.setNota( null );
				}
				break;
			case 4:
				if ( item.getRevisor( ).isCoordenador( ).equals( true ) ) {
					this.distributionSession.remove( auth, item.getId( ) );
				}
				else {
					item.setStatus( this.statusSession.get( DistributionStatus.statusDistributed ) );
					item.setNota( null );
				}
				break;
			}
		}
		test.setGrade( null );
	}

	@Override
	public void swapTasks( InepSubscription s, InepTask t1, InepTask t2 )
	{
		InepMedia m1 = null, m2 = null;
		InepSubscription merged = this.subscriptionSession.get( s.getId( ) );
		if ( merged == null ) {
			throw new InvalidParameterException( "Não existe a inscrição" );
		}
		for ( InepMedia media : merged.getMedias( ) ) {
			if ( t1.getId( ).getId( ).equals( media.getTask( ) ) ) {
				m1 = media;
			}
			if ( t2.getId( ).getId( ).equals( media.getTask( ) ) ) {
				m2 = media;
			}
		}
		Integer aux;
		aux = m1.getTask( );
		m1.setTask( m2.getTask( ) );
		m2.setTask( aux );
	}

	@Override
	public List<Object[ ]> getSubscriptionStatus( InepEvent event )
	{
		String sql;

		sql = "select count(*) total, count(s.isc_written_grade_nm) done from inep.inep_subscription s where s.usr_id_in = "
				+ event.getId( ).getCompanyId( )
				+ " and s.pct_id_in = " + event.getId( ).getId( ) + " and s.isc_id_ch in ( select distinct isc_id_ch from inep.inep_test t " +
				"where t.usr_id_in = s.usr_id_in and t.pct_id_in = s.pct_id_in and t.isc_id_ch = s.isc_id_ch )";
		Query query = this.getEntityManager( ).createNativeQuery( sql );
		@SuppressWarnings( "unchecked" )
		List<Object[ ]> result = query.getResultList( );
		return result;
	}

	@Override
	public List<InepOralDistribution> getOralDistributions( InepOralTest s )
	{
		return this.oralDistributionSession.getOralDistributions( s );
	}

	@Override
	public List<StationDTO> getStations( PrincipalDTO auth, InepEvent evt )
	{
		return this.stationSession.getAll( evt );
	}

}
