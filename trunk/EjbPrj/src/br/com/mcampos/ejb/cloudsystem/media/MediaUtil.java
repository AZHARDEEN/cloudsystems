package br.com.mcampos.ejb.cloudsystem.media;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

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
}
