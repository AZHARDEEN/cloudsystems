package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.address.AddressType;
import br.com.mcampos.ejb.entity.address.City;
import br.com.mcampos.ejb.entity.login.Login;
import br.com.mcampos.ejb.entity.user.Address;
import br.com.mcampos.ejb.entity.user.Person;

import br.com.mcampos.ejb.entity.user.UserContact;
import br.com.mcampos.ejb.entity.user.UserDocument;


import br.com.mcampos.ejb.entity.user.attributes.ContactType;
import br.com.mcampos.ejb.entity.user.attributes.DocumentType;

import br.com.mcampos.ejb.entity.user.attributes.UserStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "PersonSession", mappedName = "CloudSystems-EjbPrj-PersonSession" )
public class PersonSessionBean implements PersonSessionLocal
{
	@PersistenceContext( unitName = "EjbPrj" )
	private EntityManager em;
    
        
    @EJB UserSessionLocal userSession;

	public PersonSessionBean()
	{
	}

    protected UserSessionLocal getUserSession()
    {
        return userSession;
    }


    /**
     * Adiciona um pessoa (Usuario do tipo pessoa) no banco de dados.
     * @param dto DTO personDTO
     * @return Person EntityBean
     */
    public Person add( PersonDTO dto )
	{
        return add ( DTOFactory.copy( dto ) );
	}
    
    /**
     * Adiciona um pessoa (Usuario do tipo pessoa) no banco de dados. Esta funcao 
     * recebe como parâmetro uma Entity que não está gerenciado pelo EJB.
     * 
     * 
     * @param entity EntityBean Não gerenciado (Not Managed)
     * @return Person EntityBean
     */
    public Person add( Person entity )
    {
        String splitted[] = splitName( entity.getName() );
        entity.setFirstName ( splitted[0] );
        entity.setMiddleName( splitted[1] );
        entity.setLastName  ( splitted[2] );
        applyRules( entity );
        verifyDocuments( entity );
        em.persist( entity );
        return entity;
    }
    

    protected String[] splitName ( String name )
    {
        String splitted[] = name.split(" ");
        String firstName = null, lastName = null, middleName = null;
        int nIndex;
        
        
        for ( nIndex = 0; nIndex < splitted.length ; nIndex ++) {
            if ( nIndex == 0)
                firstName = splitted[ nIndex ];
            else if ( nIndex == splitted.length - 1 )
                lastName = splitted[ nIndex ];
            else {
                if ( middleName == null || middleName.isEmpty() )
                    middleName = splitted[ nIndex ];
                else
                    middleName += " " + splitted[ nIndex ];
            }
        }
        
        splitted = new String[3];
        splitted[0] = firstName;
        splitted[1] = middleName;
        splitted[2] = lastName;
        
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
		if ( person.getDocuments() != null ) {
			for ( UserDocument item : person.getDocuments() ) {
                if ( getUserSession().getUserByDocument( item  ) != null )
                    throw new EJBException( "Ja existe um usuário cadastrado com o mesmo documento [" + item.getCode() + "]" +
                                            ". O sistema não permite dois cadastros com os mesmos documentos" );
			}
		}
	}

	protected String toUpperCase( String fieldValue )
	{
		if ( fieldValue == null || fieldValue.isEmpty() )
			return fieldValue;
		return fieldValue.toUpperCase();
	}

	protected String toLowerCase( String fieldValue )
	{
		if ( fieldValue == null || fieldValue.isEmpty() )
			return fieldValue;
		return fieldValue.toLowerCase();
	}

	protected void applyRules( Person person )
	{
		person.setFatherName( toUpperCase( person.getFatherName() ) );
		person.setFirstName( toUpperCase( person.getFirstName() ) );
		person.setLastName( toUpperCase( person.getLastName() ) );
		person.setMotherName( toUpperCase( person.getMotherName() ) );
		person.setName( toUpperCase( person.getName() ) );
		person.setNickName( toUpperCase( person.getNickName() ) );
	}


    protected void mergeAddress ( Person person, List<AddressDTO> list )
    {
        int nIndex;
        
        for ( AddressDTO addressDTO: list )
        {
            Address address = DTOFactory.copy( addressDTO );
            address.setUser( person );
            nIndex = person.getAddresses().indexOf( address );
            if ( nIndex >= 0 ) 
            {
                Address merged = person.getAddresses().get ( nIndex );
                DTOFactory.copy( merged, addressDTO );
                merged.setCity( em.find ( City.class, addressDTO.getCity().getId() ) );
                merged.setAddressType( em.find ( AddressType.class, merged.getAddressType().getId() ) );
            }
            else
                person.addAddress( address );
        }
        ArrayList<Address> removeList = null;
        for ( Address entity: person.getAddresses() )  
        {
            AddressDTO ct = DTOFactory.copy( entity );
            if ( list.contains( ct ) == false )
            {
                if ( removeList == null )
                    removeList = new ArrayList<Address>();
                removeList.add( entity );
                em.remove( entity );
            }
        }
        if ( removeList != null && removeList.size() > 0 ) 
        {
            for ( Address entity: removeList ) 
            {
                person.removeAddress( entity );
            }
        }
    }



