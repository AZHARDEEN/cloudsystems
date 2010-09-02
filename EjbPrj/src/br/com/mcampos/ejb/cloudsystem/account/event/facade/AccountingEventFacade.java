package br.com.mcampos.ejb.cloudsystem.account.event.facade;


import br.com.mcampos.dto.accounting.AccountingEventDTO;
import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AccountingEventFacade extends Serializable
{
    void delete( AuthenticationDTO auth, Integer maskId, Integer eventId ) throws ApplicationException;

    AccountingEventDTO get( AuthenticationDTO auth, Integer maskId, Integer eventId ) throws ApplicationException;

    AccountingEventDTO add( AuthenticationDTO auth, AccountingEventDTO dto ) throws ApplicationException;

    AccountingEventDTO update( AuthenticationDTO auth, AccountingEventDTO dto ) throws ApplicationException;

    List<AccountingEventDTO> getAll( AuthenticationDTO auth, Integer maskId ) throws ApplicationException;

    List<AccountingMaskDTO> getMasks( AuthenticationDTO auth ) throws ApplicationException;

    Integer nextId( AuthenticationDTO auth, Integer maskId ) throws ApplicationException;
}
