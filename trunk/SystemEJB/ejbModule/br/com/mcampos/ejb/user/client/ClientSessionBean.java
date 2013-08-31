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
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.utils.dto.PrincipalDTO;

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
	public List<Client> getAllPerson( PrincipalDTO auth, DBPaging paging )
	{
		List<Client> clients = Collections.emptyList( );

		if ( auth != null ) {
			Company c = companySession.get( auth.getCompanyID( ) );
			if ( c != null )
				clients = findByNamedQuery( Client.getAllPerson, paging, c );
		}
		return clients;
	}

	@Override
	public List<Client> getAllCompany( PrincipalDTO auth, DBPaging paging )
	{
		List<Client> clients = Collections.emptyList( );
		if ( auth == null )
			return null;
		Company company = companySession.get( auth.getCompanyID( ) );

		clients = findByNamedQuery( Client.getAllCompany, paging, company );
		return clients;
	}

	@Override
	public Long countPerson( PrincipalDTO auth )
	{
		if ( auth == null )
			return null;
		Company company = companySession.get( auth.getCompanyID( ) );
		Query query = getEntityManager( ).createNamedQuery( Client.countPerson );
		query.setParameter( 1, company );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Long countCompany( PrincipalDTO auth )
	{
		if ( auth == null )
			return null;
		Company company = companySession.get( auth.getCompanyID( ) );
		Query query = getEntityManager( ).createNamedQuery( Client.countCompany );
		query.setParameter( 1, company );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Users getUser( PrincipalDTO auth, String document )
	{
		if ( auth == null )
			return null;
		Users user = userDocumentSession.getUserByDocument( document );
		if ( user != null ) {
			user.getAddresses( ).size( );
			user.getContacts( ).size( );
		}
		return user;
	}

	@Override
	public Users getUser( PrincipalDTO auth, Integer id )
	{
		if ( auth == null )
			return null;
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
	public Client addNewPerson( PrincipalDTO auth, Client newEntity )
	{
		if ( auth == null )
			return null;
		Company company = companySession.get( auth.getCompanyID( ) );
		newEntity.setCompany( company );
		configClient( newEntity );
		return updatePerson( auth, newEntity );
	}

	@Override
	public Client updatePerson( PrincipalDTO auth, Client newEntity )
	{
		if ( auth == null )
			return null;
		Company company = companySession.get( auth.getCompanyID( ) );
		if ( company.equals( newEntity.getCompany( ) ) == false ) {
			newEntity.setCompany( company );
		}
		newEntity.setClient( personSession.merge( (Person) newEntity.getClient( ) ) );
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
	public Client addNewCompany( PrincipalDTO auth, Client newEntity )
	{
		configClient( newEntity );
		return updateCompany( auth, newEntity );
	}

	@Override
	public Client updateCompany( PrincipalDTO auth, Client newEntity )
	{
		if ( auth == null )
			return null;

		Company company = companySession.get( auth.getCompanyID( ) );
		if ( company.equals( newEntity.getCompany( ) ) == false ) {
			newEntity.setCompany( company );
		}
		newEntity.setClient( companySession.merge( (Company) newEntity.getClient( ) ) );
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
	public List<Person> reportClientList( PrincipalDTO auth )
	{
		if ( auth == null )
			return null;

		Company company = companySession.get( auth.getCompanyID( ) );
		List<Client> clients = Collections.emptyList( );
		List<Person> persons = Collections.emptyList( );
		clients = findByNamedQuery( Client.getAllPerson, company );
		persons = new ArrayList<Person>( clients.size( ) );
		for ( Client client : clients ) {
			persons.add( (Person) client.getClient( ) );
		}
		return persons;
	}

	@Override
	public Client getClient( PrincipalDTO auth, String document )
	{
		Users u = getUser( auth, document );
		if ( u == null ) {
			return null;
		}
		Company company = companySession.get( auth.getCompanyID( ) );
		return getByNamedQuery( Client.getClientFromUser, company, u );
	}

	@Override
	public Client getClient( PrincipalDTO auth, Integer id )
	{
		Users u = getEntityManager( ).find( Users.class, id );
		return getClient( auth, u );
	}

	@Override
	public Client getClient( PrincipalDTO auth, Users user )
	{
		if ( user == null ) {
			return null;
		}
		Company company = companySession.get( auth.getCompanyID( ) );
		Client c;
		c = getByNamedQuery( Client.getClientFromUser, company, user );
		return c;
	}
}
