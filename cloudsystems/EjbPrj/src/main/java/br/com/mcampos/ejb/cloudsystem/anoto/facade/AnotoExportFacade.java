package br.com.mcampos.ejb.cloudsystem.anoto.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AnotoExportFacade
{
    List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException;

    List<MediaDTO> getAttachments( AuthenticationDTO auth, PGCDTO page ) throws ApplicationException;

    List<MediaDTO> getImages( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException;

    List<PgcPageDTO> getPages( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;
}
