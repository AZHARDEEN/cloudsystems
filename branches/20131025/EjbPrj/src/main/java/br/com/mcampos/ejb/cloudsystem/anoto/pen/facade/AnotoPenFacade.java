package br.com.mcampos.ejb.cloudsystem.anoto.pen.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AnotoPenFacade extends Serializable
{

    List<PenDTO> getPens( Integer nStart, Integer nSize ) throws ApplicationException;

    Integer count() throws ApplicationException;

    void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

}
