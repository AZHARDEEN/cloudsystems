package br.com.mcampos.ejb.cloudsystem.user.collaborator.role;


import br.com.mcampos.ejb.cloudsystem.user.collaborator.Collaborator;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CollaboratorRoleSession", mappedName = "CloudSystems-EjbPrj-CollaboratorRoleSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CollaboratorRoleSessionBean extends Crud<CollaboratorRolePK, CollaboratorRole> implements CollaboratorRoleSessionLocal
{
	public CollaboratorRoleSessionBean()
	{
	}

	public void delete( CollaboratorRolePK key ) throws ApplicationException
	{
		delete( CollaboratorRole.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public CollaboratorRole get( CollaboratorRolePK key ) throws ApplicationException
	{
		return get( CollaboratorRole.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<CollaboratorRole> getAll( Collaborator collaborator ) throws ApplicationException
	{
		return ( List<CollaboratorRole> )getResultList( CollaboratorRole.getAll, collaborator );
	}
}
