package br.com.mcampos.ejb.cloudsystem.account.costcenter.session;


import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostArea;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostAreaPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CostAreaSession", mappedName = "CloudSystems-EjbPrj-CostAreaSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CostAreaSessionBean extends Crud<CostAreaPK, CostArea> implements CostAreaSessionLocal
{

    public void delete( Login login, CostAreaPK key ) throws ApplicationException
    {
        delete( CostArea.class, key );
    }

    public void delete( Login login, Company company, Integer id ) throws ApplicationException
    {
        delete( login, new CostAreaPK( id, company.getId() ) );
    }

    public CostArea get( CostAreaPK key ) throws ApplicationException
    {
        return get( CostArea.class, key );
    }

    public CostArea get( Company company, Integer id ) throws ApplicationException
    {
        return get( new CostAreaPK( id, company.getId() ) );
    }

    public List<CostArea> getAll( Company company ) throws ApplicationException
    {
        return ( List<CostArea> )getResultList( CostArea.getAll, company );
    }

    public CostArea add( Login login, CostArea entity ) throws ApplicationException
    {
        entity.setFrom( new Date() );
        return add( entity );
    }

    public Integer nextId( Company company ) throws ApplicationException
    {
        return nextIntegerId( CostArea.nextId, company );
    }
}
