package br.com.mcampos.ejb.security.register;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.security.LoginSessionLocal;
import br.com.mcampos.ejb.security.UserStatusSessionLocal;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.ejb.user.document.type.DocumentTypeSessionLocal;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.jpa.user.Users;
import br.com.mcampos.sysutils.CPF;

/**
 * Session Bean implementation class RegisterSessionBean
 */
@Stateless( name = "RegisterSession", mappedName = "RegisterSession" )
@LocalBean
public class RegisterSessionBean extends BaseSessionBean implements RegisterSession
{
	@Resource
	SessionContext sessionContext;

	@EJB
	UserDocumentSessionLocal userDocumentSession;

	@EJB
	LoginSessionLocal loginSession;

	@EJB
	DocumentTypeSessionLocal documentTypeSession;

	@EJB
	UserStatusSessionLocal userStatus;

	@EJB
	PersonSessionLocal personSession;

	@Override
	public Boolean register( RegisterDTO dto ) throws Exception
	{
		if ( dto == null ) {
			return false;
		}
		if ( this.hasLogin( dto ) ) {
			/*
			 * Neste ponto nao deveria haver usuário com o documento (email ou
			 * cpf)
			 */
			return false;
		}
		Person person = this.getPersonByDocument( dto );
		boolean emailFound = false, cpfFound = false;
		if ( person == null ) {
			person = new Person( );
			person.setName( dto.getName( ) );
			person = this.personSession.merge( person );
			// this.userDocumentSession.merge( person.getDocuments( ) );
		}
		else {
			for ( UserDocument doc : person.getDocuments( ) ) {
				switch ( doc.getType( ).getId( ) ) {
				case UserDocument.EMAIL:
					doc.setCode( dto.getEmail( ) );
					emailFound = true;
					break;
				case UserDocument.CPF:
					doc.setCode( dto.getDocument( ) );
					cpfFound = true;
					break;
				}
			}
		}
		UserDocument document;
		if ( emailFound == false ) {
			document = new UserDocument( dto.getEmail( ), this.documentTypeSession.get( ( UserDocument.EMAIL ) ) );
			person.add( document );
		}
		if ( cpfFound == false ) {
			document = new UserDocument( dto.getDocument( ), this.documentTypeSession.get( ( UserDocument.CPF ) ) );
			person.add( document );
		}
		return this.loginSession.add( person, dto.getPassword( ) ) != null;
	}

	private UserDocumentSessionLocal getUserDocumentSession( )
	{
		return this.userDocumentSession;
	}

	@Override
	public Boolean hasLogin( RegisterDTO dto ) throws Exception
	{
		if ( dto == null ) {
			return false;
		}

		Users userA = this.getUserDocumentSession( ).getUserByDocument( dto.getEmail( ) );
		if ( userA != null && this.getLoginSession( ).get( userA.getId( ) ) != null ) {
			return true;
		}
		Users userB = this.getUserDocumentSession( ).getUserByDocument( CPF.removeMask( dto.getDocument( ) ) );
		if ( userB != null && this.getLoginSession( ).get( userA.getId( ) ) != null ) {
			return true;
		}
		if ( userA != null && userB != null ) {
			/**
			 * Os documentos remetem a usuários diferentes
			 */
			if ( userA.equals( userB ) == false ) {
				throw new Exception( "O CPF e/ou email inválido" );
			}
		}
		return false;
	}

	private Person getPersonByDocument( RegisterDTO dto ) throws Exception
	{
		Users userA = this.getUserDocumentSession( ).getUserByDocument( dto.getEmail( ) );
		Users userB = this.getUserDocumentSession( ).getUserByDocument( CPF.removeMask( dto.getDocument( ) ) );

		if ( userA != null && userB != null ) {
			/**
			 * Os documentos remetem a usuários diferentes
			 */
			if ( userA.equals( userB ) == false ) {
				throw new Exception( "O CPF e/ou email inválido" );
			}
		}
		if ( userA != null && ( userA instanceof Person ) ) {
			return (Person) userA;
		}
		if ( userB != null && ( userB instanceof Person ) ) {
			return (Person) userB;
		}
		return null;
	}

	private LoginSessionLocal getLoginSession( )
	{
		return this.loginSession;
	}

	@Override
	public Boolean validate( String token, String password ) throws Exception
	{
		return this.loginSession.validateToken( token, password );
	}
}
