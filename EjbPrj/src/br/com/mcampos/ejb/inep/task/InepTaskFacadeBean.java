package br.com.mcampos.ejb.inep.task;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.inep.dto.InepTaskDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "InepTaskFacade", mappedName = "CloudSystems-EjbPrj-InepTaskFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class InepTaskFacadeBean extends AbstractSecurity implements InepTaskFacade
{
    public static final Integer messageId = 10;

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    InepTaskSessionLocal session;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    @Override
    public Integer getMessageTypeId()
    {
        return messageId;
    }

    @Override
    public List<InepTaskDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException
    {
        List<InepTask> tasks = session.getAll();
        return toDTO( tasks );
    }

    @Override
    public Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        return session.getNextId();
    }

    @Override
    public void add( AuthenticationDTO currentUser, InepTaskDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        InepTask entity = session.get( dto.getId() );
        if ( entity != null )
            throwException( 1 );
        entity = InepTaskUtil.createEntity( dto );
        session.add( entity );
    }

    @Override
    public void update( AuthenticationDTO currentUser, InepTaskDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        InepTask entity = session.get( dto.getId() );
        if ( entity == null )
            throwException( 2 );
        entity.setDescription( dto.getDescription() );
        session.update( entity );
    }

    @Override
    public void delete( AuthenticationDTO currentUser, InepTaskDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        session.delete( dto.getId() );
    }


    public static List<InepTaskDTO> toDTO( List<InepTask> entities )
    {
        if ( SysUtils.isEmpty( entities ) )
            return Collections.EMPTY_LIST;
        List<InepTaskDTO> dtos = new ArrayList<InepTaskDTO>( entities.size() );
        for ( InepTask item : entities )
            dtos.add( item.toDTO() );
        return dtos;
    }
}
