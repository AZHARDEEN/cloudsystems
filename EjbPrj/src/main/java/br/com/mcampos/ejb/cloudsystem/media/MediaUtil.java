package br.com.mcampos.ejb.cloudsystem.media;


import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaUtil
{
    public MediaUtil()
    {
        super();
    }

    public static Media createEntity( MediaDTO source )
    {
        if ( source == null )
            return null;
        Media target = new Media( source.getId() );

        target.setMimeType( source.getMimeType() );
        target.setName( source.getName() );
        target.setObject( source.getObject() );
        target.setFormat( source.getFormat() );
        return target;
    }


    public static List<MediaDTO> toListDTO( List<Media> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<MediaDTO> dtos = new ArrayList<MediaDTO>( list.size() );
        for ( Media item : list )
            dtos.add( MediaUtil.copy( item ) );
        return dtos;
    }

    public static MediaDTO copy( Media source )
    {
        if ( source == null )
            return null;
        MediaDTO target = new MediaDTO( source.getId(), source.getName() );
        target.setMimeType( source.getMimeType() );
        target.setObject( source.getObject() );
        target.setFormat( source.getFormat() );
        return target;
    }

}
