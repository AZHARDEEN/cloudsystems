package br.com.mcampos.ejb.cloudsystem.user.person.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.user.UserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.address.AddressUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.entity.AddressType;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.entity.CivilState;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity.Title;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.ejb.cloudsystem.user.contact.UserContactUtil;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;
import br.com.mcampos.ejb.cloudsystem.user.document.UserDocumentUtil;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "PersonSession", mappedName = "PersonSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PersonSessionBean implements PersonSessionLocal
{
	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private UserSessionLocal userSession;

	public PersonSessionBean( )
	{
	}

	protected UserSessionLocal getUserSession( )
	{
		return userSession;
	}

	/**
	 * Adiciona um pessoa (Usuario do tipo pessoa) no banco de dados.
	 * 
	 * @param dto
	 *            DTO personDTO
	 * @return Person EntityBean
	 */
	@Override
	public Person add( PersonDTO dto )
	{
		return add( PersonUtil.createEntity( dto ) );
	}

	/**
	 * Adiciona um pessoa (Usuario do tipo pessoa) no banco de dados. Esta funcao recebe como parâmetro uma Entity que não está gerenciado pelo EJB.
	 * 
	 * 
	 * @param entity
	 *            EntityBean Não gerenciado (Not Managed)
	 * @return Person EntityBean
	 */
	@Override
	public Person add( Person entity )
	{
		String splitted[] = splitName( entity.getName( ) );
		entity.setFirstName( splitted[ 0 ] );
		entity.setMiddleName( splitted[ 1 ] );
		entity.setLastName( splitted[ 2 ] );
		applyRules( entity );
		verifyDocuments( entity );
		entity.setUserType( em.find( UserType.class, "1" ) );
		entity.setCivilState( em.find( CivilState.class, 1 ) );
		entity.setGender( em.find( Gender.class, 1 ) );
		entity.setTitle( em.find( Title.class, 1 ) );
		em.persist( entity );
		return entity;
	}

	protected String[ ] splitName( String name )
	{
		String splitted[] = name.split( " " );
		String firstName = null, lastName = null, middleName = null;
		int nIndex;

		for ( nIndex = 0; nIndex < splitted.length; nIndex++ )
			if ( nIndex == 0 )
				firstName = splitted[ nIndex ];
			else if ( nIndex == splitted.length - 1 )
				lastName = splitted[ nIndex ];
			else if ( middleName == null || middleName.isEmpty( ) )
				middleName = splitted[ nIndex ];
			else
				middleName += " " + splitted[ nIndex ];

		splitted = new String[ 3 ];
		splitted[ 0 ] = firstName;
		splitted[ 1 ] = middleName;
		splitted[ 2 ] = lastName;

		return splitted;
	}

	/*
	public Person createPersonForLogin( RegisterDTO dto )
	{
		Person person;

		person = (Person) getUserSession().findByDocumentList( dto.getDocuments() );
		if ( person == null ) {
			person = add ( DTOFactory.copy( dto ) );
		}
		else {
			if ( person.getLogin() != null )
				throw new EJBException ( "Este cadastro já possui login" );
			if ( person.getName().equalsIgnoreCase( dto.getName() ) == false ) {
	            String splitted[] = splitName( dto.getName() );
				person.setName( dto.getName() );
	            person.setFirstName ( splitted[0] );
			    person.setMiddleName( splitted[1] );
			    person.setLastName  ( splitted[2] );
			}
			for ( UserDocumentDTO documentDTO : dto.getDocuments() ) {
				boolean bFound = false;
				for ( UserDocument document : person.getDocuments() ) {
					if ( document.getDocumentId().equals(documentDTO.getDocumentType().getId() ) ) {
						document.setCode( documentDTO.getCode() );
						bFound = true;
					}
				}
				if ( bFound == false )
				{
					person.addDocument( DTOFactory.copy ( documentDTO ) );
				}
			}
		}
		return person;
	}
	*/

	protected void verifyDocuments( Person person )
	{
		if ( person.getDocuments( ) != null )
			for ( UserDocument item : person.getDocuments( ) )
				if ( getUserSession( ).getUserByDocument( item ) != null )
					throw new EJBException( "Ja existe um usuário cadastrado com o mesmo documento [" + item.getCode( ) + "]" +
							". O sistema não permite dois cadastros com os mesmos documentos" );
	}

	protected void applyRules( Person person )
	{
		person.setFatherName( SysUtils.toUpperCase( person.getFatherName( ) ) );
		person.setFirstName( SysUtils.toUpperCase( person.getFirstName( ) ) );
		person.setLastName( SysUtils.toUpperCase( person.getLastName( ) ) );
		person.setMotherName( SysUtils.toUpperCase( person.getMotherName( ) ) );
		person.setName( SysUtils.toUpperCase( person.getName( ) ) );
		person.setNickName( SysUtils.toUpperCase( person.getNickName( ) ) );
	}

	protected void mergeAddress( Person person, List<AddressDTO> list )
	{
		int nIndex;

		for ( AddressDTO addressDTO : list ) {
			Address address = AddressUtil.createEntity( addressDTO, person );
			address.setUser( person );
			nIndex = person.getAddresses( ).indexOf( address );
			if ( nIndex >= 0 ) {
				Address merged = person.getAddresses( ).get( nIndex );
				AddressUtil.update( merged, addressDTO );
				merged.setCity( em.find( City.class, addressDTO.getCity( ).getId( ) ) );
				merged.setAddressType( em.find( AddressType.class, merged.getAddressType( ).getId( ) ) );
			}
			else
				person.addAddress( address );
		}
		ArrayList<Address> removeList = null;
		for ( Address entity : person.getAddresses( ) ) {
			AddressDTO ct = AddressUtil.copy( entity );
			if ( list.contains( ct ) == false ) {
				if ( removeList == null )
					removeList = new ArrayList<Address>( );
				removeList.add( entity );
				em.remove( entity );
			}
		}
		if ( removeList != null && removeList.size( ) > 0 )
			for ( Address entity : removeList )
				person.removeAddress( entity );
	}

	protected void mergeDocuments( Person person, List<UserDocumentDTO> list )
	{
		ArrayList<UserDocument> removeList = null;
		for ( UserDocument entity : person.getDocuments( ) ) {
			UserDocumentDTO ct = UserDocumentUtil.copy( entity );
			if ( list.contains( ct ) == false ) {
				if ( removeList == null )
					removeList = new ArrayList<UserDocument>( );
				removeList.add( entity );
				em.remove( entity );
			}
		}
		if ( removeList != null && removeList.size( ) > 0 )
			for ( UserDocument entity : removeList )
				person.removeDocument( entity );
	}

	protected void mergeContacts( Person person, List<UserContactDTO> list )
	{
		int nIndex;

		for ( UserContactDTO dtoItem : list ) {
			UserContact entity = UserContactUtil.createEntity( dtoItem, person );
			entity.setUser( person );
			nIndex = person.getContacts( ).indexOf( entity );
			if ( nIndex >= 0 ) {
				UserContact merged = person.getContacts( ).get( nIndex );
				UserContactUtil.update( merged, dtoItem );
				merged.setContactType( em.find( ContactType.class, merged.getContactType( ).getId( ) ) );
			}
			else
				person.addContact( entity );
		}

		ArrayList<UserContact> removeList = null;
		for ( UserContact entity : person.getContacts( ) ) {
			UserContactDTO ct = UserContactUtil.copy( entity );
			if ( list.contains( ct ) == false ) {
				if ( removeList == null )
					removeList = new ArrayList<UserContact>( );
				removeList.add( entity );
				em.remove( entity );
			}
		}
		if ( removeList != null && removeList.size( ) > 0 )
			for ( UserContact entity : removeList )
				person.removeContact( entity );
	}

	@Override
	public void delete( Integer id )
	{
		Person person;

		person = em.find( Person.class, id );
		em.remove( person );
	}

	@Override
	public List<PersonDTO> getAll( )
	{
		return getDTOList( ( em.createNamedQuery( "Person.findAll" ).getResultList( ) ) );
	}

	protected List<PersonDTO> getDTOList( List<Person> list )
	{
		List<PersonDTO> dtos;

		if ( list == null )
			return Collections.emptyList( );
		dtos = new ArrayList<PersonDTO>( list.size( ) );
		for ( Person item : list )
			dtos.add( PersonUtil.copy( item ) );
		return dtos;
	}

	@Override
	public PersonDTO getByDocument( String document )
	{
		Query query;
		UserDocument userDocument;
		Person person;
		PersonDTO dto = null;

		try {
			query = em.createNamedQuery( "UserDocument.findByDocument" );
			query.setParameter( "document", document );
			userDocument = (UserDocument) query.getSingleResult( );
			if ( userDocument == null )
				return null;
			person = em.find( Person.class, userDocument.getUserId( ) );
			if ( person != null )
				dto = PersonUtil.copy( person );
			return dto;
		}
		catch ( NoResultException e ) {
			return null;
		}
	}

	@Override
	public PersonDTO get( Integer userId )
	{
		Person person;

		person = em.find( Person.class, userId );
		if ( person != null ) {
			if ( person.getUserType( ) != null )
				em.refresh( person.getUserType( ) );
			return PersonUtil.copy( person );
		}
		else
			return null;
	}
}
