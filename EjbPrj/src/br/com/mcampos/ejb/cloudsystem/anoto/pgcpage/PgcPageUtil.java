package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage;

import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPageUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PgcUtil;

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
}
