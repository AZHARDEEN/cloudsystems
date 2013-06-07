package br.com.mcampos.ejb.inep.team;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.dto.inep.TaskGrade;
import br.com.mcampos.dto.inep.reporting.BaseSubscriptionDTO;
import br.com.mcampos.dto.inep.reporting.NotasConsenso;
import br.com.mcampos.dto.inep.reporting.NotasFinaisDTO;
import br.com.mcampos.dto.inep.reporting.NotasIndividuaisCorretorDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionSessionLocal;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.distribution.InepOralDistributionLocal;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepDistributionPK;
import br.com.mcampos.ejb.inep.entity.InepMedia;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTest;
import br.com.mcampos.ejb.inep.entity.InepTestPK;
import br.com.mcampos.ejb.inep.entity.SubscriptionGradeView;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.inep.test.InepTestSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginProperty;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginPropertySessionLocal;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class TeamSessionBean
 */
@Stateless( name = "TeamSession", mappedName = "TeamSession" )
@LocalBean
public class TeamSessionBean extends SimpleSessionBean<InepRevisor> implements TeamSession, TeamSessionLocal
{
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

	public TeamSessionBean( )
	{
	}

	@Override
	protected Class<InepRevisor> getEntityClass( )
	{
		return InepRevisor.class;
	}

	@Override
	public List<InepTask> getTasks( )
	{
		return (List<InepTask>) taskSession.getAll( );
	}

	@Override
	public List<InepTask> getTasks( InepPackage evt )
	{
		List<InepTask> list = taskSession.getAll( evt );
		return list;
	}

	@Override
	public List<InepRevisor> getTeam( InepTask task )
	{
		InepTask entityTask = taskSession.get( task.getId( ) );
		if ( entityTask == null ) {
			return Collections.emptyList( );
		}
		List<InepRevisor> team = findByNamedQuery( InepRevisor.getAllTeamByEventAndTask, entityTask );
		return team;
	}

	@Override
	public List<InepPackage> getPackages( InepRevisor rev )
	{
		List<InepPackage> packages = Collections.emptyList( );
		return packages;
	}

	@Override
	public InepRevisor getRevisor( InepPackage event, Collaborator auth )
	{
		return getRevisorSession( ).get( event, auth );
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
		return distributionSession.get( revisor, status );
	}

	@Override
	public InepDistribution getRevision( InepRevisor rev, InepDistribution t )
	{
		String ql = "from InepRevision o where o.id.packageId = ?1 " +
				"and o.id.taskId = ?2 and o.id.companyId = ?3 and o.id.collaboratorId = ?4 and o.id.testId = ?5";
		Query query = getEntityManager( ).createQuery( ql );
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
		return getEntityManager( ).find( DistributionStatus.class, status );
	}

	@Override
	public InepDistribution updateRevision( InepDistribution dist )
	{
		InepDistribution entity = getEntityManager( ).find( InepDistribution.class, dist.getId( ) );
		if ( entity != null ) {
			if ( entity.getStatus( ).getId( ).equals( DistributionStatus.statusDistributed ) == false
					&& entity.getRevisor( ).isCoordenador( ) == false ) {
				throw new RuntimeException( "Uma nota já foi atribuída para esta prova. Nota atribuida anteriormente foi: " + entity.getNota( ) );
			}
			dist = getEntityManager( ).merge( dist );
		}
		else {
			getEntityManager( ).persist( dist );
		}
		if ( dist.getRevisor( ).isCoordenador( ) == false ) {
			dist.setStatus( getStatus( DistributionStatus.statusRevised ) );
			if ( hasVariance( dist ) ) {
				variance( dist, getStatus( DistributionStatus.statusVariance ) );
			}
		}
		else {
			dist.setStatus( getStatus( DistributionStatus.statusFinalRevised ) );
			variance( dist, getStatus( DistributionStatus.statusFinalRevised ) );
			testSession.setGrade( dist.getTest( ), dist.getNota( ) <= 5 ? dist.getNota( ) : 0 );
		}
		return dist;
	}

