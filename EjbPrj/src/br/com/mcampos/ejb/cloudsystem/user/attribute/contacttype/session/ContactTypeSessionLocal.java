package br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.session;


import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface ContactTypeSessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    ContactType get( Integer key ) throws ApplicationException;

    List<ContactType> getAll() throws ApplicationException;

    Integer nextIntegerId() throws ApplicationException;

    ContactType add( ContactType entity ) throws ApplicationException;

    ContactType update( ContactType entity ) throws ApplicationException;
}
