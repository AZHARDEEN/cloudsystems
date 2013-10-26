package br.com.mcampos.ejb.cloudsystem.system.revisedstatus;

import br.com.mcampos.dto.system.RevisionStatusDTO;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;

public final class RevisionStatusUtil
{
    public RevisionStatusUtil()
    {
        super();
    }

    public static RevisionStatusDTO copy( RevisionStatus status )
    {
        if ( status == null )
            return null;
        return new RevisionStatusDTO( status.getId(), status.getDescription() );
    }
}
