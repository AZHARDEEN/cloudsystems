package br.com.mcampos.ejb.cloudsystem.user.collaborator.role.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleUtils;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.CollaboratorPK;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.CollaboratorRoleUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRole;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRolePK;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.session.CollaboratorRoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CollaboratorRoleFacade", mappedName = "CollaboratorRoleFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CollaboratorRoleFacadeBean extends AbstractSecurity implements CollaboratorRoleFacade
{
    private static final Integer messageId = 32;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CollaboratorRoleSessionLocal collaboratorRoleSession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private RoleSessionLocal roleSession;

    @EJB
    private NewPersonSessionLocal personSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public List<RoleDTO> getAvailableRoles( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator col = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        List<CollaboratorRole> r = collaboratorRoleSession.getAll( col );
        List<Role> roles = RoleUtils.getRoles( CollaboratorRoleUtil.toRoleList( r ) );
        return RoleUtils.toRoleDTOList( roles );
    }

    public List<RoleDTO> getRoles( AuthenticationDTO auth, CollaboratorDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator c = collaboratorSession.get( new CollaboratorPK( dto.getCompany().getId(), dto.getSequence() ) );
        List<CollaboratorRole> r = collaboratorRoleSession.getAll( c );
        List<Role> roles = RoleUtils.getRoles( CollaboratorRoleUtil.toRoleList( r ) );
        return RoleUtils.toRoleDTOList( roles );
    }

    public void add( AuthenticationDTO auth, CollaboratorDTO dto, List<RoleDTO> roles ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator c = collaboratorSession.get( new CollaboratorPK( dto.getCompany().getId(), dto.getSequence() ) );
        CollaboratorRolePK key = new CollaboratorRolePK();
        key.setCompanyId( dto.getCompany().getId() );
        key.setCollaboratorSequence( dto.getSequence() );
        CollaboratorRole cr = new CollaboratorRole();
        cr.setCollaborator( c );
        for ( RoleDTO r : roles ) {
            Role e = roleSession.get( r.getId() );
            if ( e != null ) {
                key.setRoleId( r.getId() );
                if ( collaboratorRoleSession.get( key ) == null ) {
                    cr.setRole( e );
                    collaboratorRoleSession.add( cr );
                }
            }
        }
    }

    public void delete( AuthenticationDTO auth, CollaboratorDTO dto, List<RoleDTO> roles ) throws ApplicationException
    {
        authenticate( auth );
        CollaboratorRolePK key = new CollaboratorRolePK();
        key.setCompanyId( dto.getCompany().getId() );
        key.setCollaboratorSequence( dto.getSequence() );
        for ( RoleDTO r : roles ) {
            Role e = roleSession.get( r.getId() );
            if ( e != null ) {
                key.setRoleId( r.getId() );
                collaboratorRoleSession.delete( key );
            }
        }
    }

    protected Person getPerson( Integer id ) throws ApplicationException
    {
        Person person = personSession.get( id );
        if ( person == null )
            throwException( 1 );
        return person;
    }
}
