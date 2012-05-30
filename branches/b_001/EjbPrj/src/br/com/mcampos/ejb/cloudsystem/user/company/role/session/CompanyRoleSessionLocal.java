package br.com.mcampos.ejb.cloudsystem.user.company.role.session;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.role.entity.CompanyRole;
import br.com.mcampos.ejb.cloudsystem.user.company.role.entity.CompanyRolePK;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CompanyRoleSessionLocal extends Serializable
{
    CompanyRole add( CompanyRole entity ) throws ApplicationException;

    void delete( CompanyRolePK key ) throws ApplicationException;

    void delete( CompanyRole collaborator ) throws ApplicationException;

    CompanyRole get( CompanyRolePK key ) throws ApplicationException;

    List<CompanyRole> getAll( Company company ) throws ApplicationException;
}
