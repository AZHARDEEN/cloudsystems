package br.com.mcampos.ejb.cloudsystem.user.collaborator.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.CollaboratorUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
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


@Stateless( name = "CollaboratorFacade", mappedName = "CloudSystems-EjbPrj-CollaboratorFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CollaboratorFacadeBean extends AbstractSecurity implements CollaboratorFacade
{
    public static final Integer messageId = 28;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private NewPersonSessionLocal personSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public List<CompanyDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Person person = personSession.get( auth.getUserId() );
        if ( person == null )
            throwException( 1 );
        List<Collaborator> list = collaboratorSession.get( person );
        return CollaboratorUtil.toCompanyDTOList( list );
    }
}
