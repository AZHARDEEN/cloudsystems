package br.com.mcampos.ejb.cloudsystem.anode.facade;

import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pen;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
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

    MediaDTO addToForm( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException;

    MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException;

    List<MediaDTO> getMedias( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException;

    PenDTO insertIntoForm( AuthenticationDTO auth, FormDTO key, PenDTO toRemove ) throws ApplicationException;

    PenDTO removeFromForm( AuthenticationDTO auth, FormDTO key, PenDTO toRemove ) throws ApplicationException;

    List<PenDTO> insertIntoForm( AuthenticationDTO auth, FormDTO key, List<PenDTO> itens ) throws ApplicationException;

    List<PenDTO> removeFromForm( AuthenticationDTO auth, FormDTO key, List<PenDTO> itens ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException;

    List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException;


    /*Operação em Canetas*/

    PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException;

    PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    List<FormDTO> getAvailableForms( AuthenticationDTO auth, PenDTO dto ) throws ApplicationException;

    List<FormDTO> getForms( AuthenticationDTO auth, PenDTO dto ) throws ApplicationException;

    FormDTO insertIntoPen( AuthenticationDTO auth, PenDTO pen, FormDTO form ) throws ApplicationException;

    FormDTO removeFromPen( AuthenticationDTO auth, PenDTO pen, FormDTO form ) throws ApplicationException;

    List<FormDTO> insertIntoPen( AuthenticationDTO auth, PenDTO pen, List<FormDTO> forms ) throws ApplicationException;

    List<FormDTO> removeFromPen( AuthenticationDTO auth, PenDTO pen, List<FormDTO> forms ) throws ApplicationException;

    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM CANETAS
     *
     * *************************************************************************
     * *************************************************************************
     */

    public byte[] getObject( AuthenticationDTO auth, MediaDTO key ) throws ApplicationException;
}
