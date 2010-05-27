package br.com.mcampos.ejb.cloudsystem.user.title;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;
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


@Stateless( name = "TitleFacade", mappedName = "CloudSystems-EjbPrj-TitleFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class TitleFacadeBean extends AbstractSecurity implements TitleFacade
{

    public static final Integer messageId = 10;

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    private TitleSessionLocal titleSession;


    public TitleFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<TitleDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        List<Title> titles = titleSession.getAll();
        return toDTO( titles );
    }


    @TransactionAttribute( TransactionAttributeType.NEVER )
    public Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        return titleSession.getNextId();
    }

    public void add( AuthenticationDTO currentUser, TitleDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        Title entity = titleSession.get( dto.getId() );
        if ( entity != null )
            throwException( 1 );
        entity = DTOFactory.copy( dto );
        titleSession.add( entity );
    }

    public void update( AuthenticationDTO currentUser, TitleDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        Title entity = titleSession.get( dto.getId() );
        if ( entity == null )
            throwException( 2 );
        entity.setAbbreviation( dto.getAbbreviation() );
        entity.setDescription( dto.getDescription() );
        titleSession.update( entity );
    }

    public void delete( AuthenticationDTO currentUser, TitleDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        Title entity = titleSession.get( dto.getId() );
        if ( entity == null )
            throwException( 3 );
        titleSession.delete( dto.getId() );
    }

    public static List<TitleDTO> toDTO( List<Title> titles )
    {
        if ( SysUtils.isEmpty( titles ) )
            return Collections.EMPTY_LIST;
        List<TitleDTO> dtos = new ArrayList<TitleDTO>( titles.size() );
        for ( Title title : titles )
            dtos.add( title.toDTO() );
        return dtos;
    }

}
