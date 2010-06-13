package br.com.mcampos.ejb.cloudsystem.anoto.pad;

import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormUtil;
import br.com.mcampos.ejb.cloudsystem.media.MediaUtil;

public class AnotoPadUtil
{
    public AnotoPadUtil()
    {
        super();
    }

    public static Pad createEntity( PadDTO dto )
    {
        Pad target = new Pad( AnotoFormUtil.createEntity( dto.getForm() ), MediaUtil.createEntity( dto.getMedia() ) );
        return target;
    }
}
