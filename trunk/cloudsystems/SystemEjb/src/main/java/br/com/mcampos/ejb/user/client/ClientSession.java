package br.com.mcampos.ejb.user.client;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.DocumentType;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.Users;

@Remote
public interface ClientSession extends CollaboratorBaseSessionInterface<Client>
{
	List<Client> getAllPerson( PrincipalDTO auth, DBPaging paging );

	Long countPerson( PrincipalDTO auth );

	List<Client> getAllCompany( PrincipalDTO auth, DBPaging paging );

	Long countCompany( PrincipalDTO auth );

	public Users getUser( PrincipalDTO auth, String document );

	public Users getUser( PrincipalDTO auth, Integer id );

	Client addNewPerson( PrincipalDTO auth, Client newEntity );

	Client updatePerson( PrincipalDTO auth, Client newEntity );

	Client addNewCompany( PrincipalDTO auth, Client newEntity );

	Client updateCompany( PrincipalDTO auth, Client newEntity );

	DocumentType getDocumentType( Integer type );

	List<Person> reportClientList( PrincipalDTO auth );
}