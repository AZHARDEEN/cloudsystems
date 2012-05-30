package br.com.mcampos.ejb.user.client;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;

/**
 * Session Bean implementation class ClientSessionBean
 */
@Stateless( name = "ClientSession", mappedName = "ClientSession" )
@LocalBean
public class ClientSessionBean extends CollaboratorBaseSessionBean<Client> implements ClientSession, ClientSessionLocal
{
	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private UserDocumentSessionLocal userDocumentSession;

	@Override
	protected Class<Client> getEntityClass( )
	{
		return Client.class;
	}

	@Override
	public List<Client> getAllPerson( Authentication auth, DBPaging paging )
	{
		List<Client> clients = Collections.emptyList( );

		Company company = getCompanySession( ).get( auth.getCompanyId( ) );
		if ( company != null ) {
			clients = findByNamedQuery( Client.getAllPerson, paging, company );
		}
		return clients;
	}

	@Override
	public List<Client> getAllCompany( Authentication auth, DBPaging paging )
	{
		List<Client> clients = Collections.emptyList( );

		Company company = getCompanySession( ).get( auth.getCompanyId( ) );
		if ( company != null ) {
			clients = findByNamedQuery( Client.getAllCompany, paging, company );
		}
		return clients;
	}

	private CompanySessionLocal getCompanySession( )
	{
		return this.companySession;
	}

	@Override
	public Long countPerson( Authentication auth )
	{
		Company company = getCompanySession( ).get( auth.getCompanyId( ) );
		if ( company == null ) {
			return 0L;
		}
		Query query = getEntityManager( ).createNamedQuery( Client.countPerson );
		query.setParameter( 1, company );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Long countCompany( Authentication auth )
	{
		Company company = getCompanySession( ).get( auth.getCompanyId( ) );
		if ( company == null ) {
			return 0L;
		}
		Query query = getEntityManager( ).createNamedQuery( Client.countCompany );
		query.setParameter( 1, company );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Users getUser( Authentication auth, String document )
	{
		Company company = getCompanySession( ).get( auth.getCompanyId( ) );
		if ( company == null ) {
			return null;
		}
		Users user = this.userDocumentSession.getUserByDocument( document );
		user.getAddresses( ).size( );
		user.getContacts( ).size( );
		return user;
	}

	@Override
	public Users getUser( Authentication auth, Integer id )
	{
		Company company = getCompanySession( ).get( auth.getCompanyId( ) );
		if ( company == null ) {
			return null;
		}
		Users user = getEntityManager( ).find( Users.class, id );
		user.getAddresses( ).size( );
		user.getContacts( ).size( );
		return user;
	}

}
