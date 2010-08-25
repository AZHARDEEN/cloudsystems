package br.com.mcampos.ejb.cloudsystem.account.plan;


import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlan;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccountingPlanUtil
{
    public static AccountingPlan createEntity( Company owner, AccountingPlanDTO dto )
    {
        if ( dto == null )
            return null;

        AccountingPlan entity = new AccountingPlan( owner, dto.getNumber() );
        return update( entity, dto );
    }

    public static AccountingPlan update( AccountingPlan entity, AccountingPlanDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setShortNumber( dto.getShortNumber() );
        entity.setBalance( dto.getBalance() );
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static AccountingPlanDTO copy( AccountingPlan entity )
    {
        if ( entity == null )
            return null;

        AccountingPlanDTO dto = new AccountingPlanDTO();
        dto.setBalance( entity.getBalance() );
        dto.setNumber( entity.getNumber() );
        dto.setShortNumber( entity.getShortNumber() );
        dto.setDescription( entity.getDescription() );
        return dto;
    }

    public static List<AccountingPlanDTO> toDTOList( List<AccountingPlan> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<AccountingPlanDTO> listDTO = new ArrayList<AccountingPlanDTO>( list.size() );
        for ( AccountingPlan m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
