package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
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

    MediaDTO addToForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException;

    MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException;

    List<MediaDTO> getPADs( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM CANETAS
     *
     * *************************************************************************
     * *************************************************************************
     */

    PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException;

    PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException;

    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM MEDIAS
     *
     * *************************************************************************
     * *************************************************************************
     */

    byte[] getObject( AuthenticationDTO auth, MediaDTO key ) throws ApplicationException;

    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM PAGES
     *
     * *************************************************************************
     * *************************************************************************
     */

    List<AnotoPageDTO> getPages( AuthenticationDTO auth, PadDTO pad ) throws ApplicationException;

    List<MediaDTO> getImages( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException;

    MediaDTO removeFromPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException;

    MediaDTO addToPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException;

    List<PenDTO> getAvailablePens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException;
}
