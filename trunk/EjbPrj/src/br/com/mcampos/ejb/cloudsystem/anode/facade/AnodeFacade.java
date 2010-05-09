package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    PadDTO addToForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad, List<String> pages ) throws ApplicationException;

    MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO entity, MediaDTO pad ) throws ApplicationException;

    List<PadDTO> getPads( FormDTO form ) throws ApplicationException;

    List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

    void addPens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException;

    void removePens( AuthenticationDTO auth, FormDTO form, List<PenDTO> pens ) throws ApplicationException;

    MediaDTO addFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException;

    void removeFile( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException;

    List<MediaDTO> getFiles( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

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

    byte[] getObject( MediaDTO key ) throws ApplicationException;

    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM PAGES
     *
     * *************************************************************************
     * *************************************************************************
     */

    List<AnotoPageDTO> getPages( AuthenticationDTO auth, PadDTO pad ) throws ApplicationException;

    List<MediaDTO> getImages( AnotoPageDTO page ) throws ApplicationException;

    MediaDTO removeFromPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException;

    MediaDTO addToPage( AuthenticationDTO auth, AnotoPageDTO page, MediaDTO image ) throws ApplicationException;

    List<PenDTO> getAvailablePens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException;

    List<PenDTO> getPens( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException;

    void addPens( AuthenticationDTO auth, AnotoPageDTO page, List<PenDTO> pens ) throws ApplicationException;

    void removePens( AuthenticationDTO auth, AnotoPageDTO page, List<PenDTO> pens ) throws ApplicationException;

    List<AnotoPageDTO> getPages( AuthenticationDTO auth ) throws ApplicationException;

    List<AnotoPageDTO> getPages( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;


    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM PGCS
     *
     * *************************************************************************
     * *************************************************************************
     */

    PGCDTO add( PGCDTO dto, List<String> pages, ArrayList<MediaDTO> medias,
                List<PgcPropertyDTO> properties ) throws ApplicationException;

    List<PGCDTO> getAllPgc( AuthenticationDTO auth ) throws ApplicationException;

    List<PGCDTO> getSuspendedPgc( AuthenticationDTO auth ) throws ApplicationException;

    List<AnotoPenPageDTO> getPenPages( AuthenticationDTO auth, AnotoPageDTO page ) throws ApplicationException;

    List<PgcPenPageDTO> get( AuthenticationDTO auth, AnotoPenPageDTO penPage ) throws ApplicationException;

    List<AnotoResultList> getAllPgcPenPage( AuthenticationDTO auth, Properties props,
                                            Integer maxRecord ) throws ApplicationException;

    List<PgcPenPageDTO> getPgcPenPages( PGCDTO pgc ) throws ApplicationException;

    void delete( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException;

    void addProcessedImage( PGCDTO pgc, MediaDTO media, int book, int page ) throws ApplicationException;

    void addPgcField( PgcFieldDTO dto ) throws ApplicationException;

    void addPgcAttachment( PgcAttachmentDTO dto ) throws ApplicationException;

    void add( PgcPageDTO dto ) throws ApplicationException;

    List<MediaDTO> getImages( PgcPageDTO page ) throws ApplicationException;

    List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException;

    void update( AuthenticationDTO auth, PgcFieldDTO page ) throws ApplicationException;

    Integer remove( AuthenticationDTO auth, AnotoResultList item ) throws ApplicationException;

    List<PgcAttachmentDTO> getAttachments( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException;

    List<MediaDTO> getAttachments( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException;

    List<PgcPropertyDTO> getProperties( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException;

    List<PgcPropertyDTO> getGPS( AuthenticationDTO auth, PGCDTO pgc ) throws ApplicationException;

    void addToPage( AuthenticationDTO auth, PadDTO PAD, String page, List<AnotoPageFieldDTO> fields ) throws ApplicationException;

    List<FieldTypeDTO> getFieldTypes( AuthenticationDTO auth ) throws ApplicationException;

    void addFields( AuthenticationDTO auth, List<AnotoPageFieldDTO> fields ) throws ApplicationException;

    void refreshFields( AuthenticationDTO auth, List<AnotoPageFieldDTO> fields ) throws ApplicationException;

    List<AnotoPageFieldDTO> getFields( AuthenticationDTO auth, AnotoPageDTO anotoPage ) throws ApplicationException;

    void update( AuthenticationDTO auth, AnotoPageFieldDTO dto ) throws ApplicationException;

    void setPgcStatus( PGCDTO dto, Integer newStatus ) throws ApplicationException;

    void update( AuthenticationDTO auth, AnotoPageDTO anotoPage ) throws ApplicationException;
}
