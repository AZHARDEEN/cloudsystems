package br.com.mcampos.ejb.cloudsystem.user.company.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface CompanySessionLocal extends Serializable
{
    Company get( Integer key ) throws ApplicationException;

    Company get( Company company ) throws ApplicationException;

    Company add( Company entity ) throws ApplicationException;

    Company update( Company entity ) throws ApplicationException;

    List<Company> getAll() throws ApplicationException;
}
