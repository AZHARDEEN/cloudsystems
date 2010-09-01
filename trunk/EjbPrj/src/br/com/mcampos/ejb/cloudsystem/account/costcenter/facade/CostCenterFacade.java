package br.com.mcampos.ejb.cloudsystem.account.costcenter.facade;


import br.com.mcampos.dto.accounting.CostAreaDTO;
import br.com.mcampos.dto.accounting.CostCenterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CostCenterFacade extends Serializable
{
    void delete( AuthenticationDTO auth, Integer areaId, Integer id ) throws ApplicationException;

    CostCenterDTO get( AuthenticationDTO auth, Integer areaId, Integer id ) throws ApplicationException;

    CostCenterDTO add( AuthenticationDTO auth, CostCenterDTO dto ) throws ApplicationException;

    CostCenterDTO update( AuthenticationDTO auth, CostCenterDTO dto ) throws ApplicationException;

    List<CostCenterDTO> getAll( AuthenticationDTO auth, Integer areaId ) throws ApplicationException;

    List<CostAreaDTO> getCostAreas( AuthenticationDTO auth ) throws ApplicationException;

    Integer nextId( AuthenticationDTO auth, Integer areaId ) throws ApplicationException;
}
