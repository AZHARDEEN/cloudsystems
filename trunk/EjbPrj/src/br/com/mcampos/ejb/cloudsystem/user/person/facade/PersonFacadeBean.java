package br.com.mcampos.ejb.cloudsystem.user.person.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "PersonFacade", mappedName = "CloudSystems-EjbPrj-PersonFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class PersonFacadeBean extends AbstractSecurity implements PersonFacade
{

    public static final Integer messageId = 22;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private NewPersonSessionLocal session;

    public PersonFacadeBean()
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

    public PersonDTO get( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Person person = session.get( auth.getUserId() );
        return PersonUtil.copy( person );
    }
}
