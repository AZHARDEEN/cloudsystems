package br.com.mcampos.ejb.cloudsystem.anoto.pen;

import br.com.mcampos.dto.anoto.PenDTO;

public final class AnotoPenUtil
{

    public static AnotoPen createEntity( PenDTO entity )
    {
        if ( entity == null )
            return null;
        AnotoPen pen = new AnotoPen( entity.getId() );
        return update( pen, entity );
    }

    public static AnotoPen update( AnotoPen pen, PenDTO dto )
    {
        pen.setDescription( dto.getDescription() );
        return pen;
    }

    public static PenDTO copy( AnotoPen pen )
    {
        return new PenDTO( pen.getId(), pen.getDescription() );

    }
}
