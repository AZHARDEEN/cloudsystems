package br.com.mcampos.ejb.cloudsystem.user.collaborator.type.session;


import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity.CollaboratorType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CollaboratorTypeSessionLocal extends Serializable
{
    CollaboratorType add( CollaboratorType entity ) throws ApplicationException;

    CollaboratorType update( CollaboratorType entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    CollaboratorType get( Integer key ) throws ApplicationException;

    List<CollaboratorType> getAll() throws ApplicationException;

    Integer getNextId() throws ApplicationException;
}
