package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment;


import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PgcAttachmentSessionLocal
{
    void delete( PgcAttachmentPK key ) throws ApplicationException;

    PgcAttachment get( PgcAttachmentPK key ) throws ApplicationException;

    List<PgcAttachment> getAll( PgcPage page ) throws ApplicationException;
}
