package br.com.mcampos.ejb.cloudsystem.anoto.page.field.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AnotoPageFieldFacade extends Serializable
{
    List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException;

    List<AnotoPageFieldDTO> getFields( AuthenticationDTO auth, Integer formId ) throws ApplicationException;

    List<FieldTypeDTO> getFieldTypes() throws ApplicationException;

    void update( AuthenticationDTO auth, AnotoPageFieldDTO dto ) throws ApplicationException;
}
