package br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUserPK;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.Date;

import javax.ejb.Local;


@Local
public interface AnotoPenUserSessionLocal extends Serializable
{
    AnotoPenUser add( AnotoPenUser entity ) throws ApplicationException;

    void delete( AnotoPenUserPK key ) throws ApplicationException;

    void delete( AnotoPenUser entity ) throws ApplicationException;

    AnotoPenUser getUser( String penId, Date date ) throws ApplicationException;

    AnotoPenUser getUser( Person person, Date date ) throws ApplicationException;

    AnotoPenUser getCurrentUser( String penId ) throws ApplicationException;
}
