package br.com.mcampos.ejb.cloudsystem.product.facade;


import br.com.mcampos.dto.product.ProductDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.product.ProductUtil;
import br.com.mcampos.ejb.cloudsystem.product.entity.Product;
import br.com.mcampos.ejb.cloudsystem.product.entity.ProductPK;
import br.com.mcampos.ejb.cloudsystem.product.session.ProductSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ProductFacade", mappedName = "CloudSystems-EjbPrj-ProductFacade" )
@Remote
public class ProductFacadeBean extends AbstractSecurity implements ProductFacade
{
    protected static final int messageTypeId = 34;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private ProductSessionLocal productSession;

    @EJB
    private CompanySessionLocal companySession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageTypeId;
    }

    private Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        if ( company == null )
            throwException( 1 );
        return company;
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        return productSession.nextId( getCompany( auth ) );
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        ProductPK key = new ProductPK( getCompany( auth ), id );
        productSession.delete( key );
    }

    public ProductDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        ProductPK key = new ProductPK( getCompany( auth ), id );
        Product prd = productSession.get( key );
        return ProductUtil.copy( prd );
    }

    public ProductDTO add( AuthenticationDTO auth, ProductDTO dto ) throws ApplicationException
    {
        Company company = getCompany( auth );
        ProductPK key = new ProductPK( company, dto.getId() );
        Product entity = productSession.get( key );
        if ( entity != null )
            throwException( 2 );
        entity = ProductUtil.createEntity( dto, company );
        entity = productSession.add( entity );
        return ProductUtil.copy( entity );
    }

    public ProductDTO update( AuthenticationDTO auth, ProductDTO dto ) throws ApplicationException
    {
        Company company = getCompany( auth );
        ProductPK key = new ProductPK( company, dto.getId() );
        Product entity = productSession.get( key );
        if ( entity == null )
            throwException( 3 );
        entity = ProductUtil.update( entity, dto );
        entity = productSession.update( entity );
        return ProductUtil.copy( entity );
    }

    public List<ProductDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Product> entities = productSession.getAll( company );
        return ProductUtil.toDTOList( entities );
    }
}
