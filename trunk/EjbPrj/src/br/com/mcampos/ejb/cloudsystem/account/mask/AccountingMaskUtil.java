package br.com.mcampos.ejb.cloudsystem.account.mask;


import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class AccountingMaskUtil
{
    public static AccountingMask createEntity( Company owner, AccountingMaskDTO dto )
    {
        if ( dto == null )
            return null;

        AccountingMask entity = new AccountingMask( owner, dto.getId() );
        return update( entity, dto );
    }

    public static AccountingMask update( AccountingMask entity, AccountingMaskDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setMask( dto.getMask() );
        entity.setFrom( new Date() );
        return entity;
    }

    public static AccountingMaskDTO copy( AccountingMask entity )
    {
        if ( entity == null )
            return null;

        AccountingMaskDTO dto = new AccountingMaskDTO();
        dto.setDescription( entity.getDescription() );
        dto.setMask( entity.getMask() );
        dto.setId( entity.getId() );
        return dto;
    }

    public static List<AccountingMaskDTO> toDTOList( List<AccountingMask> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<AccountingMaskDTO> listDTO = new ArrayList<AccountingMaskDTO>( list.size() );
        for ( AccountingMask m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
