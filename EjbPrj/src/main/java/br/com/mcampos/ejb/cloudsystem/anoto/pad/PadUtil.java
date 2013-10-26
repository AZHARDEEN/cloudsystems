package br.com.mcampos.ejb.cloudsystem.anoto.pad;

import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormUtil;

public final class PadUtil
{
    public static PadDTO copy( Pad entity )
    {
        PadDTO dto = new PadDTO();
        dto.setForm( AnotoFormUtil.copy( entity.getForm() ) );
        dto.setId( entity.getId() );
        return dto;
    }
}
