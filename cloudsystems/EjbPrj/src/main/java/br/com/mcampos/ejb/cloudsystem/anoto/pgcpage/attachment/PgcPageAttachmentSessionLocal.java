package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface PgcPageAttachmentSessionLocal extends Serializable
{
    void delete( PgcPageAttachmentPK key ) throws ApplicationException;

    PgcPageAttachment get( PgcPageAttachmentPK key ) throws ApplicationException;

    List<PgcPageAttachment> getAll( PgcPage page ) throws ApplicationException;

    List<PgcPageAttachment> getAll( String barCodeValue, String pageAddress, Integer padId,
                                    Integer currentPGC ) throws ApplicationException;
}
