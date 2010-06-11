package br.com.mcampos.ejb.cloudsystem.anoto.upload;


import br.com.mcampos.dto.anoto.PGCDTO;
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
}
