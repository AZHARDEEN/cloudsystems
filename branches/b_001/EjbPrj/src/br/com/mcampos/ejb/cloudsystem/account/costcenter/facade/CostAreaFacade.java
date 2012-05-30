package br.com.mcampos.ejb.cloudsystem.account.costcenter.facade;


import br.com.mcampos.dto.accounting.CostAreaDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CostAreaFacade extends Serializable
{
    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    CostAreaDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    CostAreaDTO add( AuthenticationDTO auth, CostAreaDTO dto ) throws ApplicationException;

    CostAreaDTO update( AuthenticationDTO auth, CostAreaDTO dto ) throws ApplicationException;

    List<CostAreaDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;

    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;
}
