package br.com.mcampos.ejb.cloudsystem.anoto.facade;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.AnotoSummary;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.resale.DealerDTO;
import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;
import java.util.Properties;

import javax.ejb.Remote;


@Remote
public interface EmbratelFacade extends Serializable
{
    List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException;

    List<ResaleDTO> getResales( AuthenticationDTO auth ) throws ApplicationException;

    List<AnotoPageFieldDTO> getSearchableFields( AuthenticationDTO auth, FormDTO form ) throws ApplicationException;

    List<AnotoResultList> getAllPgcPenPage( AuthenticationDTO auth, Properties props,
                                            Integer maxRecords ) throws ApplicationException;

    AnotoSummary getSummary( AuthenticationDTO auth, Properties props ) throws ApplicationException;

    List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException;

    List<DealerDTO> getDealers( AuthenticationDTO auth, ResaleDTO resale ) throws ApplicationException;
}
