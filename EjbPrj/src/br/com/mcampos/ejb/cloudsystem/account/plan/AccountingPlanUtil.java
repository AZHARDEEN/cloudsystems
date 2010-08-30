package br.com.mcampos.ejb.cloudsystem.account.plan;


import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.ejb.cloudsystem.account.mask.AccountingMaskUtil;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.nature.AccountingNatureUtil;
import br.com.mcampos.ejb.cloudsystem.account.nature.entity.AccountingNature;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlan;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AccountingPlanUtil
{
    public static AccountingPlan createEntity( AccountingMask owner, AccountingPlanDTO dto, AccountingNature nature )
    {
        if ( dto == null )
            return null;

        AccountingPlan entity = new AccountingPlan( owner, dto.getNumber() );
        return update( entity, dto, nature );
    }

    public static AccountingPlan update( AccountingPlan entity, AccountingPlanDTO dto, AccountingNature nature )
    {
        if ( dto == null )
            return null;
        entity.setShortNumber( dto.getShortNumber() );
        entity.setDescription( dto.getDescription() );
        entity.setNature( nature );
        return entity;
    }

    public static AccountingPlanDTO copy( AccountingPlan entity )
    {
        if ( entity == null )
            return null;

        AccountingPlanDTO dto = new AccountingPlanDTO();
        dto.setNumber( entity.getNumber() );
        dto.setShortNumber( entity.getShortNumber() );
        dto.setDescription( entity.getDescription() );
        dto.setMask( AccountingMaskUtil.copy( entity.getMask() ) );
        dto.setNature( AccountingNatureUtil.copy( entity.getNature() ) );
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
