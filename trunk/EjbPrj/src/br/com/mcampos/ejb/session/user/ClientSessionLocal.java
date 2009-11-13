package br.com.mcampos.ejb.session.user;

import br.com.mcampos.ejb.entity.user.Client;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ClientSessionLocal
{
    Client persistClients( Client clients );

    Client mergeClients( Client clients );

    void removeClients( Client clients );

    List<Client> getClientsByCriteria( String jpqlStmt, int firstResult, int maxResults );

    List<Client> getClientsFindAll();

    List<Client> getClientsFindAllByRange( int firstResult, int maxResults );

    List<Client> getClientsFindAllActive( Integer owner );

    List<Client> getClientsFindAllActiveByRange( Integer owner, int firstResult, int maxResults );

    Client getClientsFind( Integer owner, Integer client );
    
    Long getCount ( Integer owner );
}
