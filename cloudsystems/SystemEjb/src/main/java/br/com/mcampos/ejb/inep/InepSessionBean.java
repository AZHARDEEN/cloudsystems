package br.com.mcampos.ejb.inep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.CorretorDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionSessionLocal;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.inep.team.TeamSessionLocal;
import br.com.mcampos.ejb.inep.test.InepTestSessionLocal;
import br.com.mcampos.ejb.security.LoginSessionLocal;
import br.com.mcampos.ejb.security.UserStatusSessionLocal;
import br.com.mcampos.ejb.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.type.CollaboratorTypeSessionBeanLocal;
import br.com.mcampos.ejb.user.document.type.DocumentTypeSessionLocal;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.jpa.inep.DistributionStatus;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepDistributionPK;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepEventPK;
import br.com.mcampos.jpa.inep.InepGoldenTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTaskPK;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.inep.RevisorType;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.jpa.security.UserStatus;
import br.com.mcampos.jpa.user.Collaborator;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class InepSessionBean
 */
@Stateless( name = "InepSession", mappedName = "InepSession" )
@LocalBean
public class InepSessionBean extends SimpleSessionBean<InepTask> implements InepSession, InepSessionLocal
{
	@EJB
	private CompanySessionLocal companySession;
	@EJB
	private InepPackageSessionLocal packageSession;
	@EJB
	private PersonSessionLocal personSession;
	@EJB
	private DocumentTypeSessionLocal documentTypeSession;
	@EJB
	private LoginSessionLocal loginSession;
	@EJB
	private UserStatusSessionLocal userStatusSession;
	@EJB
	private CollaboratorSessionLocal collaboratorSession;
	@EJB
	private CollaboratorTypeSessionBeanLocal collaboratorTypeSession;
	@EJB
	private RoleSessionLocal roleSession;
	@EJB
	private InepRevisorSessionLocal revisorSession;
	@EJB
	private InepPackageSessionLocal eventSession;
	@EJB
	private InepTaskSessionLocal taskSession;
	@EJB
	private DistributionStatusSessionLocal distributionStatusSession;
	@EJB
	private TeamSessionLocal teamSession;
	@EJB
	private InepTestSessionLocal testSession;
	@EJB
	private DistributionSessionLocal distributionSession;

	private static final Logger logger = LoggerFactory.getLogger( InepSessionBean.class.getSimpleName( ) );

	@Override
	protected Class<InepTask> getEntityClass( )
	{
		return InepTask.class;
	}

	@Override
	public boolean loadCorretore( CorretorDTO dto )
	{
		if ( dto == null || dto.getEvento( ) <= 0 || dto.getUser( ) <= 0 ) {
			logger.warn( "Dto is null" );
			return false;
		}
		Company company = this.companySession.get( dto.getUser( ) );
		if ( company == null ) {
			logger.warn( "Company is null" );
			return false;
		}
		InepEvent event = this.packageSession.get( new InepEventPK( company, dto.getEvento( ) ) );
		if ( event == null ) {
			logger.warn( "Event is null" );
			return false;
		}
		Person person = this.getPerson( dto );
		Login login = this.getLogin( person );
		Collaborator col = this.getCollaborator( login, company, dto );
		logger.debug( "The task is done!!!!! " );
		logger.debug( "Name...........: " + col.getPerson( ).getName( ) );
		logger.debug( "Id.............: " + col.getPerson( ).getId( ) );
		logger.debug( "Collaborator ID: " + col.getId( ).getSequence( ) );
		return this.getRevisor( col, dto ) != null ? true : false;
	}

	private InepRevisor getRevisor( Collaborator col, CorretorDTO dto )
	{
		InepEventPK key = new InepEventPK( col.getCompany( ), dto.getEvento( ) );
		InepEvent event = this.eventSession.get( key );
		if ( event == null ) {
			return null;
		}
		InepRevisor rev = this.revisorSession.get( event, col );
		if ( rev == null ) {
			rev = new InepRevisor( );
			rev.setCollaborator( col );
			rev.setCoordenador( dto.getCoordenador( ) );
			InepTask task = this.taskSession.get( new InepTaskPK( event, dto.getTarefa( ) ) );
			if ( task == null ) {
				return null;
			}
			rev.setTask( task );
			rev = this.revisorSession.merge( rev );
		}
		return rev;
	}

	private Collaborator getCollaborator( Login login, Company company, CorretorDTO dto )
	{
		Collaborator collaborator = this.collaboratorSession.find( new PrincipalDTO( company.getId( ), login.getId( ), null ) );
		if ( collaborator == null ) {
			logger.debug( "Collaborator does not exists: " + login.getPerson( ).getName( ) );
			collaborator = new Collaborator( );
			collaborator.setCollaboratorType( this.collaboratorTypeSession.get( company.getId( ), 1 ) );
			collaborator.setCompany( company );
			collaborator.setPerson( login.getPerson( ) );
			collaborator.setCpsIdIn( 5 );
			collaborator.setFromDate( new Date( ) );
			collaborator = this.collaboratorSession.merge( collaborator );
		}
		logger.debug( "Adding roles for collaborator: " + collaborator.getId( ).getSequence( ) );
		collaborator.add( this.roleSession.get( 4 ) );
		if ( dto.getCoordenador( ) ) {
			collaborator.add( this.roleSession.get( 20 ) );
		}
		else {
			collaborator.add( this.roleSession.get( 18 ) );
		}
		return collaborator;
	}

