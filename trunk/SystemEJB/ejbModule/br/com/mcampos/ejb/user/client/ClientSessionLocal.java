package br.com.mcampos.ejb.user.client;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;

@Local
public interface ClientSessionLocal extends CollaboratorBaseSessionInterface<Client>
{
	List<Client> getAllPerson( Authentication auth, DBPaging paging );

	Long countPerson( Authentication auth );

	List<Client> getAllCompany( Authentication auth, DBPaging paging );

	Long countCompany( Authentication auth );
}
