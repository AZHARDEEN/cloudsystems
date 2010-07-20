package br.com.mcampos.ejb.cloudsystem.product.type.facade;


import br.com.mcampos.dto.product.ProductTypeDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.product.type.ProductTypeUtil;
import br.com.mcampos.ejb.cloudsystem.product.type.entity.ProductType;
import br.com.mcampos.ejb.cloudsystem.product.type.session.ProductTypeSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ProductTypeFacade", mappedName = "CloudSystems-EjbPrj-ProductTypeFacade" )
@TransactionAttribute( value = TransactionAttributeType.REQUIRES_NEW )
public class ProductTypeFacadeBean extends AbstractSecurity implements ProductTypeFacade
{
    public static final Integer messageId = 31;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;


    @EJB
    private ProductTypeSessionLocal session;

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
        return session.nextId();
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        ProductType type = session.get( id );
        if ( type == null )
            throwException( 1 );
        session.delete( type );
    }

    public ProductTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        ProductType type = session.get( id );
        if ( type == null )
            throwException( 1 );
        return ProductTypeUtil.copy( type );
    }

    public ProductTypeDTO add( AuthenticationDTO auth, ProductTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );

        ProductType type = session.get( dto.getId() );
        if ( type != null )
            throwException( 2 );
        type = ProductTypeUtil.createEntity( dto );
        type = session.add( type );
        return ProductTypeUtil.copy( type );
    }

    public ProductTypeDTO update( AuthenticationDTO auth, ProductTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        ProductType type = session.get( dto.getId() );
        if ( type == null )
            throwException( 1 );
        ProductTypeUtil.update( type, dto );
        return ProductTypeUtil.copy( session.update( type ) );
    }

    public List<ProductTypeDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        List<ProductType> list = session.getAll();
        return ProductTypeUtil.toDTOList( list );
    }
}
