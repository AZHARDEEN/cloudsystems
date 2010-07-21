package br.com.mcampos.ejb.cloudsystem.product.session;


import br.com.mcampos.ejb.cloudsystem.product.entity.Product;
import br.com.mcampos.ejb.cloudsystem.product.entity.ProductPK;
import br.com.mcampos.ejb.cloudsystem.product.type.session.ProductTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless( name = "ProductSession", mappedName = "CloudSystems-EjbPrj-ProductSession" )
@Local
public class ProductSessionBean extends Crud<ProductPK, Product> implements ProductSessionLocal
{
    @EJB
    private ProductTypeSessionLocal typeSession;

    public void delete( ProductPK key ) throws ApplicationException
    {
        Product entity = get( key );
        if ( entity != null )
            entity.setToDate( new Date() );
    }

    public Product get( ProductPK key ) throws ApplicationException
    {
        return get( Product.class, key );
    }

    public List<Product> getAll( Company company ) throws ApplicationException
    {
        return ( List<Product> )getResultList( Product.getAll, company );
    }

    public Integer nextId( Company company ) throws ApplicationException
    {
        return nextIntegerId( Product.nextId, company );
    }

    @Override
    public Product add( Product entity ) throws ApplicationException
    {
        if ( entity == null )
            return null;
        if ( entity.getType() != null )
            entity.setType( typeSession.get( entity.getType().getId() ) );
        else
            entity.setType( typeSession.get( 1 ) );
        if ( entity.getFromDate() == null )
            entity.setFromDate( new Date() );
        return super.add( entity );
    }
}
