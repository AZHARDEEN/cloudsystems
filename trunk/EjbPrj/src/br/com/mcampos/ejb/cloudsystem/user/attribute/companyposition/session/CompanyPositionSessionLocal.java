package br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.session;


import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.entity.CompanyPosition;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CompanyPositionSessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    CompanyPosition get( Integer key ) throws ApplicationException;

    List<CompanyPosition> getAll() throws ApplicationException;

    Integer nextIntegerId() throws ApplicationException;

    CompanyPosition add( CompanyPosition entity ) throws ApplicationException;

    CompanyPosition update( CompanyPosition entity ) throws ApplicationException;
}
