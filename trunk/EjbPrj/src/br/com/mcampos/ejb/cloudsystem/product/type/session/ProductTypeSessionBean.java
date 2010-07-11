package br.com.mcampos.ejb.cloudsystem.product.type.session;


import br.com.mcampos.ejb.cloudsystem.product.type.entity.ProductType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "ProductTypeSession", mappedName = "CloudSystems-EjbPrj-ProductTypeSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class ProductTypeSessionBean extends Crud<Integer, ProductType> implements ProductTypeSessionLocal
{
    public void delete( Integer key ) throws ApplicationException
    {
        super.delete( ProductType.class, key );
    }

    public ProductType get( Integer key ) throws ApplicationException
    {
        return super.get( ProductType.class, key );
    }

    public List<ProductType> getAll() throws ApplicationException
    {
        return ( List<ProductType> )getResultList( ProductType.getAll );
    }

    public Integer nextId() throws ApplicationException
    {
        return nextIntegerId( ProductType.nextId );
    }
}
