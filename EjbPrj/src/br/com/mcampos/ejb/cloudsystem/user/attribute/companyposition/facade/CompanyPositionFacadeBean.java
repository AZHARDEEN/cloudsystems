package br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.CompanyPositionUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.entity.CompanyPosition;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.session.CompanyPositionSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CompanyPositionFacade", mappedName = "CloudSystems-EjbPrj-CompanyPositionFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CompanyPositionFacadeBean extends AbstractSecurity implements CompanyPositionFacade
{
    public static final Integer messageId = 21;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CompanyPositionSessionLocal session;

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

    public CompanyPositionDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        CompanyPosition entity = session.get( id );
        return CompanyPositionUtil.copy( entity );
    }

    public CompanyPositionDTO add( AuthenticationDTO auth, CompanyPositionDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        CompanyPosition entity = session.get( dto.getId() );
        if ( entity != null )
            throwException( 1 );
        entity = session.add( CompanyPositionUtil.createEntity( dto ) );
        return CompanyPositionUtil.copy( entity );
    }

    public CompanyPositionDTO update( AuthenticationDTO auth, CompanyPositionDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        CompanyPosition entity = session.get( dto.getId() );
        if ( entity == null )
            throwException( 2 );
        entity = session.add( CompanyPositionUtil.update( entity, dto ) );
        return CompanyPositionUtil.copy( entity );
    }

    public List<CompanyPositionDTO> getAll() throws ApplicationException
    {
        return CompanyPositionUtil.toDTOList( session.getAll() );
    }
}
