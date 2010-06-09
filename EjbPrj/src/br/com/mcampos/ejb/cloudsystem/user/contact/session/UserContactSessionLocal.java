package br.com.mcampos.ejb.cloudsystem.user.contact.session;


import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface UserContactSessionLocal extends Serializable
{
    void delete( Users user ) throws ApplicationException;

    UserContact add( UserContact contact ) throws ApplicationException;
}
