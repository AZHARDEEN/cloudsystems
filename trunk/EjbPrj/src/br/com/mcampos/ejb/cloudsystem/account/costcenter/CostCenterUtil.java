package br.com.mcampos.ejb.cloudsystem.account.costcenter;


import br.com.mcampos.dto.accounting.CostAreaDTO;
import br.com.mcampos.dto.accounting.CostCenterDTO;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostArea;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostCenter;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CostCenterUtil
{
    public static CostCenter createEntity( CostArea owner, CostCenterDTO dto )
    {
        if ( dto == null )
            return null;

        CostCenter entity = new CostCenter( owner, dto.getId() );
        return update( entity, dto );
    }

    public static CostCenter update( CostCenter entity, CostCenterDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static CostCenterDTO copy( CostCenter entity )
    {
        if ( entity == null )
            return null;

        CostCenterDTO dto = new CostCenterDTO();
        dto.setDescription( entity.getDescription() );
        dto.setId( entity.getId() );
        dto.setArea( copy( entity.getCostArea() ) );
        return dto;
    }

    public static List<CostCenterDTO> toCostCenterDTOList( List<CostCenter> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<CostCenterDTO> listDTO = new ArrayList<CostCenterDTO>( list.size() );
        for ( CostCenter m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }


    public static CostArea createEntity( Company owner, CostAreaDTO dto )
    {
        if ( dto == null )
            return null;

        CostArea entity = new CostArea( owner, dto.getId() );
        return update( entity, dto );
    }

    public static CostArea update( CostArea entity, CostAreaDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static CostAreaDTO copy( CostArea entity )
    {
        if ( entity == null )
            return null;

        CostAreaDTO dto = new CostAreaDTO();
        dto.setDescription( entity.getDescription() );
        dto.setId( entity.getId() );
        return dto;
    }

    public static List<CostAreaDTO> toCostAreaDTOList( List<CostArea> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<CostAreaDTO> listDTO = new ArrayList<CostAreaDTO>( list.size() );
        for ( CostArea m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }

}