	private Login getLogin( Person person )
	{
		Login login = this.loginSession.get( person.getId( ) );
		if ( login == null ) {
			logger.debug( "Creating Login for person: " + person.getName( ) );
			this.loginSession.add( person, "987654" );
			login = this.loginSession.get( person.getId( ) );
		}
		logger.debug( "Resetting login parameters for login: " + login.getId( ) );
		login.setStatus( this.userStatusSession.get( UserStatus.statusOk ) );
		login.setTryCount( 0 );
		Calendar now = Calendar.getInstance( );
		now.add( Calendar.DATE, 60 );
		login.setExpDate( now.getTime( ) );
		return login;
	}

	private Person getPerson( CorretorDTO dto )
	{
		Person person = this.personSession.getByDocument( dto.getCpf( ) );
		if ( person == null ) {
			logger.debug( "There is no person for document" + dto.getCpf( ) );
			person = this.personSession.getByDocument( dto.getEmail( ) );
			if ( person == null ) {
				logger.debug( "There is no person for document" + dto.getEmail( ) );
				person = this.insertPerson( dto );
				logger.debug( dto.getNome( ) + " is created" );
			}
			else {
				UserDocument doc = new UserDocument( dto.getCpf( ), this.documentTypeSession.get( UserDocument.CPF ) );
				person.add( doc );
			}
		}
		return person;
	}

	private Person insertPerson( CorretorDTO dto )
	{
		Person person = new Person( );

		logger.debug( "Creating Person" + dto.getNome( ) );
		UserDocument doc = new UserDocument( dto.getCpf( ), this.documentTypeSession.get( UserDocument.CPF ) );
		person.add( doc );
		doc = new UserDocument( dto.getEmail( ), this.documentTypeSession.get( UserDocument.EMAIL ) );
		person.add( doc );
		person.setName( dto.getNome( ) );
		return this.personSession.merge( person );
	}

	@Override
	public void distribute( InepTask task )
	{
		int x = 0;
		int nIndex = 1;
		InepDistribution entity;
		DistributionStatus status = this.distributionStatusSession.get( new Integer( 1 ) );
		List<InepRevisor> team = this.teamSession.findByNamedQuery( InepRevisor.getAllRevisorByEventAndTask, task );
		ArrayList<InepRevisor> pairs = new ArrayList<InepRevisor>( team );
		ArrayList<InepRevisor> assigneds = new ArrayList<InepRevisor>( team.size( ) );
		logger.info( "Getting tests....." );
		List<InepTest> tests = this.testSession.findByNamedQuery( InepTest.getAllEventTasks, task );
		logger.info( "Done..." );
		InepRevisor current;
		Boolean mustAdd = false;

		for ( InepTest test : tests ) {
			if ( x >= team.size( ) ) {
				x = 0;
			}
			current = team.get( x );
			if ( pairs.contains( current ) ) {
				pairs.remove( current );
				mustAdd = true;
				if ( pairs.size( ) == 0 ) {
					pairs.addAll( assigneds );
					assigneds.clear( );
					if ( pairs.contains( current ) ) {
						pairs.remove( current );
					}
				}
			}

			entity = new InepDistribution( );
			entity.setStatus( status );
			entity.setRevisor( current );
			entity.setTest( test );
			this.distributionSession.merge( entity );

			InepRevisor pair = this.getPair( pairs, assigneds );
			entity = new InepDistribution( );
			entity.setStatus( status );
			entity.setRevisor( pair );
			entity.setTest( test );
			this.distributionSession.merge( entity );

			if ( mustAdd ) {
				pairs.add( current );
				mustAdd = false;
			}
			x++;
			if ( ( nIndex % 100 ) == 0 ) {
				logger.info( "Working..." + nIndex );
			}
			nIndex++;
		}
	}

	private InepRevisor getPair( ArrayList<InepRevisor> pairs, ArrayList<InepRevisor> assigneds )
	{
		int nIndex = ( (int) ( 0 + ( Math.random( ) * pairs.size( ) ) ) );

		InepRevisor pair = pairs.get( nIndex );
		pairs.remove( pair );
		assigneds.add( pair );
		if ( pairs.size( ) == 0 ) {
			pairs.addAll( assigneds );
			assigneds.clear( );
		}
		return pair;
	}

	@Override
	public List<InepTask> getTasks( InepEvent event )
	{
		return this.taskSession.getAll( event );
	}

