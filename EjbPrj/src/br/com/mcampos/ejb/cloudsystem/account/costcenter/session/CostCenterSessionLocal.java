package br.com.mcampos.ejb.cloudsystem.account.costcenter.session;


import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostArea;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostCenter;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostCenterPK;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CostCenterSessionLocal extends Serializable
{
    void delete( Login login, CostCenterPK key ) throws ApplicationException;

    void delete( Login login, CostArea owner, Integer id ) throws ApplicationException;

    CostCenter get( CostCenterPK key ) throws ApplicationException;

    CostCenter get( CostArea owner, Integer id ) throws ApplicationException;

    List<CostCenter> getAll( CostArea owner ) throws ApplicationException;

    CostCenter add( Login login, CostCenter entity ) throws ApplicationException;

    CostCenter update( CostCenter entity ) throws ApplicationException;

    Integer nextId( CostArea owner ) throws ApplicationException;
}
