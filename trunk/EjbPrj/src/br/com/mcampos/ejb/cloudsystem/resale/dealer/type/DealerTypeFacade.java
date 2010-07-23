package br.com.mcampos.ejb.cloudsystem.resale.dealer.type;


import br.com.mcampos.dto.resale.DealerTypeDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface DealerTypeFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    DealerTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    DealerTypeDTO add( AuthenticationDTO auth, DealerTypeDTO dto ) throws ApplicationException;

    DealerTypeDTO update( AuthenticationDTO auth, DealerTypeDTO dto ) throws ApplicationException;

    List<DealerTypeDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;
}
