package br.com.mcampos.ejb.cloudsystem.anode.facade;

import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AnodeFacade
{
    /*Operação em formulários*/

    FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException;

    FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException;


    /*Operação em Canetas*/

    PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException;

    PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    List<FormDTO> getAvailableForms( AuthenticationDTO auth, PenDTO dto ) throws ApplicationException;

}
