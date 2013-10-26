package br.com.mcampos.ejb.cloudsystem.anoto.form.user.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUserPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface AnotoFormUserSessionLocal extends Serializable
{
    AnotoFormUser add( AnotoFormUser entity ) throws ApplicationException;

    AnotoFormUser add( Integer formId, Integer companyId ) throws ApplicationException;

    void delete( AnotoFormUserPK key ) throws ApplicationException;

    void delete( Integer formId, Integer companyId ) throws ApplicationException;

    List<AnotoFormUser> get( Integer formId ) throws ApplicationException;

    AnotoFormUser get( Integer formId, Integer companyId ) throws ApplicationException;

    List<AnotoFormUser> get( Company company ) throws ApplicationException;
}
