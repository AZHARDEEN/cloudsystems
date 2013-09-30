package br.com.mcampos.ejb.user.client;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.DocumentType;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.Users;

@Local
public interface ClientSessionLocal extends CollaboratorBaseSessionInterface<Client>
{
	List<Client> getAllPerson( PrincipalDTO auth, DBPaging paging );

	Long countPerson( PrincipalDTO auth );

	List<Client> getAllCompany( PrincipalDTO auth, DBPaging paging );

	Long countCompany( PrincipalDTO auth );

	public Users getUser( PrincipalDTO auth, String document );

	public Users getUser( PrincipalDTO auth, Integer id );

	Client getClient( PrincipalDTO auth, String document );

	Client getClient( PrincipalDTO auth, Integer id );

	Client getClient( PrincipalDTO auth, Users user );

	Client addNewPerson( PrincipalDTO auth, Client newEntity );

	Client updatePerson( PrincipalDTO auth, Client newEntity );

	Client addNewCompany( PrincipalDTO auth, Client newEntity );

	Client add( PrincipalDTO auth, Users newClient );

	Client updateCompany( PrincipalDTO auth, Client newEntity );

	DocumentType getDocumentType( Integer type );

	List<Person> reportClientList( PrincipalDTO auth );

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
	Client get( PrincipalDTO auth, String internalCode );
}
