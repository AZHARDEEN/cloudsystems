package br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.SystemUserPropertyDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.FieldTypeUtil;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.session.FieldTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.SystemUserPropertyUtil;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity.SystemUserProperty;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.session.SystemUserPropertySessionLocal;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "SystemUserPropertyFacade", mappedName = "SystemUserPropertyFacade" )
@Remote
public class SystemUserPropertyFacadeBean extends AbstractSecurity implements SystemUserPropertyFacade
{
    public static final Integer messageId = 39;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private SystemUserPropertySessionLocal session;

    @EJB
    private FieldTypeSessionLocal typeSession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return session.nextIntegerId();
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        if ( SysUtils.isZero( id ) )
            throwException( 4 );
        authenticate( auth );
        session.delete( id );
    }

    public SystemUserPropertyDTO get( Integer id ) throws ApplicationException
    {
        if ( SysUtils.isZero( id ) )
            throwException( 4 );
        return SystemUserPropertyUtil.copy( session.get( id ) );
    }

    public SystemUserPropertyDTO add( AuthenticationDTO auth, SystemUserPropertyDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        if ( dto == null )
            throwException( 1 );
        SystemUserProperty entity = session.get( dto.getId() );
        if ( entity != null )
            throwException( 2 );
        entity = SystemUserPropertyUtil.createEntity( dto );
        return SystemUserPropertyUtil.copy( session.add( entity ) );
    }

    public SystemUserPropertyDTO update( AuthenticationDTO auth, SystemUserPropertyDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        if ( dto == null )
            throwException( 1 );
        SystemUserProperty entity = session.get( dto.getId() );
        if ( entity == null )
            throwException( 3 );
        entity = SystemUserPropertyUtil.update( entity, dto );
        return SystemUserPropertyUtil.copy( session.update( entity ) );
    }

    public List<SystemUserPropertyDTO> getAll() throws ApplicationException
    {
        return SystemUserPropertyUtil.toDTOList( session.getAll() );
    }

    public List<FieldTypeDTO> getTypes() throws ApplicationException
    {
        return FieldTypeUtil.toDTOList( typeSession.getAll() );
    }
}
