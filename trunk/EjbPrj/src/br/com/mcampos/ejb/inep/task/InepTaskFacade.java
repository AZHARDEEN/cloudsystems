package br.com.mcampos.ejb.inep.task;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.inep.dto.InepTaskDTO;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface InepTaskFacade
{
    List<InepTaskDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;

    Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException;

    void add( AuthenticationDTO currentUser, InepTaskDTO dto ) throws ApplicationException;

    void update( AuthenticationDTO currentUser, InepTaskDTO dto ) throws ApplicationException;

    void delete( AuthenticationDTO currentUser, InepTaskDTO dto ) throws ApplicationException;
}
