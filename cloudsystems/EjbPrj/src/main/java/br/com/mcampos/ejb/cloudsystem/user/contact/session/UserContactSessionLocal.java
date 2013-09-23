package br.com.mcampos.ejb.cloudsystem.user.contact.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface UserContactSessionLocal extends Serializable
{
    void delete( Users user ) throws ApplicationException;

    UserContact add( UserContact contact ) throws ApplicationException;

    void refresh( Users user, List<UserContact> contacts ) throws ApplicationException;
}
