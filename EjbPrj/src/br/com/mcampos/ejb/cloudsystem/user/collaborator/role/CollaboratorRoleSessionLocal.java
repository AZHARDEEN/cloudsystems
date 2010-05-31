package br.com.mcampos.ejb.cloudsystem.user.collaborator.role;


import br.com.mcampos.ejb.cloudsystem.user.collaborator.Collaborator;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CollaboratorRoleSessionLocal extends Serializable
{
	CollaboratorRole add( CollaboratorRole entity ) throws ApplicationException;

	void delete( CollaboratorRolePK key ) throws ApplicationException;

	CollaboratorRole get( CollaboratorRolePK key ) throws ApplicationException;

	List<CollaboratorRole> getAll( Collaborator collaborator ) throws ApplicationException;
}
