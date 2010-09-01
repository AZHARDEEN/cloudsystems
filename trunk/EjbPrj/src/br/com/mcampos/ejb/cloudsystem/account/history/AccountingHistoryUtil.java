package br.com.mcampos.ejb.cloudsystem.account.history;


import br.com.mcampos.dto.accounting.AccountingHistoryDTO;
import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistory;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccountingHistoryUtil
{
    public static AccountingHistory createEntity( Company owner, AccountingHistoryDTO dto )
    {
        if ( dto == null )
            return null;

        AccountingHistory entity = new AccountingHistory( owner, dto.getId() );
        return update( entity, dto );
    }

    public static AccountingHistory update( AccountingHistory entity, AccountingHistoryDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setHistory( dto.getHistory() );
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static AccountingHistoryDTO copy( AccountingHistory entity )
    {
        if ( entity == null )
            return null;

        AccountingHistoryDTO dto = new AccountingHistoryDTO();
        dto.setDescription( entity.getDescription() );
        dto.setHistory( dto.getHistory() );
        dto.setId( entity.getId() );
        return dto;
    }

    public static List<AccountingHistoryDTO> toDTOList( List<AccountingHistory> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<AccountingHistoryDTO> listDTO = new ArrayList<AccountingHistoryDTO>( list.size() );
        for ( AccountingHistory m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
