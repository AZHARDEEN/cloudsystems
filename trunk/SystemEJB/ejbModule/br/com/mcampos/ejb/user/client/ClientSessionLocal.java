package br.com.mcampos.ejb.user.client;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Local
public interface ClientSessionLocal extends CollaboratorBaseSessionInterface<Client>
{
	List<Client> getAllPerson( Collaborator auth, DBPaging paging );

	Long countPerson( Collaborator auth );

	List<Client> getAllCompany( Collaborator auth, DBPaging paging );

	Long countCompany( Collaborator auth );
}
