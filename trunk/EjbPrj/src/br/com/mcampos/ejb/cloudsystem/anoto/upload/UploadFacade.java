package br.com.mcampos.ejb.cloudsystem.anoto.upload;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.anoto.PgcPropertyDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface UploadFacade extends Serializable
{
    PGCDTO add( PGCDTO dto, List<String> addresses, ArrayList<MediaDTO> medias,
                List<PgcPropertyDTO> properties ) throws ApplicationException;

    void setPgcStatus( PGCDTO dto, Integer newStatus ) throws ApplicationException;

    List<PgcPenPageDTO> getPgcPenPages( PGCDTO pgc ) throws ApplicationException;

    void add( PgcPageDTO dto ) throws ApplicationException;

    List<PgcAttachmentDTO> getBarCodes( String barCodeValue, String pageAddress, Integer padId,
                                        Integer currentPGC ) throws ApplicationException;

    byte[] getObject( MediaDTO key ) throws ApplicationException;

    void addProcessedImage( PGCDTO pgc, MediaDTO media, int book, int page ) throws ApplicationException;

    void addPgcAttachment( PgcAttachmentDTO dto ) throws ApplicationException;

    void addPgcField( PgcFieldDTO dto ) throws ApplicationException;

    List<MediaDTO> getImages( AnotoPageDTO page ) throws ApplicationException;

}
