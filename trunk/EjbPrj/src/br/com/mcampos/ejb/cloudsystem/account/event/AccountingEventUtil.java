package br.com.mcampos.ejb.cloudsystem.account.event;


import br.com.mcampos.dto.accounting.AccountingEventDTO;
import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountEvent;
import br.com.mcampos.ejb.cloudsystem.account.mask.AccountingMaskUtil;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccountingEventUtil
{
    public static AccountEvent createEntity( AccountingMask owner, AccountingEventDTO dto )
    {
        if ( dto == null )
            return null;

        AccountEvent entity = new AccountEvent( owner, dto.getId() );
        return update( entity, dto );
    }

    public static AccountEvent update( AccountEvent entity, AccountingEventDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setHistory( dto.getHistory() );
        return entity;
    }

    public static AccountingEventDTO copy( AccountEvent entity )
    {
        if ( entity == null )
            return null;

        AccountingEventDTO dto = new AccountingEventDTO( entity.getId() );
        dto.setDescription( entity.getDescription() );
        dto.setMask( AccountingMaskUtil.copy( entity.getMask() ) );
        dto.setHistory( entity.getHistory() );
        return dto;
    }

    public static List<AccountingEventDTO> toDTOList( List<AccountEvent> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<AccountingEventDTO> listDTO = new ArrayList<AccountingEventDTO>( list.size() );
        for ( AccountEvent m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
