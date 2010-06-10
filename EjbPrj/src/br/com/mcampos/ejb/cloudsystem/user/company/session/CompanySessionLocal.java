package br.com.mcampos.ejb.cloudsystem.user.company.session;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface CompanySessionLocal extends Serializable
{
    Company get( Integer key ) throws ApplicationException;

    Company get( Company company ) throws ApplicationException;

    Company add( Company entity ) throws ApplicationException;

    Company update( Company entity ) throws ApplicationException;
}
