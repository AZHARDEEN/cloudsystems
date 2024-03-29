package br.com.mcampos.ejb.cloudsystem.client.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.entity.ClientPK;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "ClientSession", mappedName = "ClientSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class ClientSessionBean extends Crud<ClientPK, Client> implements ClientSessionLocal
{

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
        return ( List<Client> )getResultList( Client.getAllPerson, company );
    }

    @Override
    public Client add( Client entity ) throws ApplicationException
    {
        Client newClient = getClient( entity.getCompany(), entity.getClient() );
        if ( newClient != null )
            return newClient;
        entity.setClientId( nextIntegerId( entity.getCompany() ) );
        entity.setInsertDate( new Date() );
        newClient = super.add( entity );
        return newClient;
    }

    private Integer nextIntegerId( Company company ) throws ApplicationException
    {
        Integer id = ( Integer )getSingleResult( Client.nextId, company );
        if ( id == null )
            id = 1;
        id++;
        return id;
    }


    private Client getClient( Company company, Users client ) throws ApplicationException
    {
        List<Object> param = new ArrayList<Object>( 2 );
        param.add( company );
        param.add( client );
        Client c = ( Client )getSingleResult( Client.getClient, param );
        return c;
    }

    public Client get( Company myCompany, Users client ) throws ApplicationException
    {
        return getClient( myCompany, client );
    }

    public Client getSponsor( Company client ) throws ApplicationException
    {
        List<Client> clients = ( List<Client> )getResultList( Client.getSponsor, client );
        if ( SysUtils.isEmpty( clients ) )
            return null;
        /*
         * ATENÇÃO: ainda não está definido como tratar uma empresa (cliente) que possui mais de um
         * patrocinador. Atualmente esta função está sendo usada somente para obter o logo.
         */
        if ( clients.size() > 1 )
            return null;
        return clients.get( 0 );
    }
}
