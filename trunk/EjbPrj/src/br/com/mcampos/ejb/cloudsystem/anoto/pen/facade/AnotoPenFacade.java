package br.com.mcampos.ejb.cloudsystem.anoto.pen.facade;


import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AnotoPenFacade extends Serializable
{

    List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    ListUserDTO findUser( AuthenticationDTO auth, Integer userId ) throws ApplicationException;

    ListUserDTO findUserByEmail( AuthenticationDTO auth, String document ) throws ApplicationException;

    List<ClientDTO> getClients( AuthenticationDTO auth ) throws ApplicationException;
}
