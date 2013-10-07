package br.com.mcampos.ejb.user.person;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.user.BaseUserSession;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.jpa.security.UserStatus;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserType;
import br.com.mcampos.jpa.user.Users;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "PersonSession", mappedName = "PersonSession" )
public class PersonSessionBean extends BaseUserSession<Person> implements PersonSession, PersonSessionLocal
{
	private static final long serialVersionUID = 105019512338763383L;

	@EJB
	UserDocumentSessionLocal userDocumentSession;

	@Override
	protected Class<Person> getEntityClass( )
	{
		return Person.class;
	}

	@Override
	@Deprecated
	public Person merge( Person newEntity )
	{
		this.configureDefault( newEntity );
		newEntity = super.merge( newEntity );
		this.lazyLoad( newEntity );
		return newEntity;
	}

	public String[ ] splitName( String name )
	{
		if ( SysUtils.isEmpty( name ) ) {
			return null;
		}
		String splitted[] = name.split( " " );
		String firstName = null, lastName = null, middleName = null;
		int nIndex;

		for ( nIndex = 0; nIndex < splitted.length; nIndex++ ) {
			if ( nIndex == 0 ) {
				firstName = splitted[ nIndex ];
			}
			else if ( nIndex == splitted.length - 1 ) {
				lastName = splitted[ nIndex ];
			}
			else {
				if ( middleName == null || middleName.isEmpty( ) ) {
					middleName = splitted[ nIndex ];
				}
				else {
					middleName += " " + splitted[ nIndex ];
				}
			}
		}

		splitted = new String[ 3 ];
		splitted[ 0 ] = firstName;
		splitted[ 1 ] = middleName;
		splitted[ 2 ] = lastName;

		return splitted;
	}

	@Override
	public Person get( Serializable key )
	{
		Person p = super.get( key );
		if ( p != null ) {
			this.lazyLoad( p );
		}
		return p;
	}

	@Override
	public Person getByDocument( String document )
	{
		return (Person) this.userDocumentSession.getUserByDocument( document );
	}

	private void configureDefault( Person newEntity )
	{
		super.configureDefault( (Users) newEntity );
		if ( newEntity.getUserType( ) == null ) {
			newEntity.setUserType( this.getEntityManager( ).find( UserType.class, UserType.typePerson ) );
		}
		if ( SysUtils.isEmpty( newEntity.getFatherName( ) ) == false ) {
			newEntity.setFatherName( SysUtils.unaccent( newEntity.getFatherName( ).toUpperCase( ) ) );
		}
		if ( SysUtils.isEmpty( newEntity.getMotherName( ) ) == false ) {
			newEntity.setMotherName( SysUtils.unaccent( newEntity.getMotherName( ).toUpperCase( ) ) );
		}
		String[ ] s = this.splitName( newEntity.getName( ) );
		newEntity.setFirstName( s[ 0 ] );
		newEntity.setMiddleName( s[ 1 ] );
		newEntity.setLastName( s[ 2 ] );
	}

	@Override
	public Person add( Person newEntity )
	{
		this.configureDefault( newEntity );
		newEntity = super.add( newEntity );
		this.lazyLoad( newEntity );
		return newEntity;
	}

	@Override
	public Person update( PrincipalDTO auth, Person newEntity )
	{
		this.configureDefault( newEntity );
		newEntity = super.update( auth, newEntity );
		this.lazyLoad( newEntity );
		this.verifyLoginStatus( auth, newEntity );
		return newEntity;
	}

	private void verifyLoginStatus( PrincipalDTO auth, Person newEntity )
	{
		if ( newEntity.getId( ).equals( auth.getUserId( ) ) ) {
			Login login = this.getEntityManager( ).find( Login.class, newEntity.getId( ) );
			if ( login != null && login.getStatus( ).getId( ).equals( UserStatus.statusFullfillRecord ) ) {
				login.setStatus( this.getEntityManager( ).find( UserStatus.class, UserStatus.statusOk ) );
			}
		}
	}
}
