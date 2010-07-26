package br.com.mcampos.ejb.cloudsystem.resale.dealer.facade;


import br.com.mcampos.dto.resale.DealerDTO;
import br.com.mcampos.dto.resale.DealerTypeDTO;
import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.ejb.cloudsystem.client.ClientUtil;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.resale.ResaleUtil;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.DealerUtil;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.entity.Dealer;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.session.DealerSessionLocal;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.DealerTypeUtil;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.session.DealerTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.resale.session.ResaleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "DealerFacade", mappedName = "CloudSystems-EjbPrj-DealerFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class DealerFacadeBean extends AbstractSecurity implements DealerFacade
{
    public static final Integer messageId = 37;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private ResaleSessionLocal resaleSession;

    @EJB
    private DealerSessionLocal session;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    private DealerTypeSessionLocal typeSession;

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


    private Resale getResale( AuthenticationDTO auth, ResaleDTO resale ) throws ApplicationException
    {
        Company owner = getCompany( auth );
        Resale r = resaleSession.get( owner, resale.getSequence() );
        if ( r == null )
            throwException( 2 );
        return r;
    }

    private Person getPerson( Integer personId ) throws ApplicationException
    {
        Person p = personSession.get( personId );
        if ( p == null )
            throwException( 3 );
        return p;
    }

    public void delete( AuthenticationDTO auth, ResaleDTO resale, Integer sequence ) throws ApplicationException
    {
        Resale r = getResale( auth, resale );
        session.delete( r, sequence );
    }

    public DealerDTO get( AuthenticationDTO auth, ResaleDTO resale, Integer sequence ) throws ApplicationException
    {
        Resale r = getResale( auth, resale );
        Dealer dealer = session.get( r, sequence );
        return DealerUtil.copy( dealer );
    }

    public DealerDTO add( AuthenticationDTO auth, DealerDTO dto ) throws ApplicationException
    {
        Resale resale = getResale( auth, dto.getResale() );
        Person person = getPerson( dto.getPerson().getId() );
        Dealer dealer = session.get( person );
        if ( dealer != null )
            throwException( 4 );
        dealer = DealerUtil.createEntity( resale, person, dto );
        return DealerUtil.copy( session.add( dealer ) );
    }

    public DealerDTO update( AuthenticationDTO auth, DealerDTO dto ) throws ApplicationException
    {
        Resale resale = getResale( auth, dto.getResale() );
        Dealer dealer = session.get( resale, dto.getSequence() );
        if ( dealer == null ) {
            throwException( 5 );
        }
        Person person = getPerson( dto.getPerson().getId() );
        if ( person.equals( dealer.getDealer() ) == false ) {
            /*Are you changing dealer????*/
            session.delete( dealer );
            return add( auth, dto );
        }
        else {
            /*
           * Be carefull, we DON´T change person during update
           */
            DealerUtil.update( dealer, dto );
            dealer = session.update( dealer );
            return DealerUtil.copy( dealer );
        }
    }

    public List<DealerDTO> getAll( AuthenticationDTO auth, ResaleDTO resale ) throws ApplicationException
    {
        Resale r = getResale( auth, resale );
        return DealerUtil.toDTOList( session.getAll( r ) );
    }


    public List<DealerTypeDTO> getTypes() throws ApplicationException
    {
        return DealerTypeUtil.toDTOList( typeSession.getAll() );
    }

    public List<ClientDTO> getPeople( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAllPersonClients( company );
        return ClientUtil.toDTOList( list );
    }

    public List<ResaleDTO> getResales( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        return ResaleUtil.toDTOList( resaleSession.getAll( company ) );
    }
}
