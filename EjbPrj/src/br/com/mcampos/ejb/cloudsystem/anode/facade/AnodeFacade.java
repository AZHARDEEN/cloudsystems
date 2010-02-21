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

    public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    public void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    public FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException;

    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException;


    /*Operação em Canetas*/

    public PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    public void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    public PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    public List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException;

    public PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

}
