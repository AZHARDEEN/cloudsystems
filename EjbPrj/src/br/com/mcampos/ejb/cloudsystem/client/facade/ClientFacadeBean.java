package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.client.ClientUtil;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.NewPersonSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.Person;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ClientFacade", mappedName = "CloudSystems-EjbPrj-ClientFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class ClientFacadeBean extends AbstractSecurity implements ClientFacade
{
    public static final Integer messageId = 16;

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    private ClientSessionLocal clientSession;

    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    public ClientFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    protected Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Person person;
        person = personSession.get( auth.getUserId() );
        if ( person == null )
            throwException( 1 );
        List<Collaborator> list = collaboratorSession.get( person );
        if ( SysUtils.isEmpty( list ) )
            throwException( 2 );
        /*
         * O colaborador possui vínculo ativo com mais de uma empresa
         */
        if ( list.size() > 1 ) {
            /*
             * TODO: vinculo com mais de uma empresa
             */
            throwException( 99 );
        }
        return list.get( 0 ).getCompany();
    }

    public List<ListUserDTO> getClients( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAll( company );
        return ClientUtil.toUserDTOList( list );
    }

    public List<ListUserDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAllCompanyClients( company );
        return ClientUtil.toUserDTOList( list );
    }

    public List<ListUserDTO> getPeople( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAllPersonClients( company );
        return ClientUtil.toUserDTOList( list );
    }
}
