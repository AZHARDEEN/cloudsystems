package br.com.mcampos.ejb.cloudsystem.resale.dealer.facade;


import br.com.mcampos.dto.resale.DealerDTO;
import br.com.mcampos.dto.resale.DealerTypeDTO;
import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface DealerFacade extends Serializable
{
    void delete( AuthenticationDTO auth, ResaleDTO resale, Integer sequence ) throws ApplicationException;

    DealerDTO get( AuthenticationDTO auth, ResaleDTO resale, Integer sequence ) throws ApplicationException;

    DealerDTO add( AuthenticationDTO auth, DealerDTO dto ) throws ApplicationException;

    DealerDTO update( AuthenticationDTO auth, DealerDTO dto ) throws ApplicationException;

    List<DealerDTO> getAll( AuthenticationDTO auth, ResaleDTO resale ) throws ApplicationException;

    List<DealerTypeDTO> getTypes() throws ApplicationException;

    List<ClientDTO> getPeople( AuthenticationDTO auth ) throws ApplicationException;

    List<ResaleDTO> getResales( AuthenticationDTO auth ) throws ApplicationException;

}
