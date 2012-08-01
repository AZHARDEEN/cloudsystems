package br.com.mcampos.ejb.user.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;

/**
 * Session Bean implementation class ClientSessionBean
 */
@Stateless( name = "ClientSession", mappedName = "ClientSession" )
@LocalBean
public class ClientSessionBean extends CollaboratorBaseSessionBean<Client> implements ClientSession, ClientSessionLocal
{
	@EJB
	private UserDocumentSessionLocal userDocumentSession;

	@EJB
	private PersonSessionLocal personSession;

	@EJB
	private CompanySessionLocal companySession;

	@Override
	protected Class<Client> getEntityClass( )
	{
		return Client.class;
	}

	@Override
	public List<Client> getAllPerson( Collaborator auth, DBPaging paging )
	{
		List<Client> clients = Collections.emptyList( );

		if ( auth != null && auth.getCompany( ) != null ) {
			clients = findByNamedQuery( Client.getAllPerson, paging, auth.getCompany( ) );
		}
		return clients;
	}

	@Override
	public List<Client> getAllCompany( Collaborator auth, DBPaging paging )
	{
		List<Client> clients = Collections.emptyList( );

		if ( auth != null && auth.getCompany( ) != null ) {
			clients = findByNamedQuery( Client.getAllCompany, paging, auth.getCompany( ) );
		}
		return clients;
	}

	@Override
	public Long countPerson( Collaborator auth )
	{
		if ( auth == null || auth.getCompany( ) == null ) {
			return 0L;
		}
		Query query = getEntityManager( ).createNamedQuery( Client.countPerson );
		query.setParameter( 1, auth.getCompany( ) );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Long countCompany( Collaborator auth )
	{
		if ( auth == null || auth.getCompany( ) == null ) {
			return 0L;
		}
		Query query = getEntityManager( ).createNamedQuery( Client.countCompany );
		query.setParameter( 1, auth.getCompany( ) );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Users getUser( Collaborator auth, String document )
	{
		if ( auth == null || auth.getCompany( ) == null ) {
			return null;
		}
		Users user = this.userDocumentSession.getUserByDocument( document );
		if ( user != null ) {
			user.getAddresses( ).size( );
			user.getContacts( ).size( );
		}
		return user;
	}

	@Override
	public Users getUser( Collaborator auth, Integer id )
	{
		if ( auth == null || auth.getCompany( ) == null ) {
			return null;
		}
		Users user = getEntityManager( ).find( Users.class, id );
		if ( user != null ) {
			int nSize;
			nSize = user.getAddresses( ).size( );
			nSize = user.getContacts( ).size( );
			if ( nSize > 0 ) {
				nSize = 0;
			}
		}
		return user;
	}

	@Override
	public Client addNewPerson( Collaborator auth, Client newEntity )
	{
		configClient( newEntity );
		return updatePerson( auth, newEntity );
	}

	@Override
	public Client updatePerson( Collaborator auth, Client newEntity )
	{
		if ( auth.getCompany( ).equals( newEntity.getCompany( ) ) == false ) {
			newEntity.setCompany( auth.getCompany( ) );
		}
		newEntity.setClient( this.personSession.merge( (Person) newEntity.getClient( ) ) );
		return super.merge( newEntity );
	}

	private void configClient( Client client )
	{
		client.setFromDate( new Date( ) );
		if ( client.getId( ).getSequence( ) == null ) {
			client.getId( ).setSequence( getSequence( client.getCompany( ) ) );
		}
	}

	@Override
	public Client addNewCompany( Collaborator auth, Client newEntity )
	{
		configClient( newEntity );
		return updatePerson( auth, newEntity );
	}

	@Override
	public Client updateCompany( Collaborator auth, Client newEntity )
	{
		if ( auth.getCompany( ).equals( newEntity.getCompany( ) ) == false ) {
			newEntity.setCompany( auth.getCompany( ) );
		}
		newEntity.setClient( this.companySession.merge( (Company) newEntity.getClient( ) ) );
		return super.merge( newEntity );
	}

	private Integer getSequence( Company c )
	{
		if ( c == null ) {
			return null;
		}
		return getNextId( Client.nextId, c );
	}

	@Override
	public DocumentType getDocumentType( Integer type )
	{
		try {
			return getEntityManager( ).find( DocumentType.class, type );
		}
		catch ( NoResultException e )
		{
			return null;
		}
	}

	@Override
	public Client remove( Client entity )
	{
		try {
			return super.remove( entity );
		}
		catch ( Exception e ) {
			entity.setToDate( new Date( ) );
			return merge( entity );
		}
	}

	@Override
	public List<Person> reportClientList( Collaborator auth )
	{
		List<Client> clients = Collections.emptyList( );
		List<Person> persons = Collections.emptyList( );
		if ( auth != null && auth.getCompany( ) != null ) {
			clients = findByNamedQuery( Client.getAllPerson, auth.getCompany( ) );
			persons = new ArrayList<Person>( clients.size( ) );
			for ( Client client : clients ) {
				persons.add( (Person) client.getClient( ) );
			}
		}
		return persons;
	}
}
