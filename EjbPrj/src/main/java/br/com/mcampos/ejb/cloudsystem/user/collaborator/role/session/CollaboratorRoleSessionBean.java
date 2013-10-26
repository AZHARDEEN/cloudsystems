package br.com.mcampos.ejb.cloudsystem.user.collaborator.role.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRole;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRolePK;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CollaboratorRoleSession", mappedName = "CollaboratorRoleSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CollaboratorRoleSessionBean extends Crud<CollaboratorRolePK, CollaboratorRole> implements CollaboratorRoleSessionLocal
{
    @EJB
    private RoleSessionLocal roleSession;

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
        if ( collaborator == null )
            return Collections.emptyList();

        return ( List<CollaboratorRole> )getResultList( CollaboratorRole.getAll, collaborator );
    }

    public void delete( Collaborator collaborator ) throws ApplicationException
    {
        if ( collaborator == null )
            return;
        List<CollaboratorRole> roles = getAll( collaborator );
        if ( SysUtils.isEmpty( roles ) )
            return;
        for ( CollaboratorRole role : roles ) {
            delete( role );
        }
    }

    public void setDefaultRoles( Collaborator collaborator ) throws ApplicationException
    {
        List<Role> roles = roleSession.getDefaultRoles();
        if ( SysUtils.isEmpty( roles ) )
            return;
        CollaboratorRolePK key = new CollaboratorRolePK();
        key.setCollaboratorSequence( collaborator.getSequence() );
        key.setCompanyId( collaborator.getCompanyId() );
        for ( Role role : roles ) {
            key.setRoleId( role.getId() );
            if ( get( key ) == null ) {
                CollaboratorRole newRole = new CollaboratorRole();
                newRole.setCollaborator( collaborator );
                newRole.setRole( role );
                add( newRole );
            }
        }
    }
}