	@Override
	public InepRevisor add( InepTask task, String name, String email, String cpf, boolean coordenador )
	{
		Person person = null;

		if ( SysUtils.isEmpty( email ) == false ) {
			person = this.personSession.getByDocument( email );
		}
		if ( person == null && SysUtils.isEmpty( cpf ) == false ) {
			person = this.personSession.getByDocument( cpf );
		}
		if ( person == null ) {
			person = this.createPerson( name, email, cpf );
		}
		Login login = this.loginSession.get( person.getId( ) );
		if ( login == null ) {
			this.loginSession.add( person, "987654" );
			login = this.loginSession.get( person.getId( ) );
		}
		Collaborator collaborator = this.collaboratorSession.find( new PrincipalDTO( task.getId( ).getCompanyId( ), login.getId( ), null ) );
		if ( collaborator == null ) {
			collaborator = this.collaboratorSession.add( login, task.getId( ).getCompanyId( ) );
			collaborator.add( this.roleSession.get( 4 ) );
			collaborator.add( coordenador ? this.roleSession.get( 20 ) : this.roleSession.get( 18 ) );
		}
		InepRevisor rev = new InepRevisor( collaborator, task.getEvent( ) );
		rev.setTask( task );
		rev.setCoordenador( coordenador );
		return this.revisorSession.merge( rev );
	}

	private Person createPerson( String name, String email, String cpf )
	{
		Person person = new Person( );
		person.setName( name.trim( ) );
		person = this.personSession.add( person );

		if ( SysUtils.isEmpty( email ) == false ) {
			person.add( new UserDocument( email, this.documentTypeSession.get( UserDocument.EMAIL ) ) );
		}
		if ( SysUtils.isEmpty( cpf ) == false ) {
			person.add( new UserDocument( cpf, this.documentTypeSession.get( UserDocument.CPF ) ) );
		}
		return person;
	}

	@Override
	public InepRevisor add( InepEvent event, Integer task, String name, String email, String cpf, Integer type )
	{
		Person person = null;

		if ( SysUtils.isEmpty( email ) == false ) {
			person = this.personSession.getByDocument( email );
		}
		if ( person == null && SysUtils.isEmpty( cpf ) == false ) {
			person = this.personSession.getByDocument( cpf );
		}
		if ( person == null ) {
			person = this.createPerson( name, email, cpf );
		}
		Login login = this.loginSession.get( person.getId( ) );
		if ( login == null ) {
			this.loginSession.add( person, "987654" );
			login = this.loginSession.get( person.getId( ) );
		}
		Collaborator collaborator = this.collaboratorSession.find( new PrincipalDTO( event.getId( ).getCompanyId( ), login.getId( ), null ) );
		if ( collaborator == null ) {
			collaborator = this.collaboratorSession.add( login, event.getId( ).getCompanyId( ) );
			collaborator.add( this.roleSession.get( 4 ) );
			collaborator.add( type.equals( 3 ) || type.equals( 4 ) ? this.roleSession.get( 20 ) : this.roleSession.get( 18 ) );
		}
		InepRevisor rev = new InepRevisor( collaborator, event );
		if ( task != null ) {
			rev.setTask( this.taskSession.get( new InepTaskPK( event, task ) ) );
		}
		rev.setType( this.getEntityManager( ).find( RevisorType.class, type ) );
		rev.setCoordenador( false );
		return this.revisorSession.merge( rev );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public void distributeGoldenTests( InepEvent event )
	{
		logger.info( "Distributing Golden Test" );
		List<InepGoldenTest> list;
		Integer lastTask = 0;

		Query q = this.getEntityManager( ).createNamedQuery( InepGoldenTest.getAll );
		this.setQueryParams( q, event.getId( ).getCompanyId( ), event.getId( ).getId( ) );
		list = (List<InepGoldenTest>) q.getResultList( );
		if ( SysUtils.isEmpty( list ) ) {
			logger.info( "No Golden Tests to distribute!" );
		}
		logger.info( "We have about " + list.size( ) + " golden tests to distribute!" );
		DistributionStatus status = this.distributionStatusSession.get( DistributionStatus.statusDistributed );
		List<InepRevisor> team = null;
		for ( InepGoldenTest item : list ) {
			if ( !lastTask.equals( item.getId( ).getTskIdIn( ) ) ) {
				lastTask = item.getId( ).getTskIdIn( );
				InepTaskPK taskKey = new InepTaskPK( event, item.getId( ).getTskIdIn( ) );
				team = this.teamSession.findByNamedQuery( InepRevisor.getAllRevisorByEventAndTask, this.taskSession.get( taskKey ) );
			}

			if ( SysUtils.isEmpty( team ) ) {
				continue;
			}
			logger.info( "Distributing  " + item.getId( ).getSubscriptionId( ) + " and task " + lastTask );
			for ( InepRevisor revisor : team ) {
				InepDistributionPK distributionKey = new InepDistributionPK( revisor, null );
				distributionKey.setSubscriptionId( item.getId( ).getSubscriptionId( ) );
				distributionKey.setTaskId( item.getId( ).getTskIdIn( ) );

				InepDistribution distribution = this.distributionSession.get( distributionKey );
				if ( distribution == null ) {
					distribution = new InepDistribution( revisor, null );
					distribution.setInsertDate( new Date( ) );
					distribution.getId( ).setSubscriptionId( item.getId( ).getSubscriptionId( ) );
					distribution.setStatus( status );
					distribution.setIsGolden( true );
					this.distributionSession.add( distribution );
				}
			}
		}
	}
}
