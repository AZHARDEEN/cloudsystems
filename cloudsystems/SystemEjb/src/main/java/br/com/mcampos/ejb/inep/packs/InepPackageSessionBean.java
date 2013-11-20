package br.com.mcampos.ejb.inep.packs;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.InepStationSubscriptionResponsableImportDTO;
import br.com.mcampos.dto.inep.InepSubscriptionImportDTO;
import br.com.mcampos.dto.inep.StationGradeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.inep.InepStationSessionLocal;
import br.com.mcampos.ejb.inep.StationSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.security.LoginSessionLocal;
import br.com.mcampos.ejb.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.user.client.ClientSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepStation;
import br.com.mcampos.jpa.inep.InepStationPK;
import br.com.mcampos.jpa.inep.InepStationReponsable;
import br.com.mcampos.jpa.inep.InepStationReponsablePK;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.system.FileUpload;
import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.Collaborator;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.jpa.user.DocumentType;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class InepPackageSessionBean
 */
@Stateless( name = "InepPackageSession", mappedName = "InepPackageSession" )
@LocalBean
public class InepPackageSessionBean extends CollaboratorBaseSessionBean<InepEvent> implements InepPackageSession,
		InepPackageSessionLocal
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4327503286585562526L;
	private static final Logger LOGGER = LoggerFactory.getLogger( InepPackageSessionBean.class );

	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	private LoginSessionLocal loginSession;

	@EJB
	private RoleSessionLocal roleSession;

	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@EJB
	private PersonSessionLocal personSession;

	@EJB
	private ClientSessionLocal clientSession;

	@EJB
	private InepStationSessionLocal stationSession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionEvent;

	@EJB
	private StationSessionLocal gradeSession;

	@Override
	protected Class<InepEvent> getEntityClass( )
	{
		return InepEvent.class;
	}

	@Override
	public List<InepEvent> getAll( PrincipalDTO auth )
	{
		return this.getAll( auth, null );
	}

	@Override
	public List<InepEvent> getAll( PrincipalDTO c, DBPaging page )
	{
		if ( c == null ) {
			return Collections.emptyList( );
		}
		Company company = companySession.get( c.getCompanyID( ) );
		return this.findByNamedQuery( InepEvent.getAll, page, company );
	}

	@Override
	public Integer getNextId( PrincipalDTO c )
	{
		if ( c == null ) {
			return 0;
		}
		Query query = getEntityManager( ).createQuery(
				"select max( o.id.id ) + 1 from InepEvent o where o.company = ?1" );
		query.setParameter( 1, companySession.get( c.getCompanyID( ) ) );
		Integer id;
		try {
			id = (Integer) query.getSingleResult( );
			if ( id == null || id.equals( 0 ) ) {
				id = 1;
			}
		}
		catch ( Exception e ) {
			id = 1;
		}
		return id;
	}

	@Override
	public List<InepEvent> getAvailable( PrincipalDTO c )
	{
		Company company = companySession.get( c.getCompanyID( ) );
		if ( company == null ) {
			return Collections.emptyList( );
		}

		return this.findByNamedQuery( InepEvent.getAvailable, company );
	}

	@Override
	public List<InepEvent> getAvailable( )
	{
		return this.findByNamedQuery( InepEvent.getAllAvailable );
	}

	private void createTasks( PrincipalDTO auth, InepEvent newEntity )
	{
		for ( int i = 1; i <= 4; i++ ) {
			InepTask task = new InepTask( newEntity );
			task.getId( ).setId( i );
			task.setDescription( "Tarefa " + i );
			taskSession.add( auth, task );
		}
	}

	@Override
	public InepEvent add( PrincipalDTO auth, InepEvent newEntity )
	{
		newEntity = super.add( auth, newEntity );
		createTasks( auth, newEntity );
		return newEntity;
	}

	@Override
	public InepEvent remove( PrincipalDTO auth, Serializable key )
	{
		InepEvent event = get( key );
		if ( event != null ) {
			taskSession.remove( event );
			return super.remove( auth, key );
		}
		return null;
	}

	/**
	 * Brief Adiciona um registro de importação do INEP na base de dados, sempre verificando a existência do mesmo, ou seja, antes de incluir, certifica
	 * que a tupla já existe ou não. Esta é a versão para inserção de múltiplos registros Esta função é chamada na carga das inscrições que, no momento, é
	 * feito na tela de gerenciamento de eventos
	 *
	 * @see public InepStation add( PrincipalDTO auth, InepSubscriptionImportDTO subscription, InepEvent event )
	 */
	@Override
	public void add( PrincipalDTO auth, List<InepSubscriptionImportDTO> subscriptions, InepEvent event )
	{
		if ( auth == null || auth.getCompanyID( ) == null || auth.getUserId( ) == null || SysUtils.isEmpty( subscriptions ) ) {
			throw new InvalidParameterException( );
		}
		for ( InepSubscriptionImportDTO subscription : subscriptions ) {
			this.add( auth, subscription, event );
		}
	}

	@Override
	/**
	 * Brief Adiciona um registro de importação do INEP na base de dados, sempre verificando a existência do mesmo, ou seja, antes de incluir, certifica
	 * que a tupla já existe ou não.
	 * Esta função é chamada na carga das inscrições que, no momento, é feito na tela de gerenciamento de eventos
	 */
	public void add( PrincipalDTO auth, InepSubscriptionImportDTO dto, InepEvent event )
	{
		if ( auth == null || auth.getCompanyID( ) == null || auth.getUserId( ) == null || dto == null ) {
			throw new InvalidParameterException( );
		}
		Client client = getClient( auth, dto );
		InepStation station = getStation( auth, event, client );
		InepSubscription subscription = getSubscription( auth, event, dto.getSubscription( ) );
		subscription.setStationId( station.getId( ).getClientId( ) );
		Person person = getPerson( auth, dto );
		subscription.setPerson( person );
		subscription.setSpecialNeeds( dto.getSpecialNeeds( ) );
		subscription.setCitizenship( dto.getCountry( ) );
	}

	/**
	 * Brief Adiciona a inscrição no banco de dados, se já existir esta inscrição nada é feito Esta função é chamada na carga das inscrições que, no
	 * momento, é feito na tela de gerenciamento de eventos
	 *
	 * @param auth
	 * @param event
	 * @param id
	 * @return InepSubscription
	 */
	private InepSubscription getSubscription( PrincipalDTO auth, InepEvent event, String id )
	{
		InepSubscription subscription = subscriptionEvent.get( new InepSubscriptionPK( event, id ) );
		if ( subscription == null ) {
			subscription = new InepSubscription( );
			subscription.setEvent( event );
			subscription.getId( ).setId( id );
			subscription = subscriptionEvent.add( subscription );
		}
		return subscription;

	}

	/**
	 * Brief Obtém um posto aplicador (InepStation) válido para inclusão na tabela de posto aplicador do inep Esta função é chamada na carga das inscrições
	 * que, no momento, é feito na tela de gerenciamento de eventos
	 *
	 * @param auth
	 * @param event
	 * @param client
	 * @return InepStation
	 */
	private InepStation getStation( PrincipalDTO auth, InepEvent event, Client client )
	{
		InepStationPK key = new InepStationPK( client.getId( ).getCompanyId( ), event.getId( ).getId( ), client.getId( ).getSequence( ) );
		InepStation station;
		station = stationSession.get( key );
		if ( station == null ) {
			station = new InepStation( );
			station.setClient( client );
			station.setEvent( event );
			station = stationSession.add( station );
		}
		return station;
	}

	/**
	 * Brief obtém um cliente válido para inclusão na tabela de posto aplicador do inep Esta função é chamada na carga das inscrições que, no momento, é
	 * feito na tela de gerenciamento de eventos
	 *
	 * @param auth
	 * @param subscription
	 * @return Client
	 */
	private Client getClient( PrincipalDTO auth, InepSubscriptionImportDTO subscription )
	{
		Client client = null;
		client = clientSession.get( auth, subscription.getStationId( ) );
		if ( client == null ) {
			Company station = this.getCompany( auth, subscription );
			client = clientSession.add( auth, station );
			client.setInternalCode( subscription.getStationId( ) );
		}
		return client;
	}

	/**
	 * Brief Esta função tenta localizar algum registo na tabela de usuarios(Empresa), através do documento internalCode, com a combinação de inep.station
	 * + id do posto aplicador Importante informar que a combinação do tipo do documento internalCode + o valor não é único, em outras palavras, um
	 * registro na tabela users pode ter mais de uma ocorrência de internalCode A combinação em questão (inep.station+id) tenta garantir a unicidade de uma
	 * tupla dentro do banco de dados. Esta função é chamada na carga das inscrições que, no momento, é feito na tela de gerenciamento de eventos
	 *
	 * @param auth
	 * @param subscription
	 * @return Company
	 */
	private Company getCompany( PrincipalDTO auth, InepSubscriptionImportDTO subscription )
	{
		/**
		 * Tenta garantir a unicidade de uma tupla
		 */
		String code = "inep.station." + subscription.getStationId( );
		List<UserDocument> documents = companySession.searchByDocument( UserDocument.INTERNAL_CODE, code );
		Company station = null;
		if ( SysUtils.isEmpty( documents ) ) {
			station = new Company( );
			station.setName( subscription.getStationName( ) );
			station = companySession.add( auth, station );
			UserDocument document = new UserDocument( );
			document.setAdditionalInfo( "Regitro importado via sistema - Inep" );
			document.setCode( code );
			document.setType( getEntityManager( ).getReference( DocumentType.class, UserDocument.INTERNAL_CODE ) );
			station.add( document );
		}
		else if ( documents.size( ) > 1 ) {
			throw new UnsupportedOperationException( "Esperado apenas um documento porém foi encontrado mais de um documento" );
		}
		else {
			station = (Company) documents.get( 0 ).getUser( );
		}
		return station;
	}

	/**
	 * Brief Esta função tenta localizar algum registo na tabela de usuarios(Pessoa), através do documento de identidade, que pode ser o documento
	 * originário do pais do candidato ou passaporte. Existe a possibilidade de recuperarmos mais de um registro, o que seria um problema e neste caso o
	 * sistema ainda não está preparado para tratar Esta função é chamada na carga das inscrições que, no momento, é feito na tela de gerenciamento de
	 * eventos. Talvez fosse melhor mudar para uma nova tela. Se não houver um registro, será incluído uma nova pessoa.
	 *
	 *
	 * @param auth
	 * @param dto
	 *            do tipo InepSubscriptionImportDTO
	 * @return Person
	 */
	private Person getPerson( PrincipalDTO auth, InepSubscriptionImportDTO dto )
	{
		Person person = null;
		List<UserDocument> documents;
		int documentType;

		documentType = "passaporte".equalsIgnoreCase( dto.getDocumentType( ) ) ? UserDocument.PASSAPORTE : UserDocument.IDENTITIDADE;
		documents = companySession.searchByDocument( documentType, dto.getDocument( ) );
		if ( SysUtils.isEmpty( documents ) ) {
			person = new Person( );
			person.setName( dto.getName( ) );
			person = personSession.add( person );
			DocumentType type = getEntityManager( ).getReference( DocumentType.class, documentType );
			UserDocument document = new UserDocument( dto.getDocument( ), type );
			document.setAdditionalInfo( dto.getDocumentType( ) + "\nImportado pelo inep" );
			person.add( document );
			clientSession.add( auth, person );
		}
		else if ( documents.size( ) == 1 ) {
			if ( documents.get( 0 ).getUser( ) instanceof Person ) {
				person = (Person) documents.get( 0 ).getUser( );
			}
			else {
				throw new UnsupportedOperationException( "O documento está associado a uma empresa, não uma pessoa como esperado" );
			}
		}
		else {
			throw new UnsupportedOperationException( "Esperado apenas um documento porém foi encontrado mais de um documento" );
		}
		return person;
	}

	@Override
	public FileUpload storeUploadInformation( PrincipalDTO auth, MediaDTO media, int processed, int rejected )
	{
		FileUpload file = this.storeUploadInformation( auth, media );
		file.setRecords( processed );
		file.setRejecteds( rejected );
		return file;
	}

	@Override
	public void add( PrincipalDTO auth, InepStationSubscriptionResponsableImportDTO record, InepEvent event )
	{
		List<UserDocument> documents;
		Person person;

		if ( auth == null || record == null || event == null ) {
			throw new InvalidParameterException( );
		}

		LOGGER.info( "Looking for email: " + record.getEmail( ) );
		documents = personSession.searchByEmail( record.getEmail( ) );
		if ( SysUtils.isEmpty( documents ) ) {
			LOGGER.info( "There is no person with that email! Creating person: " + record.getName( ) );
			person = new Person( );
			person.setName( record.getName( ) );
			person = personSession.add( person );
			DocumentType type = getEntityManager( ).getReference( DocumentType.class, UserDocument.EMAIL );
			UserDocument document = new UserDocument( record.getEmail( ), type );
			document.setAdditionalInfo( "Email Importado pelo inep" );
			person.add( document );
			clientSession.add( auth, person );
		}
		else {
			person = personSession.get( documents.get( 0 ).getUser( ).getId( ) );
		}
		Client client = clientSession.getClient( auth, person );
		if ( client == null ) {
			LOGGER.info( "This person is not a client: " + record.getName( ) );
			clientSession.add( auth, person );
		}
		Login login = loginSession.get( person.getId( ) );
		if ( login == null ) {
			LOGGER.info( "This person has no login: " + record.getName( ) );
			login = loginSession.add( person, "987654" );
		}
		Collaborator collaborator = collaboratorSession.get( client.getCompany( ), login );
		if ( collaborator == null ) {
			LOGGER.info( "There is no collaborator for this person: " + record.getName( ) );
			collaborator = collaboratorSession.add( login, auth.getCompanyID( ) );
		}
		Role userRole = roleSession.get( 4 );
		Role stationRole = roleSession.get( 22 );
		if ( !collaborator.getRoles( ).contains( userRole ) ) {
			collaborator.getRoles( ).add( userRole );
		}
		if ( !collaborator.getRoles( ).contains( stationRole ) ) {
			collaborator.getRoles( ).add( stationRole );
		}

		Client station = clientSession.get( auth, record.getStationId( ) );
		if ( station != null ) {
			InepStationReponsablePK r = new InepStationReponsablePK( event, station, collaborator );
			InepStationReponsable existing = getEntityManager( ).find( InepStationReponsable.class, r );
			if ( existing == null ) {
				existing = new InepStationReponsable( );
				existing.setId( r );
				getEntityManager( ).persist( existing );
			}
		}
		else {
			LOGGER.error( "STATION not found. Station with internal id " + record.getStationId( ) + " not found" );
		}
		LOGGER.info( "Done!!!!" );
	}

	@Override
	public void verifyInepRecord( PrincipalDTO auth, InepEvent evt, StationGradeDTO other )
	{
		InepSubscription subscription = subscriptionEvent.get( new InepSubscriptionPK( evt, other.getSubscription( ) ) );
		if ( subscription == null ) {
			return;
		}
		if ( other.getIsMising( ) ) {
			gradeSession.setMissing( auth, subscription );
			return;
		}
		gradeSession.setInterviewerInformation( auth, subscription, null, other.getInterviewerGrade( ) );
		gradeSession.setObserverInformation( auth, subscription, other.getObserverGrade( ) );
	}

	public StationGradeDTO getOralGrade( PrincipalDTO auth, InepEvent evt, StationGradeDTO other )
	{
		InepSubscription subscription = subscriptionEvent.get( new InepSubscriptionPK( evt, other.getSubscription( ) ) );
		if( subscription == null ) {
			return null;
		}
		return gradeSession.getStationGrade( auth, subscription );

	}

}
