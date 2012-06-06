package br.com.mcampos.ejb.user.client;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.locale.City;
import br.com.mcampos.ejb.locale.CitySessionLocal;
import br.com.mcampos.ejb.locale.state.State;
import br.com.mcampos.ejb.locale.state.StateSessionLocal;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.ejb.user.person.gender.Gender;
import br.com.mcampos.ejb.user.person.title.Title;
import br.com.mcampos.ejb.user.person.title.TitleSessionLocal;

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
	TitleSessionLocal titleSession;

	@EJB
	StateSessionLocal stateSession;

	@EJB
	CitySessionLocal citySession;

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
			user.getAddresses( ).size( );
			user.getContacts( ).size( );
		}
		return user;
	}

	@Override
	public List<Title> getTitle( Gender gender )
	{
		List<Title> titles = this.titleSession.getAll( gender );
		return titles;
	}

	@Override
	public List<State> getStates( String countryCode )
	{
		return this.stateSession.getAll( countryCode );
	}

	@Override
	public List<City> getCities( State state )
	{
		return this.citySession.getAll( state );
	}
}
