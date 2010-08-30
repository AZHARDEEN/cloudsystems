package br.com.mcampos.ejb.cloudsystem.account.nature;


import br.com.mcampos.dto.accounting.AccountingNatureDTO;
import br.com.mcampos.ejb.cloudsystem.account.nature.entity.AccountingNature;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccountingNatureUtil
{
    public static AccountingNature createEntity( AccountingNatureDTO dto )
    {
        if ( dto == null )
            return null;

        AccountingNature entity = new AccountingNature( dto.getId() );
        return update( entity, dto );
    }

    public static AccountingNature update( AccountingNature entity, AccountingNatureDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static AccountingNatureDTO copy( AccountingNature entity )
    {
        if ( entity == null )
            return null;

        AccountingNatureDTO dto = new AccountingNatureDTO();
        dto.setDescription( entity.getDescription() );
        dto.setId( entity.getId() );
        return dto;
    }

    public static List<AccountingNatureDTO> toDTOList( List<AccountingNature> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<AccountingNatureDTO> listDTO = new ArrayList<AccountingNatureDTO>( list.size() );
        for ( AccountingNature m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
