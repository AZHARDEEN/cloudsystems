package br.com.mcampos.ejb.cloudsystem.user.media.type.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.UserMediaTypeDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface UserMediaTypeFacade extends Serializable
{
    List<UserMediaTypeDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;

    Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException;

    void add( AuthenticationDTO currentUser, UserMediaTypeDTO dto ) throws ApplicationException;

    void update( AuthenticationDTO currentUser, UserMediaTypeDTO dto ) throws ApplicationException;

    void delete( AuthenticationDTO currentUser, Integer id ) throws ApplicationException;
}
