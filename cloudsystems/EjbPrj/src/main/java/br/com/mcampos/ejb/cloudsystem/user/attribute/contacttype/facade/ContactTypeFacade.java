package br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ContactTypeFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    ContactTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    ContactTypeDTO add( AuthenticationDTO auth, ContactTypeDTO dto ) throws ApplicationException;

    ContactTypeDTO update( AuthenticationDTO auth, ContactTypeDTO dto ) throws ApplicationException;

    List<ContactTypeDTO> getAll() throws ApplicationException;
}
