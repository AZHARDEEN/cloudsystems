package br.com.mcampos.ejb.cloudsystem.user.gender;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface GenderFacade
{
    List<GenderDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;
}
