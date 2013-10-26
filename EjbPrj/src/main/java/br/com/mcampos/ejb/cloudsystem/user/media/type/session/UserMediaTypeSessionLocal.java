package br.com.mcampos.ejb.cloudsystem.user.media.type.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface UserMediaTypeSessionLocal extends Serializable
{
    UserMediaType add( UserMediaType entity ) throws ApplicationException;

    UserMediaType update( UserMediaType entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    UserMediaType get( Integer key ) throws ApplicationException;

    List<UserMediaType> getAll() throws ApplicationException;

    Integer getNextId() throws ApplicationException;
}
