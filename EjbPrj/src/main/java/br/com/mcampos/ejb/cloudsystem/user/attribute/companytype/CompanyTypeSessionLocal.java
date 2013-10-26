package br.com.mcampos.ejb.cloudsystem.user.attribute.companytype;


import br.com.mcampos.dto.ApplicationException;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface CompanyTypeSessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    CompanyType get( Integer key ) throws ApplicationException;

    List<CompanyType> getAll() throws ApplicationException;

    Integer nextIntegerId() throws ApplicationException;

    CompanyType add( CompanyType entity ) throws ApplicationException;

    CompanyType update( CompanyType entity ) throws ApplicationException;
}
