package br.com.mcampos.ejb.cloudsystem.user.company.role.session;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.role.entity.CompanyRole;
import br.com.mcampos.ejb.cloudsystem.user.company.role.entity.CompanyRolePK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CompanyRoleSession", mappedName = "CloudSystems-EjbPrj-CompanyRoleSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CompanyRoleSessionBean extends Crud<CompanyRolePK, CompanyRole> implements CompanyRoleSessionLocal
{
    public void delete( CompanyRolePK key ) throws ApplicationException
    {
        delete( CompanyRole.class, key );
    }

    public CompanyRole get( CompanyRolePK key ) throws ApplicationException
    {
        return get( CompanyRole.class, key );
    }

    public List<CompanyRole> getAll( Company company ) throws ApplicationException
    {
        return ( List<CompanyRole> )getResultList( CompanyRole.getAll, company );
    }

    @Override
    public CompanyRole add( CompanyRole entity ) throws ApplicationException
    {
        CompanyRole role = super.add( entity );

        return role;
    }
}
