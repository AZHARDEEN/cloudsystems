package br.com.mcampos.ejb.cloudsystem.account.plan.facade;


import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.dto.accounting.AccountingNatureDTO;
import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AccountingPlanFacade extends Serializable
{
    void delete( AuthenticationDTO auth, Integer maskId, String accNumber ) throws ApplicationException;

    AccountingPlanDTO get( AuthenticationDTO auth, Integer maskId, String accNumber ) throws ApplicationException;

    AccountingPlanDTO add( AuthenticationDTO auth, AccountingPlanDTO dto ) throws ApplicationException;

    AccountingPlanDTO update( AuthenticationDTO auth, AccountingPlanDTO dto ) throws ApplicationException;

    List<AccountingPlanDTO> getAll( AuthenticationDTO auth, Integer maskId ) throws ApplicationException;

    List<AccountingMaskDTO> getMasks( AuthenticationDTO auth ) throws ApplicationException;

    List<AccountingNatureDTO> getNatures( AuthenticationDTO auth ) throws ApplicationException;
}
