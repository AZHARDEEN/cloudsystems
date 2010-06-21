package br.com.mcampos.ejb.cloudsystem.user.collaborator;


import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.CollaboratorPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface NewCollaboratorSessionLocal extends Serializable
{
    Collaborator add( Collaborator entity ) throws ApplicationException;

    Collaborator update( Collaborator entity ) throws ApplicationException;

    void delete( CollaboratorPK key ) throws ApplicationException;

    Collaborator get( CollaboratorPK key ) throws ApplicationException;

    List<Collaborator> get( Person person ) throws ApplicationException;

    Collaborator get( Integer companyId, Integer userId ) throws ApplicationException;

    Collaborator get( Company company, Person person ) throws ApplicationException;

    List<Collaborator> get( Users user ) throws ApplicationException;
}
