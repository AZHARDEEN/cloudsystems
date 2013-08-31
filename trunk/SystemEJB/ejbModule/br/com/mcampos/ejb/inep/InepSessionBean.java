package br.com.mcampos.ejb.inep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.TransactionTimeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import br.com.mcampos.entity.inep.DistributionStatus;
import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepPackagePK;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.entity.inep.InepTaskPK;
import br.com.mcampos.entity.inep.InepTest;
import br.com.mcampos.entity.inep.RevisorType;
import br.com.mcampos.entity.security.Login;
import br.com.mcampos.entity.security.UserStatus;
import br.com.mcampos.entity.user.Collaborator;
import br.com.mcampos.entity.user.CollaboratorType;
import br.com.mcampos.entity.user.Company;
import br.com.mcampos.entity.user.Person;
import br.com.mcampos.entity.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.utils.dto.PrincipalDTO;

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
		Company company = companySession.get( dto.getUser( ) );
		if ( company == null ) {
			logger.warn( "Company is null" );
			return false;
		}
		InepPackage event = packageSession.get( new InepPackagePK( company, dto.getEvento( ) ) );
		if ( event == null ) {
			logger.warn( "Event is null" );
			return false;
		}
		Person person = getPerson( dto );
		Login login = getLogin( person );
		Collaborator col = getCollaborator( login, company, dto );
		logger.debug( "The task is done!!!!! " );
		logger.debug( "Name...........: " + col.getPerson( ).getName( ) );
		logger.debug( "Id.............: " + col.getPerson( ).getId( ) );
		logger.debug( "Collaborator ID: " + col.getId( ).getSequence( ) );
		return getRevisor( col, dto ) != null ? true : false;
	}

	private InepRevisor getRevisor( Collaborator col, CorretorDTO dto )
	{
		InepPackagePK key = new InepPackagePK( col.getCompany( ), dto.getEvento( ) );
		InepPackage event = eventSession.get( key );
		if ( event == null ) {
			return null;
		}
		InepRevisor rev = revisorSession.get( event, col );
		if ( rev == null ) {
			rev = new InepRevisor( );
			rev.setCollaborator( col );
			rev.setCoordenador( dto.getCoordenador( ) );
			InepTask task = taskSession.get( new InepTaskPK( event, dto.getTarefa( ) ) );
			if ( task == null ) {
				return null;
			}
			rev.setTask( task );
			rev = revisorSession.merge( rev );
		}
		return rev;
	}

	private Collaborator getCollaborator( Login login, Company company, CorretorDTO dto )
	{
		Collaborator collaborator = collaboratorSession.find( new PrincipalDTO( company.getId( ), login.getId( ), null ) );
		if ( collaborator == null ) {
			logger.debug( "Collaborator does not exists: " + login.getPerson( ).getName( ) );
			collaborator = new Collaborator( );
			collaborator.setCollaboratorType( collaboratorTypeSession.get( CollaboratorType.typeEmployee ) );
			collaborator.setCompany( company );
			collaborator.setPerson( login.getPerson( ) );
			collaborator.setCpsIdIn( 5 );
			collaborator.setFromDate( new Date( ) );
			collaborator = collaboratorSession.merge( collaborator );
		}
		logger.debug( "Adding roles for collaborator: " + collaborator.getId( ).getSequence( ) );
		collaborator.add( roleSession.get( 4 ) );
		if ( dto.getCoordenador( ) ) {
			collaborator.add( roleSession.get( 20 ) );
		}
		else {
			collaborator.add( roleSession.get( 18 ) );
		}
		return collaborator;
	}

	private Login getLogin( Person person )
	{
		Login login = loginSession.get( person.getId( ) );
		if ( login == null ) {
			logger.debug( "Creating Login for person: " + person.getName( ) );
			loginSession.add( person, "987654" );
			login = loginSession.get( person.getId( ) );
		}
		logger.debug( "Resetting login parameters for login: " + login.getId( ) );
		login.setStatus( userStatusSession.get( UserStatus.statusOk ) );
		login.setTryCount( 0 );
		Calendar now = Calendar.getInstance( );
		now.add( Calendar.DATE, 60 );
		login.setExpDate( now.getTime( ) );
		return login;
	}

	private Person getPerson( CorretorDTO dto )
	{
		Person person = personSession.getByDocument( dto.getCpf( ) );
		if ( person == null ) {
			logger.debug( "There is no person for document" + dto.getCpf( ) );
			person = personSession.getByDocument( dto.getEmail( ) );
			if ( person == null ) {
				logger.debug( "There is no person for document" + dto.getEmail( ) );
				person = insertPerson( dto );
				logger.debug( dto.getNome( ) + " is created" );
			}
			else {
				UserDocument doc = new UserDocument( dto.getCpf( ), documentTypeSession.get( UserDocument.typeCPF ) );
				person.add( doc );
			}
		}
		return person;
	}

	private Person insertPerson( CorretorDTO dto )
	{
		Person person = new Person( );

		logger.debug( "Creating Person" + dto.getNome( ) );
		UserDocument doc = new UserDocument( dto.getCpf( ), documentTypeSession.get( UserDocument.typeCPF ) );
		person.add( doc );
		doc = new UserDocument( dto.getEmail( ), documentTypeSession.get( UserDocument.typeEmail ) );
		person.add( doc );
		person.setName( dto.getNome( ) );
		return personSession.merge( person );
	}

	@Override
	@TransactionTimeout( unit = TimeUnit.MINUTES, value = 20 )
	public void distribute( InepTask task )
	{
		int x = 0;
		int nIndex = 1;
		InepDistribution entity;
		DistributionStatus status = distributionStatusSession.get( new Integer( 1 ) );
		List<InepRevisor> team = teamSession.findByNamedQuery( InepRevisor.getAllRevisorByEventAndTask, task );
		ArrayList<InepRevisor> pairs = new ArrayList<InepRevisor>( team );
		ArrayList<InepRevisor> assigneds = new ArrayList<InepRevisor>( team.size( ) );
		logger.info( "Getting tests....." );
		List<InepTest> tests = testSession.findByNamedQuery( InepTest.getAllEventTasks, task );
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
			distributionSession.merge( entity );

			InepRevisor pair = getPair( pairs, assigneds );
			entity = new InepDistribution( );
			entity.setStatus( status );
			entity.setRevisor( pair );
			entity.setTest( test );
			distributionSession.merge( entity );

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
	public List<InepTask> getTasks( InepPackage event )
	{
		return taskSession.getAll( event );
	}

	@Override
	public InepRevisor add( InepTask task, String name, String email, String cpf, boolean coordenador )
	{
		Person person = null;

		if ( SysUtils.isEmpty( email ) == false ) {
			person = personSession.getByDocument( email );
		}
		if ( person == null && SysUtils.isEmpty( cpf ) == false ) {
			person = personSession.getByDocument( cpf );
		}
		if ( person == null ) {
			person = new Person( );
			if ( SysUtils.isEmpty( email ) == false ) {
				person.add( new UserDocument( email, documentTypeSession.get( UserDocument.typeEmail ) ) );
			}
			if ( SysUtils.isEmpty( cpf ) == false ) {
				person.add( new UserDocument( cpf, documentTypeSession.get( UserDocument.typeCPF ) ) );
			}
			person.setName( name );
			person = personSession.merge( person );
		}
		Login login = loginSession.get( person.getId( ) );
		if ( login == null ) {
			loginSession.add( person, "987654" );
			login = loginSession.get( person.getId( ) );
		}
		Collaborator collaborator = collaboratorSession.find( new PrincipalDTO( task.getId( ).getCompanyId( ), login.getId( ), null ) );
		if ( collaborator == null ) {
			collaborator = collaboratorSession.add( login, task.getId( ).getCompanyId( ) );
			collaborator.add( roleSession.get( 4 ) );
			collaborator.add( coordenador ? roleSession.get( 20 ) : roleSession.get( 18 ) );
		}
		InepRevisor rev = new InepRevisor( collaborator, task.getEvent( ) );
		rev.setTask( task );
		rev.setCoordenador( coordenador );
		return revisorSession.merge( rev );
	}

	@Override
	public InepRevisor add( InepPackage event, Integer task, String name, String email, String cpf, Integer type )
	{
		Person person = null;

		if ( SysUtils.isEmpty( email ) == false ) {
			person = personSession.getByDocument( email );
		}
		if ( person == null && SysUtils.isEmpty( cpf ) == false ) {
			person = personSession.getByDocument( cpf );
		}
		if ( person == null ) {
			person = new Person( );
			if ( SysUtils.isEmpty( email ) == false ) {
				person.add( new UserDocument( email, documentTypeSession.get( UserDocument.typeEmail ) ) );
			}
			if ( SysUtils.isEmpty( cpf ) == false ) {
				person.add( new UserDocument( cpf, documentTypeSession.get( UserDocument.typeCPF ) ) );
			}
			person.setName( name );
			person = personSession.merge( person );
		}
		Login login = loginSession.get( person.getId( ) );
		if ( login == null ) {
			loginSession.add( person, "987654" );
			login = loginSession.get( person.getId( ) );
		}
		Collaborator collaborator = collaboratorSession.find( new PrincipalDTO( event.getId( ).getCompanyId( ), login.getId( ), null ) );
		if ( collaborator == null ) {
			collaborator = collaboratorSession.add( login, event.getId( ).getCompanyId( ) );
			collaborator.add( roleSession.get( 4 ) );
			collaborator.add( type.equals( 3 ) || type.equals( 4 ) ? roleSession.get( 20 ) : roleSession.get( 18 ) );
		}
		InepRevisor rev = new InepRevisor( collaborator, event );
		if ( task != null ) {
			rev.setTask( taskSession.get( new InepTaskPK( event, task ) ) );
		}
		rev.setType( getEntityManager( ).find( RevisorType.class, type ) );
		rev.setCoordenador( false );
		return revisorSession.merge( rev );
	}
}
