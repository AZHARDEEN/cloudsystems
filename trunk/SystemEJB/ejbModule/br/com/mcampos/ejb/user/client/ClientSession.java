package br.com.mcampos.ejb.user.client;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.Users;

@Remote
public interface ClientSession extends CollaboratorBaseSessionInterface<Client>
{
	List<Client> getAllPerson( Authentication auth, DBPaging paging );

	Long countPerson( Authentication auth );

	List<Client> getAllCompany( Authentication auth, DBPaging paging );

	Long countCompany( Authentication auth );

	public Users getUser( Authentication auth, String document );

	public Users getUser( Authentication auth, Integer id );

}
