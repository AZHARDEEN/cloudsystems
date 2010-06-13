package br.com.mcampos.ejb.cloudsystem.anoto.pgc;

import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.ejb.cloudsystem.media.MediaUtil;

public class PgcUtil
{
    public PgcUtil()
    {
        super();
    }

    public static Pgc createEntity( PGCDTO source )
    {
        Pgc pgc = new Pgc();
        pgc.setMedia( MediaUtil.createEntity( source.getMedia() ) );
        String penId = source.getPenId();
        if ( penId != null ) {
            if ( penId.length() > 16 )
                penId = penId.substring( 0, 15 );
        }
        pgc.setPenId( penId );
        pgc.setTimediff( source.getTimeDiff() );
        return pgc;
    }
}
