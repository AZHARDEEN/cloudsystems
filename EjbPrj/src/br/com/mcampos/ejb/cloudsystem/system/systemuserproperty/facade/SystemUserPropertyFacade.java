package br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.SystemUserPropertyDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface SystemUserPropertyFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    SystemUserPropertyDTO get( Integer id ) throws ApplicationException;

    SystemUserPropertyDTO add( AuthenticationDTO auth, SystemUserPropertyDTO dto ) throws ApplicationException;

    SystemUserPropertyDTO update( AuthenticationDTO auth, SystemUserPropertyDTO dto ) throws ApplicationException;

    List<SystemUserPropertyDTO> getAll() throws ApplicationException;

    List<FieldTypeDTO> getTypes() throws ApplicationException;
}
