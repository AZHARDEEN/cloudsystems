package br.com.mcampos.ejb.cloudsystem.resale.facade;


import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.ejb.cloudsystem.client.ClientUtil;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.resale.ResaleUtil;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.resale.session.ResaleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ResaleFacade", mappedName = "CloudSystems-EjbPrj-ResaleFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class ResaleFacadeBean extends AbstractSecurity implements ResaleFacade
{
    public static final Integer messageId = 36;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private ResaleSessionLocal session;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private ClientSessionLocal clientSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    private Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        if ( company == null )
            throwException( 1 );
        return company;
    }

    private Client getClient( Company company, Integer clientId ) throws ApplicationException
    {
        Company clientCompany = companySession.get( clientId );
        if ( clientCompany == null )
            throwException( 2 );
        Client client = clientSession.get( company, clientCompany );
        if ( client == null )
            throwException( 3 );
        return client;
    }


    public void delete( AuthenticationDTO auth, Integer sequence ) throws ApplicationException
    {
        session.delete( getCompany( auth ), sequence );
    }

    public ResaleDTO get( AuthenticationDTO auth, Integer sequence ) throws ApplicationException
    {
        return ResaleUtil.copy( session.get( getCompany( auth ), sequence ) );
    }

    public ResaleDTO add( AuthenticationDTO auth, ResaleDTO dto ) throws ApplicationException
    {
        Company company = getCompany( auth );
        Client client = getClient( company, dto.getResale().getClient().getId() );
        Resale entity = session.get( company, client );
        if ( entity != null )
            throwException( 4 );
        entity = ResaleUtil.createEntity( company, dto );
        entity.setResale( client );
        return ResaleUtil.copy( session.add( entity ) );
    }

    public ResaleDTO update( AuthenticationDTO auth, ResaleDTO dto ) throws ApplicationException
    {
        Company company = getCompany( auth );
        Resale entity = session.get( company, dto.getSequence() );
        if ( entity == null )
            throwException( 1 );
        entity.setResale( getClient( company, dto.getResale().getClient().getId() ) );
        ResaleUtil.update( entity, dto );
        return ResaleUtil.copy( session.update( entity ) );
    }


    public List<ResaleDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Resale> list = session.getAll( company );
        return ResaleUtil.toDTOList( list );
    }


    public List<ClientDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAllCompanyClients( company );
        return ClientUtil.toDTOList( list );
    }

}
