package br.com.mcampos.ejb.cloudsystem.user.collaborator.type.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorTypeDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CollaboratorTypeFacade extends Serializable
{
    List<CollaboratorTypeDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;

    Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException;

    void add( AuthenticationDTO currentUser, CollaboratorTypeDTO dto ) throws ApplicationException;

    void update( AuthenticationDTO currentUser, CollaboratorTypeDTO dto ) throws ApplicationException;

    void delete( AuthenticationDTO currentUser, CollaboratorTypeDTO dto ) throws ApplicationException;
}
