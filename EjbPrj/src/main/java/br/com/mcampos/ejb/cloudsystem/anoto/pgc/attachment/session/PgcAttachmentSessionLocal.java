package br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity.PgcAttachment;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface PgcAttachmentSessionLocal extends Serializable
{
    List<PgcAttachment> get( Integer pgcId ) throws ApplicationException;
}
