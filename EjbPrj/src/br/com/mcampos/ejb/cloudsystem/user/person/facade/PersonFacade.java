package br.com.mcampos.ejb.cloudsystem.user.person.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Remote;


@Remote
public interface PersonFacade extends Serializable
{
    PersonDTO get( AuthenticationDTO auth ) throws ApplicationException;
}
