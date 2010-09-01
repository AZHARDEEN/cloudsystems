package br.com.mcampos.ejb.cloudsystem.account.costcenter.facade;


import br.com.mcampos.dto.accounting.CostAreaDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.CostCenterUtil;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostArea;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.session.CostAreaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.account.util.AccountingAuthUser;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CostAreaFacade", mappedName = "CloudSystems-EjbPrj-CostAreaFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CostAreaFacadeBean extends AccountingAuthUser implements CostAreaFacade
{
    public static final Integer messageId = 43;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    protected CostAreaSessionLocal session;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        load( auth );
        session.delete( getLogin(), getCompany(), id );
    }

    public CostAreaDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        load( auth );
        return CostCenterUtil.copy( session.get( getCompany(), id ) );
    }

    public CostAreaDTO add( AuthenticationDTO auth, CostAreaDTO dto ) throws ApplicationException
    {
        load( auth );
        CostArea ca = session.get( getCompany(), dto.getId() );
        if ( ca != null )
            throwException( 1 );
        ca = CostCenterUtil.createEntity( getCompany(), dto );
        ca = session.add( getLogin(), ca );
        return CostCenterUtil.copy( ca );
    }

    public CostAreaDTO update( AuthenticationDTO auth, CostAreaDTO dto ) throws ApplicationException
    {
        load( auth );
        CostArea ca = session.get( getCompany(), dto.getId() );
        if ( ca == null )
            throwException( 2 );
        ca = CostCenterUtil.update( ca, dto );
        ca = session.update( ca );
        return CostCenterUtil.copy( ca );
    }

    public List<CostAreaDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        load( auth );
        return CostCenterUtil.toCostAreaDTOList( session.getAll( getCompany() ) );
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        load( auth );
        return session.nextId( getCompany() );
    }
}
