package br.com.mcampos.ejb.cloudsystem.anoto.page;

import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.AnotoPadUtil;

public class AnotoPageUtil
{
    public AnotoPageUtil()
    {
        super();
    }


    public static AnotoPage createEntity( AnotoPageDTO dto )
    {
        AnotoPage target = new AnotoPage( AnotoPadUtil.createEntity( dto.getPad() ), dto.getPageAddress() );
        return update( target, dto );
    }


    public static AnotoPage update( AnotoPage target, AnotoPageDTO dto )
    {
        target.setDescription( dto.getDescription() );
        target.setIcrTemplate( dto.getIcrTemplate() );
        return target;
    }
}
