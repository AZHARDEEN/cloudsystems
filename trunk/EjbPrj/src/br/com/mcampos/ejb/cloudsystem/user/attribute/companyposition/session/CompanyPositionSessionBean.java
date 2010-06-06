package br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.session;


import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.entity.CompanyPosition;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "CompanyPositionSession", mappedName = "CloudSystems-EjbPrj-CompanyPositionSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CompanyPositionSessionBean extends Crud<Integer, CompanyPosition> implements CompanyPositionSessionLocal
{
    public CompanyPositionSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( CompanyPosition.class, key );
    }

    public CompanyPosition get( Integer key ) throws ApplicationException
    {
        return get( CompanyPosition.class, key );
    }

    public List<CompanyPosition> getAll() throws ApplicationException
    {
        return getAll( CompanyPosition.getAll );
    }

    public Integer nextIntegerId() throws ApplicationException
    {
        return nextIntegerId( CompanyPosition.nextId );
    }
}
