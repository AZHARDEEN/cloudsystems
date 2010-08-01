package br.com.mcampos.ejb.cloudsystem.account.mask.session;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "AccountingMaskSession", mappedName = "CloudSystems-EjbPrj-AccountingMaskSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AccountingMaskSessionBean extends Crud<Integer, AccountingMask> implements AccountingMaskSessionLocal
{
    public void set( Company company, String mask ) throws ApplicationException
    {
        AccountingMask entity = get( AccountingMask.class, company.getId() );
        if ( entity == null ) {
            if ( SysUtils.isEmpty( mask ) == false ) {
                entity = new AccountingMask( mask, company.getId() );
                entity.setCompany( company );
                add( entity );
            }
        }
        else {
            if ( SysUtils.isEmpty( mask ) == false )
                entity.setMask( mask );
            else
                delete( entity );
        }
    }

    public AccountingMask get( Company company ) throws ApplicationException
    {
        return get( AccountingMask.class, company.getId() );
    }
}
