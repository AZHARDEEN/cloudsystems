package br.com.mcampos.ejb.cloudsystem.anoto.form;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AnotoFormFacade extends Serializable
{
    Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException;

    List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException;

    List<PadDTO> getPads( FormDTO form ) throws ApplicationException;

    void addPens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException;

    void removePens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException;

    List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

    byte[] getObject( MediaDTO key ) throws ApplicationException;

    MediaDTO addFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException;

    void removeFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException;

    MediaDTO removePad( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException;

    List<MediaDTO> getFiles( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

    PadDTO addToForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad, List<String> pages ) throws ApplicationException;

    void addToPage( AuthenticationDTO auth, PadDTO padDTO, String pageAddress,
                    List<AnotoPageFieldDTO> fields ) throws ApplicationException;

    MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException;

    void addMedias( AuthenticationDTO auth, FormDTO form, MediaDTO[] medias ) throws ApplicationException;

    void removeMedias( AuthenticationDTO auth, FormDTO form, MediaDTO[] medias ) throws ApplicationException;

    List<MediaDTO> getMedias( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;
}
