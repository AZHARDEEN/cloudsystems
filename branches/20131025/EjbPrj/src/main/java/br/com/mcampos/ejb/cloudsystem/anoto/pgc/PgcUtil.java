package br.com.mcampos.ejb.cloudsystem.anoto.pgc;


import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcstatus.PgcStatusUtil;
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

    public static PGCDTO copy( Pgc entity )
    {
        PGCDTO dto = new PGCDTO();
        dto.setId( entity.getId() );
        dto.setInsertDate( entity.getInsertDate() );
        dto.setPenId( entity.getPenId() );
        dto.setPgcStatus( PgcStatusUtil.copy( entity.getPgcStatus() ) );
        dto.setTimeDiff( entity.getTimediff() );
        return dto;
    }

}
