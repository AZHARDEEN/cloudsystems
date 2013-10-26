package br.com.mcampos.ejb.cloudsystem.user.attribute.companytype;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CompanyTypeSession", mappedName = "CompanyTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CompanyTypeSessionBean extends Crud<Integer, CompanyType> implements CompanyTypeSessionLocal
{
    public void delete( Integer key ) throws ApplicationException
    {
        delete( CompanyType.class, key );
    }

    public CompanyType get( Integer key ) throws ApplicationException
    {
        return get( CompanyType.class, key );
    }

    public List<CompanyType> getAll() throws ApplicationException
    {
        return getAll( CompanyType.getAll );
    }

    public Integer nextIntegerId() throws ApplicationException
    {
        return nextIntegerId( CompanyType.nextId );
    }
}
