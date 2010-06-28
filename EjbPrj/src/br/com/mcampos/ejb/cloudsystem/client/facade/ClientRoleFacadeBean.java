package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleUtils;
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
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

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
    private CompanySessionLocal companySession;

    @EJB
    private RoleSessionLocal roleSession;


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

    public List<RoleDTO> getRoles( AuthenticationDTO auth, CompanyDTO companyDto ) throws ApplicationException
    {
        authenticate( auth );

        Company company = getCompany( companyDto.getId() );
        List<CompanyRole> r = companyRoleSession.getAll( company );
        List<Role> roles = RoleUtils.getRoles( CompanyRoleUtil.toRoleList( r ) );
        return RoleUtils.toRoleDTOList( roles );
    }

    public void add( AuthenticationDTO auth, CompanyDTO companyDto, List<RoleDTO> roles ) throws ApplicationException
    {
        authenticate( auth );
        CompanyRolePK key = new CompanyRolePK();
        Company company = getCompany( companyDto.getId() );
        key.setCompanyId( company.getId() );
        CompanyRole cr = new CompanyRole();
        cr.setCompany( company );
        for ( RoleDTO r : roles ) {
            Role e = roleSession.get( r.getId() );
            if ( e != null ) {
                key.setRoleId( r.getId() );
                if ( companyRoleSession.get( key ) == null ) {
                    cr.setRole( e );
                    companyRoleSession.add( cr );
                    grantRoleToAdministrators( e, company );
                }
            }
        }
    }

    protected void grantRoleToAdministrators( Role e, Company c ) throws ApplicationException
    {
        List<Collaborator> list = collaboratorSession.get( c, CollaboratorType.typeManager );
        if ( SysUtils.isEmpty( list ) )
            return;
        CollaboratorRole cr = new CollaboratorRole();
        cr.setRole( e );
        for ( Collaborator item : list ) {
            cr.setCollaborator( item );
            if ( collaboratorRoleSession.get( new CollaboratorRolePK( cr ) ) == null )
                collaboratorRoleSession.add( cr );
        }
    }

    protected void revokeRoleToAll( Role e, Company c ) throws ApplicationException
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


    public void delete( AuthenticationDTO auth, CompanyDTO companyDto, List<RoleDTO> roles ) throws ApplicationException
    {
        authenticate( auth );
        CompanyRolePK key = new CompanyRolePK();
        Company company = getCompany( companyDto.getId() );
        key.setCompanyId( company.getId() );
        CompanyRole cr = new CompanyRole();
        cr.setCompany( company );
        for ( RoleDTO r : roles ) {
            Role e = roleSession.get( r.getId() );
            if ( e != null ) {
                key.setRoleId( r.getId() );
                companyRoleSession.delete( key );
                revokeRoleToAll( e, company );
            }
        }
    }

    protected Company getCompany( Integer id ) throws ApplicationException
    {
        Company company = companySession.get( id );
        if ( company == null )
            throwException( 1 );
        return company;
    }
}
