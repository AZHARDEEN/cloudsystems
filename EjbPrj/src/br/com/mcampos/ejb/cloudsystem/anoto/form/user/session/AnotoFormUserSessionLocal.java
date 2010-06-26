package br.com.mcampos.ejb.cloudsystem.anoto.form.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUserPK;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface AnotoFormUserSessionLocal extends Serializable
{
    AnotoFormUser add( AnotoFormUser entity ) throws ApplicationException;

    void delete( AnotoFormUserPK key ) throws ApplicationException;

    AnotoFormUser get( Integer formId ) throws ApplicationException;
}
