package br.com.mcampos.ejb.cloudsystem.user.company.session;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CompanySession", mappedName = "CloudSystems-EjbPrj-CompanySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CompanySessionBean extends Crud<Integer, Company> implements CompanySessionLocal
{
    @Override
    public Company get( Integer key ) throws ApplicationException
    {
        return get( Company.class, key );
    }

    @Override
    public Company add( Company entity ) throws ApplicationException
    {
        return super.add( entity );
    }

    @Override
    public Company update( Company entity ) throws ApplicationException
    {
        return super.update( entity );
    }
}
