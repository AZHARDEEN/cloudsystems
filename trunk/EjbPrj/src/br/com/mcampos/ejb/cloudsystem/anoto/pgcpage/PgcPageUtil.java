package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage;


import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPageUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PgcUtil;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PgcPageUtil
{
    public PgcPageUtil()
    {
        super();
    }

    public static PgcPage createEntity( PgcPageDTO dto )
    {
        PgcPage entity = new PgcPage( PgcUtil.createEntity( dto.getPgc() ), dto.getBookId(), dto.getPageId() );
        entity.setAnotoPage( AnotoPageUtil.createEntity( dto.getAnotoPage() ) );
        return entity;
    }

    public static PgcPageDTO copy( PgcPage entity )
    {
        PgcPageDTO dto = new PgcPageDTO();
        dto.setAnotoPage( AnotoPageUtil.copy( entity.getAnotoPage() ) );
        dto.setBookId( entity.getBookId() );
        dto.setPageId( entity.getPageId() );
        dto.setPgc( PgcUtil.copy( entity.getPgc() ) );
        return dto;
    }


    public static List<PgcPageDTO> toListDTO( List<PgcPage> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PgcPageDTO> dtos = new ArrayList<PgcPageDTO>( list.size() );
        for ( PgcPage item : list )
            dtos.add( copy( item ) );
        return dtos;
    }
}
