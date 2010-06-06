package br.com.mcampos.ejb.cloudsystem.user.company.session;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Local;


@Local
public interface CompanySessionLocal
{
    Company get( Integer key ) throws ApplicationException;

    Company add( Company entity ) throws ApplicationException;

    Company update( Company entity ) throws ApplicationException;
}
