package br.com.mcampos.ejb.cloudsystem.user.attribute.title.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface TitleFacade
{
    List<TitleDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;

    Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException;

    void add( AuthenticationDTO currentUser, TitleDTO dto ) throws ApplicationException;

    void update( AuthenticationDTO currentUser, TitleDTO dto ) throws ApplicationException;

    void delete( AuthenticationDTO currentUser, TitleDTO dto ) throws ApplicationException;
}
