package br.com.mcampos.ejb.user.client;

import java.io.Serializable;
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

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.jpa.user.DocumentType;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.Users;

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
			Company c = this.companySession.get( auth.getCompanyID( ) );
			if ( c != null ) {
				clients = this.findByNamedQuery( Client.getAllPerson, paging, c );
			}
		}
		return clients;
	}

	@Override
	public List<Client> getAllCompany( PrincipalDTO auth, DBPaging paging )
	{
		List<Client> clients = Collections.emptyList( );
		if ( auth == null ) {
			return null;
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );

		clients = this.findByNamedQuery( Client.getAllCompany, paging, company );
		return clients;
	}

	@Override
	public Long countPerson( PrincipalDTO auth )
	{
		if ( auth == null ) {
			return null;
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );
		Query query = this.getEntityManager( ).createNamedQuery( Client.countPerson );
		query.setParameter( 1, company );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Long countCompany( PrincipalDTO auth )
	{
		if ( auth == null ) {
			return null;
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );
		Query query = this.getEntityManager( ).createNamedQuery( Client.countCompany );
		query.setParameter( 1, company );
		return (Long) query.getSingleResult( );
	}

	@Override
	public Users getUser( PrincipalDTO auth, String document )
	{
		if ( auth == null ) {
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
	public Users getUser( PrincipalDTO auth, Integer id )
	{
		if ( auth == null ) {
			return null;
		}
		Users user = this.getEntityManager( ).find( Users.class, id );
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
		if ( auth == null ) {
			return null;
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );
		newEntity.setCompany( company );
		this.configClient( auth, newEntity );
		return this.updatePerson( auth, newEntity );
	}

	@Override
	public Client updatePerson( PrincipalDTO auth, Client newEntity )
	{
		if ( auth == null ) {
			return null;
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );
		if ( company.equals( newEntity.getCompany( ) ) == false ) {
			newEntity.setCompany( company );
		}
		newEntity.setClient( this.personSession.merge( (Person) newEntity.getClient( ) ) );
		return super.merge( newEntity );
	}

	private void configClient( PrincipalDTO auth, Client client )
	{
		client.setFromDate( new Date( ) );
		if ( client.getId( ).getSequence( ) == null ) {
			this.set( auth, client );
			client.getId( ).setSequence( this.getSequence( client.getCompany( ) ) );
		}
	}

	@Override
	public Client addNewCompany( PrincipalDTO auth, Client newEntity )
	{
		this.configClient( auth, newEntity );
		return this.updateCompany( auth, newEntity );
	}

	@Override
	public Client updateCompany( PrincipalDTO auth, Client newEntity )
	{
		if ( auth == null ) {
			return null;
		}

		Company company = this.companySession.get( auth.getCompanyID( ) );
		if ( company.equals( newEntity.getCompany( ) ) == false ) {
			newEntity.setCompany( company );
		}
		newEntity.setClient( this.companySession.merge( (Company) newEntity.getClient( ) ) );
		return super.merge( newEntity );
	}

	private Integer getSequence( Company c )
	{
		if ( c == null ) {
			return null;
		}
		return this.getNextId( Client.nextId, c );
	}

	@Override
	public DocumentType getDocumentType( Integer type )
	{
		try {
			return this.getEntityManager( ).find( DocumentType.class, type );
		}
		catch ( NoResultException e )
		{
			return null;
		}
	}

	@Override
	public Client remove( PrincipalDTO auth, Serializable key )
	{
		if ( auth == null || key == null ) {
			throw new InvalidParameterException( this.getClass( ).getSimpleName( ) + " remove with invalid params " );
		}
		Client entity = this.get( key );
		if ( entity != null ) {
			entity.setToDate( new Date( ) );
			return this.update( auth, entity );
		}
		else {
			return null;
		}
	}

	@Override
	public List<Person> reportClientList( PrincipalDTO auth )
	{
		if ( auth == null ) {
			return null;
		}

		Company company = this.companySession.get( auth.getCompanyID( ) );
		List<Client> clients = Collections.emptyList( );
		List<Person> persons = Collections.emptyList( );
		clients = this.findByNamedQuery( Client.getAllPerson, company );
		persons = new ArrayList<Person>( clients.size( ) );
		for ( Client client : clients ) {
			persons.add( (Person) client.getClient( ) );
		}
		return persons;
	}

	@Override
	public Client getClient( PrincipalDTO auth, String document )
	{
		Users u = this.getUser( auth, document );
		if ( u == null ) {
			return null;
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );
		return this.getByNamedQuery( Client.getClientFromUser, company, u );
	}

	@Override
	public Client getClient( PrincipalDTO auth, Integer id )
	{
		Users u = this.getEntityManager( ).find( Users.class, id );
		return this.getClient( auth, u );
	}

	@Override
	public Client getClient( PrincipalDTO auth, Users user )
	{
		if ( user == null || auth == null ) {
			throw new InvalidParameterException( );
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );
		Client c;
		c = this.getByNamedQuery( Client.getClientFromUser, company, user );
		return c;
	}

	/**
	 * Brief Esta função tenta localizar um cliente pelo código interno que é atribuído pelo usuário do sistema. Este código interno faz parte da
	 * inteligência organizacional que contratou nosso sistema. Ou seja, e um identificador que diz respeito apenas a empresa. O conjunto UserId (Empresa
	 * Corrente) + internalCode é único
	 * 
	 * @param auth
	 *            Usuário Logado no sistema
	 * @param internalCode
	 *            Id interno da empresa
	 * 
	 * @return Client Entidade Cliente da empresa
	 */
	@Override
	public Client get( PrincipalDTO auth, String internalCode )
	{
		if ( auth == null || auth.getCompanyID( ) == null || internalCode == null ) {
			throw new InvalidParameterException( );
		}
		Company company = this.companySession.get( auth.getCompanyID( ) );

		return this.getByNamedQuery( Client.getByInternalCode, company, internalCode );
	}

	/**
	 * Brief Adiciona uma entrada na tabela de usuários ( Pessoa Física ou Jurídica ) como um cliente da empresa logada. Antes, verifica se já não temos
	 * este cliente na nossa base de dados para evitar dupla inserção.
	 */
	@Override
	public Client add( PrincipalDTO auth, Users newClient )
	{

		Client entity;

		/*
		 * Have we this client already????
		 */
		entity = this.getClient( auth, newClient );
		if ( entity != null ) {
			return entity;
		}
		/*
		 * No, we haven´t!!! Let´s create
		 */
		entity = new Client( );
		entity.setClient( newClient );
		return this.add( auth, entity );
	}

	@Override
	public Client add( PrincipalDTO auth, Client entity )
	{
		this.configClient( auth, entity );
		return super.add( auth, entity );
	}

}
