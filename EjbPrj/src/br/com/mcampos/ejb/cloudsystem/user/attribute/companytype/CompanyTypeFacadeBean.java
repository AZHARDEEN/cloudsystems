package br.com.mcampos.ejb.cloudsystem.user.attribute.companytype;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CompanyTypeFacade", mappedName = "CloudSystems-EjbPrj-CompanyTypeFacade" )
@Remote
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CompanyTypeFacadeBean extends AbstractSecurity implements CompanyTypeFacade
{
    public static final Integer messageId = 21;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CompanyTypeSessionLocal session;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return null;
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        session.delete( id );
    }

    public CompanyTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        CompanyType entity = session.get( id );
        return CompanyTypeUtil.copy( entity );
    }

    public CompanyTypeDTO add( AuthenticationDTO auth, CompanyTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        CompanyType entity = session.get( dto.getId() );
        if ( entity != null )
            throwException( 1 );
        entity = session.add( CompanyTypeUtil.createEntity( dto ) );
        return CompanyTypeUtil.copy( entity );
    }

    public CompanyTypeDTO update( AuthenticationDTO auth, CompanyTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        CompanyType entity = session.get( dto.getId() );
        if ( entity == null )
            throwException( 2 );
        entity = session.add( CompanyTypeUtil.update( entity, dto ) );
        return CompanyTypeUtil.copy( entity );
    }

    public List<CompanyTypeDTO> getAll() throws ApplicationException
    {
        return CompanyTypeUtil.toDTOList( session.getAll() );
    }
}
