package br.com.mcampos.ejb.cloudsystem.resale.dealer.type.session;


import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.entity.DealerType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "DealerTypeSession", mappedName = "CloudSystems-EjbPrj-DealerTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class DealerTypeSessionBean extends Crud<Integer, DealerType> implements DealerTypeSessionLocal
{

    public void delete( Integer key ) throws ApplicationException
    {
        super.delete( DealerType.class, key );
    }

    public DealerType get( Integer key ) throws ApplicationException
    {
        return get( DealerType.class, key );
    }

    public List<DealerType> getAll() throws ApplicationException
    {
        return ( List<DealerType> )getResultList( DealerType.getAll );
    }

    public Integer nextId() throws ApplicationException
    {
        return nextIntegerId( DealerType.nextId );
    }
}
