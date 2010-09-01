package br.com.mcampos.ejb.cloudsystem.account.costcenter.facade;


import br.com.mcampos.dto.accounting.CostAreaDTO;
import br.com.mcampos.dto.accounting.CostCenterDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.CostCenterUtil;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostArea;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostCenter;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.session.CostAreaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.session.CostCenterSessionLocal;
import br.com.mcampos.ejb.cloudsystem.account.util.AccountingAuthUser;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CostCenterFacade", mappedName = "CloudSystems-EjbPrj-CostCenterFacade" )
@Remote
public class CostCenterFacadeBean extends AccountingAuthUser implements CostCenterFacade
{
    public static final Integer messageId = 44;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    protected CostCenterSessionLocal session;

    @EJB
    protected CostAreaSessionLocal areaSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    private CostArea getArea( Integer id ) throws ApplicationException
    {
        return areaSession.get( getCompany(), id );
    }

    public void delete( AuthenticationDTO auth, Integer areaId, Integer id ) throws ApplicationException
    {
        load( auth );
        session.delete( getLogin(), getArea( areaId ), id );
    }

    public CostCenterDTO get( AuthenticationDTO auth, Integer areaId, Integer id ) throws ApplicationException
    {
        load( auth );
        return CostCenterUtil.copy( session.get( getArea( areaId ), id ) );
    }

    public CostCenterDTO add( AuthenticationDTO auth, CostCenterDTO dto ) throws ApplicationException
    {
        load( auth );
        CostArea area = getArea( dto.getArea().getId() );
        CostCenter ca = session.get( area, dto.getId() );
        if ( ca != null )
            throwException( 1 );
        ca = CostCenterUtil.createEntity( area, dto );
        ca = session.add( getLogin(), ca );
        return CostCenterUtil.copy( ca );
    }

    public CostCenterDTO update( AuthenticationDTO auth, CostCenterDTO dto ) throws ApplicationException
    {
        load( auth );
        CostCenter ca = session.get( getArea( dto.getArea().getId() ), dto.getId() );
        if ( ca == null )
            throwException( 2 );
        ca = CostCenterUtil.update( ca, dto );
        ca = session.update( ca );
        return CostCenterUtil.copy( ca );
    }

    public List<CostCenterDTO> getAll( AuthenticationDTO auth, Integer areaId ) throws ApplicationException
    {
        load( auth );
        return CostCenterUtil.toCostCenterDTOList( session.getAll( getArea( areaId ) ) );
    }

    public Integer nextId( AuthenticationDTO auth, Integer areaId ) throws ApplicationException
    {
        load( auth );
        return session.nextId( getArea( areaId ) );
    }

    public List<CostAreaDTO> getCostAreas( AuthenticationDTO auth ) throws ApplicationException
    {
        load( auth );
        return CostCenterUtil.toCostAreaDTOList( areaSession.getAll( getCompany() ) );
    }
}
