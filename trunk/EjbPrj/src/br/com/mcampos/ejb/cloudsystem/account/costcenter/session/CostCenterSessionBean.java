package br.com.mcampos.ejb.cloudsystem.account.costcenter.session;

import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostArea;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostAreaPK;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostCenter;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostCenterPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;

import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless( name = "CostCenterSession", mappedName = "CloudSystems-EjbPrj-CostCenterSession" )
@Local
public class CostCenterSessionBean extends Crud<CostCenterPK, CostCenter> implements CostCenterSessionLocal
{
    public void delete( Login login, CostCenterPK key ) throws ApplicationException
    {
        delete( CostCenter.class, key );
    }

    public void delete( Login login, CostArea owner, Integer id ) throws ApplicationException
    {
        delete( login, new CostCenterPK( owner, id ) );
    }

    public CostCenter get( CostCenterPK key ) throws ApplicationException
    {
        return get( CostCenter.class, key );
    }

    public CostCenter get( CostArea owner, Integer id ) throws ApplicationException
    {
        return get( new CostCenterPK( owner, id ) );
    }

    public List<CostCenter> getAll( CostArea owner ) throws ApplicationException
    {
        return ( List<CostCenter> )getResultList( CostCenter.getAll, owner );
    }

    public CostCenter add( Login login, CostCenter entity ) throws ApplicationException
    {
        entity.setFrom( new Date() );
        return add( entity );
    }

    public Integer nextId( CostArea owner ) throws ApplicationException
    {
        return nextIntegerId( CostCenter.nextId, owner );
    }
}
