package br.com.mcampos.ejb.cloudsystem.media.Session;


import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "MediaSession", mappedName = "CloudSystems-EjbPrj-MediaSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class MediaSessionBean extends Crud<Integer, Media> implements MediaSessionLocal
{
    public MediaSessionBean()
    {
    }

    @Override
    public Media add( Media entity ) throws ApplicationException
    {
        entity.setId( null );
        return super.add( entity );
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Media.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Media get( Integer key ) throws ApplicationException
    {
        return get( Media.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public byte[] getObject( Integer key ) throws ApplicationException
    {
        Media entity = get( key );
        byte[] object = null;

        if ( entity != null ) {
            if ( entity.getObject() != null && entity.getObject().length > 0 )
                object = entity.getObject();
        }

        return object;
    }
}
