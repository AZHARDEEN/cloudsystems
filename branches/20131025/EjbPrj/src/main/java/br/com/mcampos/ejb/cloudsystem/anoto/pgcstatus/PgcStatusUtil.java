package br.com.mcampos.ejb.cloudsystem.anoto.pgcstatus;

import br.com.mcampos.dto.anoto.PgcStatusDTO;

public final class PgcStatusUtil
{
    public static PgcStatusDTO copy( PgcStatus entity )
    {
        PgcStatusDTO dto = new PgcStatusDTO( entity.getId(), entity.getDescription() );
        return dto;
    }
}
