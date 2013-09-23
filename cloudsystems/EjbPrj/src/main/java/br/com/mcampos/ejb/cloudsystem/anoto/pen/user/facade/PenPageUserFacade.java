package br.com.mcampos.ejb.cloudsystem.anoto.pen.user.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenUserDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface PenPageUserFacade extends Serializable
{
    List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException;

    List<PenUserDTO> getAll( AuthenticationDTO auth, Integer formId, Boolean bActive ) throws ApplicationException;

    List<ListUserDTO> getCollaborators( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException;

    void changeUser( AuthenticationDTO auth, String penId, Integer userId ) throws ApplicationException;
}
