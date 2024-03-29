package br.com.mcampos.ejb.cloudsystem.user.attribute.gender.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface GenderFacade
{
    List<GenderDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;

    Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException;

    void add( AuthenticationDTO currentUser, GenderDTO dto ) throws ApplicationException;

    void update( AuthenticationDTO currentUser, GenderDTO dto ) throws ApplicationException;

    void delete( AuthenticationDTO currentUser, GenderDTO dto ) throws ApplicationException;
}