	private void variance( InepDistribution entity, DistributionStatus status )
	{
		List<InepRevisor> coordinators;
		InepDistribution varianceDistribution;
		if ( sendToAllFromTask( ) == false ) {
			coordinators = revisorSession.findByNamedQuery( InepRevisor.getAllCoordinatorsToTask,
					entity.getRevisor( ).getTask( ) );
		}
		else {
			coordinators = revisorSession.findByNamedQuery( InepRevisor.getAllTeamByEventAndTask,
					entity.getRevisor( ).getTask( ) );
		}
		for ( InepRevisor otherRevisor : coordinators )
		{
			varianceDistribution = distributionSession.get( new InepDistributionPK( otherRevisor, entity.getTest( ) ) );
			if ( varianceDistribution == null ) {
				varianceDistribution = new InepDistribution( otherRevisor, entity.getTest( ) );
			}
			varianceDistribution.setStatus( status );
			varianceDistribution = distributionSession.merge( varianceDistribution );
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

		others = distributionSession.findByNamedQuery( InepDistribution.getAllFromTest, entity.getTest( ) );
		threshold = getTreshold( );
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
				other.setStatus( getStatus( DistributionStatus.statusVariance ) );
			}
			else {
				double grade = 0.0;
				int n = other.getNota( );
				if ( n > 5 )
					n = 0;
				grade += n;
				n = entity.getNota( );
				if ( n > 5 )
					n = 0;
				grade += n;
				grade /= 2.0;
				testSession.setGrade( other.getTest( ), grade );
			}
		}
		if ( bRet == true ) {
			entity.setStatus( getStatus( DistributionStatus.statusVariance ) );
		}
		return bRet;
	}

	private Boolean sendToAllFromTask( )
	{
		Boolean ret = getProperty( ).getBool( "inep.varianceSendToAll" );
		if ( ret == null ) {
			ret = true;
			String description;

			description = "Envia o teste com variancia para todos os corretores da tarefa (Senao envia somente coordenadores)";
			getProperty( ).setBool( "inep.varianceSendToAll", description, true );
		}
		return ret;
	}

	private Integer getTreshold( )
	{
		Integer threshold = getProperty( ).getInt( "inep.threshold" );
		if ( threshold == null ) {
			threshold = 1;
			getProperty( ).setInt( "inep.threshold", "Valor máximo admitido de discrepância", threshold );
		}
		return threshold;
	}

	@Override
	public List<InepDistribution> getOtherDistributions( InepTest test )
	{
		List<InepDistribution> others = distributionSession.findByNamedQuery( InepDistribution.getOtherDistribution, test );

		return others;
	}

	public CollaboratorSessionLocal getCollaboratorSession( )
	{
		return collaboratorSession;
	}

	public InepRevisorSessionLocal getRevisorSession( )
	{
		return revisorSession;
	}

	@Override
	public List<InepPackage> getEvents( Collaborator auth )
	{
		return taskSession.getEvents( auth );
	}

	@Override
	public byte[ ] getMedia( InepTest test )
	{
		byte[ ] obj = null;

		InepTest merged = testSession.get( test.getId( ) );
		if ( merged == null ) {
			return null;
		}
		for ( InepMedia inepMedia : merged.getSubscription( ).getMedias( ) ) {
			if ( inepMedia.getTask( ) == null )
				continue;
			if ( inepMedia.getTask( ).equals( merged.getTask( ).getId( ).getId( ) ) ) {
				obj = inepMedia.getMedia( ).getObject( );
				break;
			}
		}
		return obj;
	}

	@Override
	public InepTaskCounters getCounters( InepRevisor rev )
	{
		InepTaskCounters dto = new InepTaskCounters( );
		if ( rev != null ) {
			Query query;

			if ( rev.isCoordenador( ) ) {
				query = getEntityManager( ).createNamedQuery( InepDistribution.getCoordCounter );
				query.setParameter( 1, rev.getTask( ) );
			}
			else {
				query = getEntityManager( ).createNamedQuery( InepDistribution.getRevisorCounter );
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
			List<InepTest> tests = testSession.getTests( revisor );
			for ( InepTest test : tests )
			{
				if ( test.getSubscription( ).getId( ).getId( ).equals( lastSubscription ) == false )
				{
					analitico = new InepAnaliticoCorrecao( );
					lastSubscription = test.getSubscription( ).getId( ).getId( );
					analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
					list.add( analitico );
				}
				loadGrades( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
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
			List<InepTest> tests = testSession.getTests( task );
			for ( InepTest test : tests )
			{
				if ( test.getSubscription( ).getId( ).getId( ).equals( lastSubscription ) == false )
				{
					analitico = new InepAnaliticoCorrecao( );
					lastSubscription = test.getSubscription( ).getId( ).getId( );
					analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
					list.add( analitico );
				}
				loadGrades( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
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
	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepPackage event )
	{
		final long startTime = System.nanoTime( );
		List<InepAnaliticoCorrecao> list = new ArrayList<InepAnaliticoCorrecao>( );
		String lastSubscription = "";

		try {
			InepAnaliticoCorrecao analitico = null;
			List<InepTest> tests = testSession.getTests( event );
			for ( InepTest test : tests )
			{
				if ( test.getSubscription( ).getId( ).getId( ).equals( lastSubscription ) == false )
				{
					analitico = new InepAnaliticoCorrecao( );
					lastSubscription = test.getSubscription( ).getId( ).getId( );
					analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
					list.add( analitico );
				}
				loadGrades( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
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
		List<InepDistribution> distributions = distributionSession.get( s );
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
		List<InepDistribution> distributions = distributionSession.getVariance( s );
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
	public List<BaseSubscriptionDTO> report( InepPackage event, Integer report )
	{
		List<BaseSubscriptionDTO> list = Collections.emptyList( );
		switch ( report )
		{
		case 1:
			break;
		case 2:
			list = notasFinais( event );
			break;
		case 3:
			list = notaConsenso( event );
			break;
		case 4:
			list = notasIndividuais( event );
			break;
		}
		return list;
	}

	private List<BaseSubscriptionDTO> notasIndividuais( InepPackage event )
	{
		List<BaseSubscriptionDTO> list = new ArrayList<BaseSubscriptionDTO>( );
		List<InepDistribution> dists = distributionSession.getAllforReport( event );
		for ( InepDistribution item : dists )
		{
			NotasIndividuaisCorretorDTO dto = new NotasIndividuaisCorretorDTO( );
			dto.setCpf( getCpf( item.getRevisor( ).getCollaborator( ).getPerson( ) ) );
			dto.setNota( item.getNota( ) );
			dto.setSubscription( item.getTest( ).getSubscription( ).getId( ).getId( ) );
			dto.setTarefa( item.getTest( ).getTask( ).getId( ).getId( ) );
			list.add( dto );
		}
		return list;
	}

	@SuppressWarnings( "unchecked" )
	private List<BaseSubscriptionDTO> notasFinais( InepPackage event )
	{
		List<BaseSubscriptionDTO> list = new ArrayList<BaseSubscriptionDTO>( );
		List<SubscriptionGradeView> dists;
		Query query = getEntityManager( ).createNamedQuery( SubscriptionGradeView.getAllByEvent );
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

	private List<BaseSubscriptionDTO> notaConsenso( InepPackage event )
	{
		List<BaseSubscriptionDTO> list = new ArrayList<BaseSubscriptionDTO>( );
		List<InepDistribution> dists = distributionSession.getVarianceForReport( event );
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
			if ( doc.getType( ).getId( ).equals( UserDocument.typeCPF ) ) {
				cpf = doc.getCode( );
				break;
			}
		}
		return cpf;
	}

	@Override
	public List<InepAnaliticoCorrecao> getAnaliticoDivergencia( InepPackage event )
	{
		final long startTime = System.nanoTime( );
		List<InepAnaliticoCorrecao> list = new ArrayList<InepAnaliticoCorrecao>( );
		try {
			InepAnaliticoCorrecao analitico = null;
			List<InepTest> tests = testSession.getTestsWithVariance( event );
			for ( InepTest test : tests )
			{
				analitico = new InepAnaliticoCorrecao( );
				test.getSubscription( ).getId( ).getId( );
				analitico.setSubscritpion( test.getSubscription( ).getId( ).getId( ) );
				list.add( analitico );
				loadGradesWithVariance( test, analitico.getGrades( )[ test.getTask( ).getId( ).getId( ) - 1 ] );
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
	public List<Object[ ]> getWorkStatus( InepPackage event )
	{
		Query query = getEntityManager( ).createNamedQuery( InepDistribution.getWorkStatus );
		query.setParameter( 1, event );
		@SuppressWarnings( "unchecked" )
		List<Object[ ]> list = query.getResultList( );
		return list;
	}

	@Override
	public LoginProperty getProperty( Collaborator collaborator, String propertyName )
	{
		return propertySession.getProperty( collaborator, propertyName );
	}

	@Override
	public void setProperty( Collaborator collaborator, String propertyName, String Value )
	{
		propertySession.setProperty( collaborator, propertyName, Value );
	}

	@Override
	public LoginProperty remove( Collaborator collaborator, String propertyName )
	{
		return propertySession.remove( collaborator, propertyName );
	}

	@Override
	public List<InepSubscription> getSubscriptions( InepPackage event, String part )
	{
		return subscriptionSession.getAll( event, part );
	}

	@Override
	public List<InepDistribution> getDistribution( InepSubscription e )
	{
		return distributionSession.getAll( e );
	}

	@Override
	public byte[ ] getMedia( InepSubscription s, InepTask t )
	{
		InepTestPK key = new InepTestPK( );

		key.set( t, s );
		byte[ ] obj = null;

		InepTest merged = testSession.get( key );
		if ( merged == null ) {
			return null;
		}
		for ( InepMedia inepMedia : merged.getSubscription( ).getMedias( ) ) {
			if ( inepMedia.getTask( ) == null )
				continue;
			if ( inepMedia.getTask( ).equals( t.getId( ).getId( ) ) ) {
				obj = inepMedia.getMedia( ).getObject( );
				break;
			}
		}
		return obj;
	}

	@Override
	public List<InepRevisor> getOralTeam( InepPackage event )
	{
		return findByNamedQuery( InepRevisor.getAllOralTeamByEvent, event.getId( ).getCompanyId( ), event.getId( ).getId( ) );
	}

	@Override
	public List<InepTest> getTests( InepSubscription s )
	{
		List<InepTest> tests = (List<InepTest>) testSession.getAll( " t.subscription = ?1", null, s );
		return tests;
	}

	@Override
	public InepOralTest getOralTest( InepSubscription s )
	{
		return oralTestSession.get( s );
	}

	@Override
	public void resetTasks( InepSubscription s, List<InepTask> tasks )
	{
		InepSubscription merged = subscriptionSession.get( s.getId( ) );
		if ( merged == null )
			throw new InvalidParameterException( "Não existe a inscrição" );
		for ( InepTask task : tasks ) {
			InepTestPK key = new InepTestPK( task, merged );
			InepTest test = testSession.get( key );
			resetTest( test );
		}
		merged.setWrittenGrade( null );
		merged.setAgreementGrade( null );
		merged.setFinalGrade( null );
	}

	private void resetTest( InepTest test )
	{
		if ( test == null )
			return;
		List<InepDistribution> list = distributionSession.findByNamedQuery( InepDistribution.getAllFromTest, test );
		if ( SysUtils.isEmpty( list ) )
			return;
		for ( InepDistribution item : list ) {
			switch ( item.getStatus( ).getId( ) ) {
			case 2:
				item.setStatus( statusSession.get( DistributionStatus.statusDistributed ) );
				item.setNota( null );
				break;
			case 3:
				if ( item.getNota( ) == null ) {
					distributionSession.remove( item );
				}
				else {
					item.setStatus( statusSession.get( DistributionStatus.statusDistributed ) );
					item.setNota( null );
				}
				break;
			case 4:
				if ( item.getRevisor( ).isCoordenador( ).equals( true ) )
					distributionSession.remove( item );
				else {
					item.setStatus( statusSession.get( DistributionStatus.statusDistributed ) );
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
		InepSubscription merged = subscriptionSession.get( s.getId( ) );
		if ( merged == null )
			throw new InvalidParameterException( "Não existe a inscrição" );
		for ( InepMedia media : merged.getMedias( ) ) {
			if ( t1.getId( ).getId( ).equals( media.getTask( ) ) )
				m1 = media;
			if ( t2.getId( ).getId( ).equals( media.getTask( ) ) )
				m2 = media;
		}
		Integer aux;
		aux = m1.getTask( );
		m1.setTask( m2.getTask( ) );
		m2.setTask( aux );
	}

	@Override
	public List<Object[ ]> getSubscriptionStatus( InepPackage event )
	{
		String sql;

		sql = "select count(*) total, count(s.isc_written_grade_nm) done from inep.inep_subscription s where s.usr_id_in = "
				+ event.getId( ).getCompanyId( )
				+ " and s.pct_id_in = " + event.getId( ).getId( ) + " and s.isc_id_ch in ( select distinct isc_id_ch from inep.inep_test t " +
				"where t.usr_id_in = s.usr_id_in and t.pct_id_in = s.pct_id_in and t.isc_id_ch = s.isc_id_ch )";
		Query query = getEntityManager( ).createNativeQuery( sql );
		@SuppressWarnings( "unchecked" )
		List<Object[ ]> result = query.getResultList( );
		return result;
	}

	@Override
	public List<InepOralDistribution> getOralDistributions( InepOralTest s )
	{
		return oralDistributionSession.getOralDistributions( s );
	}
}
