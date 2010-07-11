package br.com.mcampos.ejb.cloudsystem.anoto.penpage.user;


import br.com.mcampos.dto.anoto.PenUserDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.entity.PenUser;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PenPageUserUtil
{

    public static PenUserDTO copy( PenUser pen )
    {
        PenUserDTO dto = new PenUserDTO();

        dto.setFromDate( pen.getFromDate() );
        dto.setPenPage( pen.getPenPage().toDTO() );
        dto.setSequence( pen.getSequence() );
        dto.setToDate( dto.getToDate() );
        dto.setUser( UserUtil.copy( pen.getPerson() ) );
        return dto;
    }


    public static List<PenUserDTO> toListDTO( List<PenUser> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PenUserDTO> dtos = new ArrayList<PenUserDTO>( list.size() );
        for ( PenUser item : list )
            dtos.add( copy( item ) );
        return dtos;
    }

    public static PenUserDTO copy( AnotoPenPage penPage )
    {
        PenUserDTO dto = new PenUserDTO();

        dto.setPenPage( penPage.toDTO() );
        return dto;
    }

}
