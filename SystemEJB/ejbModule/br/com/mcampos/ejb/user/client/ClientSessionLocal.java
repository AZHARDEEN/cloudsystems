package br.com.mcampos.ejb.user.client;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.person.Person;

@Local
public interface ClientSessionLocal extends CollaboratorBaseSessionInterface<Client>
{
	List<Client> getAllPerson( Collaborator auth, DBPaging paging );

	Long countPerson( Collaborator auth );

	List<Client> getAllCompany( Collaborator auth, DBPaging paging );

	Long countCompany( Collaborator auth );

	public Users getUser( Collaborator auth, String document );

	public Users getUser( Collaborator auth, Integer id );

	Client getClient( Collaborator auth, String document );

	Client getClient( Collaborator auth, Integer id );

	Client getClient( Collaborator auth, Users user );

	Client addNewPerson( Collaborator auth, Client newEntity );

	Client updatePerson( Collaborator auth, Client newEntity );

	Client addNewCompany( Collaborator auth, Client newEntity );

	Client updateCompany( Collaborator auth, Client newEntity );

	DocumentType getDocumentType( Integer type );

	List<Person> reportClientList( Collaborator auth );
}
