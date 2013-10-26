package br.com.mcampos.ejb.cloudsystem.user.media.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.media.entity.UserMedia;
import br.com.mcampos.ejb.cloudsystem.user.media.entity.UserMediaPK;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface UserMediaSessionLocal extends Serializable
{
    UserMedia add( UserMedia entity ) throws ApplicationException;

    void delete( UserMediaPK key ) throws ApplicationException;

    UserMedia get( UserMediaPK key ) throws ApplicationException;

    UserMedia get( Users user, UserMediaType type ) throws ApplicationException;
}
