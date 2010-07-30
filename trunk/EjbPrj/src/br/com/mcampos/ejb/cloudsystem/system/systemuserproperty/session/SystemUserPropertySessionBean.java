package br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.session;


import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity.SystemUserProperty;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "SystemUserPropertySession", mappedName = "CloudSystems-EjbPrj-SystemUserPropertySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class SystemUserPropertySessionBean extends Crud<Integer, SystemUserProperty> implements SystemUserPropertySessionLocal
{

    public void delete( Integer key ) throws ApplicationException
    {
        delete( SystemUserProperty.class, key );
    }

    public SystemUserProperty get( Integer key ) throws ApplicationException
    {
        return get( SystemUserProperty.class, key );
    }

    public List<SystemUserProperty> getAll() throws ApplicationException
    {
        return ( List<SystemUserProperty> )getResultList( SystemUserProperty.getAll );
    }

    public Integer nextIntegerId() throws ApplicationException
    {
        return nextIntegerId( SystemUserProperty.nextId );
    }
}
