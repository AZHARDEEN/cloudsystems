package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleUtils;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.CollaboratorRoleUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRole;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRolePK;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.session.CollaboratorRoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity.CollaboratorType;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.role.CompanyRoleUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.role.entity.CompanyRole;
import br.com.mcampos.ejb.cloudsystem.user.company.role.entity.CompanyRolePK;
import br.com.mcampos.ejb.cloudsystem.user.company.role.session.CompanyRoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.session.user.UserSessionLocal;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ClientRoleFacade", mappedName = "CloudSystems-EjbPrj-ClientRoleFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class ClientRoleFacadeBean extends AbstractSecurity implements ClientRoleFacade
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 190968146167921972L;

	private static final Integer messageId = 31;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CollaboratorRoleSessionLocal collaboratorRoleSession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private CompanyRoleSessionLocal companyRoleSession;

    @EJB
    private UserSessionLocal userSession;

    @EJB
    private RoleSessionLocal roleSession;

    @EJB
    private CompanySessionLocal clientSession;


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

    public List<RoleDTO> getRoles( AuthenticationDTO auth, Integer userId ) throws ApplicationException
    {
        authenticate( auth );
        if ( userId == null )
            throw new InvalidParameterException( "Company Object is null" );
        List<Role> roles = null;
        Users usr = getUser( userId );
        if ( usr != null && usr instanceof Company ) {
            List<CompanyRole> r = companyRoleSession.getAll( ( Company )usr );
            roles = RoleUtils.getRoles( CompanyRoleUtil.toRoleList( r ) );
        }
        return RoleUtils.toRoleDTOList( roles );
    }

    public void add( AuthenticationDTO auth, Integer userId, List<RoleDTO> roles ) throws ApplicationException
    {
        authenticate( auth );
        CompanyRolePK key = new CompanyRolePK();
        Users usr = getUser( userId );
        key.setCompanyId( usr.getId() );
        CompanyRole cr = new CompanyRole();
        cr.setCompany( ( Company )usr );
        for ( RoleDTO r : roles ) {
            Role e = roleSession.get( r.getId() );
            if ( e != null ) {
                key.setRoleId( r.getId() );
                if ( companyRoleSession.get( key ) == null ) {
                    cr.setRole( e );
                    companyRoleSession.add( cr );
                    grantRoleToAdministrators( e, ( Company )usr );
                }
            }
        }
    }

    private void grantRoleToAdministrators( Role e, Company c ) throws ApplicationException
    {
        List<Collaborator> list = collaboratorSession.get( c, CollaboratorType.typeManager );
        if ( SysUtils.isEmpty( list ) )
            return;
        for ( Collaborator item : list ) {
            CollaboratorRole cr = new CollaboratorRole();
            cr.setRole( e );
            cr.setCollaborator( item );
            if ( collaboratorRoleSession.get( new CollaboratorRolePK( cr ) ) == null ) {
                collaboratorRoleSession.add( cr );

            }
        }
    }

    private void revokeRoleToAll( Role e, Company c ) throws ApplicationException
    {
        List<Collaborator> list = collaboratorSession.get( c, CollaboratorType.typeManager );
        if ( SysUtils.isEmpty( list ) )
            return;
        CollaboratorRolePK cr = new CollaboratorRolePK();
        cr.setRoleId( e.getId() );
        for ( Collaborator item : list ) {
            cr.setCollaboratorSequence( item.getSequence() );
            cr.setCompanyId( item.getCompanyId() );
            collaboratorRoleSession.delete( cr );
        }
    }


    public void delete( AuthenticationDTO auth, Integer userId, List<RoleDTO> roles ) throws ApplicationException
    {
        authenticate( auth );
        CompanyRolePK key = new CompanyRolePK();
        Users usr = getUser( userId );
        key.setCompanyId( usr.getId() );
        CompanyRole cr = new CompanyRole();
        cr.setCompany( ( Company )usr );
        for ( RoleDTO r : roles ) {
            Role e = roleSession.get( r.getId() );
            if ( e != null ) {
                key.setRoleId( r.getId() );
                companyRoleSession.delete( key );
                revokeRoleToAll( e, ( Company )usr );
            }
        }
    }

    private Users getUser( Integer id ) throws ApplicationException
    {
        if ( SysUtils.isZero( id ) )
            return null;
        Users company = userSession.get( id );
        if ( company == null )
            throwException( 1 );
        return company;
    }

    @SuppressWarnings("unchecked")
	public List<ListUserDTO> getClients( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        List<?> clients = clientSession.getAll();
        return UserUtil.copy( (List<Users>) clients );
    }
}
