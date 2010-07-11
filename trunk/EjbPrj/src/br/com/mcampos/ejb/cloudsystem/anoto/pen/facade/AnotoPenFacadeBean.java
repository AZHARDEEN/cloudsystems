package br.com.mcampos.ejb.cloudsystem.anoto.pen.facade;


import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnodePenSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPenUtil;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnotoPenFacade", mappedName = "CloudSystems-EjbPrj-AnotoPenFacade" )
@TransactionAttribute( value = TransactionAttributeType.REQUIRES_NEW )
public class AnotoPenFacadeBean extends AbstractSecurity implements AnotoPenFacade
{

    protected static final int SystemMessageTypeId = 29;
    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;


    @EJB
    private AnodePenSessionLocal penSession;


    public AnotoPenFacadeBean()
    {
    }


    public List<PenDTO> getPens( Integer nStart, Integer nSize ) throws ApplicationException
    {
        List<PenDTO> pens = AnotoUtils.toPenList( penSession.getAll( nStart, nSize ) );
        return pens;
    }


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }


    public void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPen pen = penSession.get( entity.getId() );
        if ( pen == null )
            throwException( 1 );
        penSession.delete( entity.getId() );
    }


    public PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPen pen = penSession.get( entity.getId() );
        if ( pen == null )
            throwException( 2 );
        pen = penSession.update( AnotoPenUtil.update( pen, entity ) );
        return pen.toDTO();
    }


    public PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        if ( entity == null )
            return null;
        authenticate( auth );
        AnotoPen pen = penSession.get( entity.getId() );
        if ( pen != null )
            throwException( 5 );
        return penSession.add( AnotoPenUtil.createEntity( entity ) ).toDTO();
    }

    public PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        PenDTO dto = penSession.get( entity.getId() ).toDTO();
        return dto;
    }

    public Integer count() throws ApplicationException
    {
        return penSession.count();
    }
}
