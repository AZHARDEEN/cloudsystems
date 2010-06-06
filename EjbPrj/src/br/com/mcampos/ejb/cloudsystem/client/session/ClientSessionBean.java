package br.com.mcampos.ejb.cloudsystem.client.session;


import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.entity.ClientPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ClientSession", mappedName = "CloudSystems-EjbPrj-ClientSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class ClientSessionBean extends Crud<ClientPK, Client> implements ClientSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    public ClientSessionBean()
    {
    }

    public void delete( ClientPK key ) throws ApplicationException
    {
        Client entity = get( key );
        /*
         * We cannot update a deleted client
         */
        if ( entity == null || entity.getEndDate() != null )
            return;
        entity.setEndDate( new Date() );
        update( entity );
    }

    public Client get( ClientPK key ) throws ApplicationException
    {
        return get( Client.class, key );
    }

    public List<Client> getAll( Company company ) throws ApplicationException
    {
        return ( List<Client> )getResultList( Client.getAll, company );
    }

    public List<Client> getAllCompanyClients( Company company ) throws ApplicationException
    {
        return ( List<Client> )getResultList( Client.getAllCompany, company );
    }

    public List<Client> getAllPersonClients( Company company ) throws ApplicationException
    {
        return ( List<Client> )getResultList( Client.getAllPerson );
    }
}