    protected void mergeDocuments ( Person person, List<UserDocumentDTO> list )
    {
        int nIndex;
        
        for ( UserDocumentDTO dtoItem: list )
        {
            UserDocument entity = DTOFactory.copy( dtoItem );
            entity.setUsers( person );
            nIndex = person.getDocuments().indexOf( entity );
            if ( nIndex >= 0 ) 
            {
                UserDocument merged = person.getDocuments().get ( nIndex );
                DTOFactory.copy( merged, dtoItem );
                merged.setDocumentType( em.find( DocumentType.class, merged.getDocumentType().getId() ) );
            }
            else
                person.addDocument( entity );
        }
        ArrayList<UserDocument> removeList = null;
        for ( UserDocument entity: person.getDocuments() )  
        {
            UserDocumentDTO ct = DTOFactory.copy( entity );
            if ( list.contains( ct ) == false )
            {
                if ( removeList == null )
                    removeList = new ArrayList<UserDocument>();
                removeList.add( entity );
                em.remove( entity );
            }
        }
        if ( removeList != null && removeList.size() > 0 ) 
        {
            for ( UserDocument entity: removeList ) 
            {
                person.removeDocument( entity );
            }
        }
    }

    protected void mergeContacts ( Person person, List<UserContactDTO> list )
    {
        int nIndex;
        
        for ( UserContactDTO dtoItem: list )
        {
            UserContact entity = DTOFactory.copy( dtoItem );
            entity.setUser( person );
            nIndex = person.getContacts().indexOf( entity );
            if ( nIndex >= 0 ) 
            {
                UserContact merged = person.getContacts().get ( nIndex );
                DTOFactory.copy( merged, dtoItem );
                merged.setContactType( em.find( ContactType.class, merged.getContactType().getId() ) );
            }
            else
                person.addContact( entity );
        }

        ArrayList<UserContact> removeList = null;
        for ( UserContact entity: person.getContacts() )  
        {
            UserContactDTO ct = DTOFactory.copy( entity );
            if ( list.contains( ct ) == false )
            {
                if ( removeList == null )
                    removeList = new ArrayList<UserContact>();
                removeList.add( entity );
                em.remove( entity );
            }
        }
        if ( removeList != null && removeList.size() > 0 ) 
        {
            for ( UserContact entity: removeList ) 
            {
                person.removeContact( entity );
            }
        }
    }

	public Person update( PersonDTO dto )
	{
		Person person;
        Login login;


	    person = em.find( Person.class, dto.getId() );
        if ( person == null ) 
            throw new EJBException ( "Não existe usuario para atualizar" );
		person = DTOFactory.copy( person, dto ); 
		applyRules( person );
        mergeAddress ( person, dto.getAddressList() );
        mergeDocuments ( person, dto.getDocumentList() );
        mergeContacts ( person, dto.getContactList() );
        login = person.getLogin();
        if ( login == null ) { 
            login = em.find ( Login.class, person.getId() );
        }
        if ( login != null ) {
            if ( login.getUserStatus().getId() == UserStatus.statusFullfillRecord)
                login.setUserStatus( em.find( UserStatus.class, UserStatus.statusOk ) );
            if ( person.getLogin() == null )
                person.setLogin( login );
        }
	    em.merge( person ); 
		return person;
	}

	public void delete( Integer id )
	{
		Person person;

		person = em.find( Person.class, id );
		em.remove( person );
	}

	public List<PersonDTO> getAll()
	{
		return getDTOList( ( ( List<Person> )em.createNamedQuery( "Person.findAll" ).getResultList() ) );
	}


	protected List<PersonDTO> getDTOList( List<Person> list )
	{
		List<PersonDTO> dtos;

		if ( list == null )
			return Collections.emptyList();
		dtos = new ArrayList<PersonDTO>( list.size() );
		for ( Person item : list )
			dtos.add( DTOFactory.copy( item, true ) );
		return dtos;
	}

	public PersonDTO getByDocument( String document )
	{
		Query query;
		UserDocument userDocument;
		Person person;
		PersonDTO dto = null;

		try {
			query = em.createNamedQuery( "UserDocument.findByDocument" );
			query.setParameter( "document", document );
			userDocument = ( UserDocument )query.getSingleResult();
			if ( userDocument == null )
				return null;
			person = em.find( Person.class, userDocument.getUserId() );
			if ( person != null )
				dto = DTOFactory.copy( person, true );
			return dto;
		}
		catch ( NoResultException e ) {
			return null;
		}
	}

	public PersonDTO get( Integer userId )
	{
		Person person;

		person = em.find( Person.class, userId );
		if ( person != null ) {
			if ( person.getUserType() != null )
				em.refresh( person.getUserType() );
			return DTOFactory.copy( person, true );
		}
		else
			return null;
	}
}
