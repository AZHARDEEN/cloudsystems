package br.com.mcampos.ejb.cloudsystem.account.plan;


import br.com.mcampos.dto.accounting.AccountingRateTypeDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountingRateType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccountingRateTypeUtil
{
    public AccountingRateTypeUtil()
    {
        super();
    }

    public static AccountingRateType createEntity( CivilStateDTO dto )
    {
        if ( dto == null )
            return null;

        AccountingRateType entity = new AccountingRateType( dto.getId() );
        return update( entity, dto );
    }

    public static AccountingRateType update( AccountingRateType entity, CivilStateDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static AccountingRateTypeDTO copy( AccountingRateType entity )
    {
        if ( entity != null )
            return new AccountingRateTypeDTO( entity.getId(), entity.getDescription() );
        else
            return null;
    }

    public static List<AccountingRateTypeDTO> toDTOList( List<AccountingRateType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<AccountingRateTypeDTO> listDTO = new ArrayList<AccountingRateTypeDTO>( list.size() );
        for ( AccountingRateType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }

}
