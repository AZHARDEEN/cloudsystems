package br.com.mcampos.ejb.cloudsystem.client;


import br.com.mcampos.ejb.entity.user.Company;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface ClientSessionLocal
{
    Client add( Client entity ) throws ApplicationException;

    void delete( ClientPK key ) throws ApplicationException;

    Client get( ClientPK key ) throws ApplicationException;

    List<Client> getAll( Company company ) throws ApplicationException;

    List<Client> getAllCompanyClients( Company company ) throws ApplicationException;

    List<Client> getAllPersonClients( Company company ) throws ApplicationException;
}
