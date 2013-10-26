package br.com.mcampos.ejb.cloudsystem.user.attribute.title.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.TitleUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity.Title;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.session.TitleSessionLocal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "TitleFacade", mappedName = "TitleFacade" )
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


    public List<TitleDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        List<Title> titles = titleSession.getAll();
        return TitleUtil.toDTOList( titles );
    }


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
        entity = TitleUtil.createEntity( dto );
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
}
