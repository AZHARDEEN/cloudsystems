package br.com.mcampos.ejb.cloudsystem.user.media.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.media.entity.UserMedia;
import br.com.mcampos.ejb.cloudsystem.user.media.entity.UserMediaPK;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "UserMediaSession", mappedName = "UserMediaSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class UserMediaSessionBean extends Crud<UserMediaPK, UserMedia> implements UserMediaSessionLocal
{
    public void delete( UserMediaPK key ) throws ApplicationException
    {
        delete( UserMedia.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public UserMedia get( UserMediaPK key ) throws ApplicationException
    {
        UserMedia userMedia = get( UserMedia.class, key );
        return ( loadMediaObject( userMedia ) );
    }

    @Override
    public UserMedia add( UserMedia entity ) throws ApplicationException
    {
        UserMedia userMedia = get( entity.getUser(), entity.getType() );
        entity.setVersion( 1 );
        if ( userMedia != null )
            entity.setVersion( userMedia.getVersion() + 1 );
        entity.setInsertDate( new Date() );
        return super.add( entity );
    }

    public UserMedia get( Users user, UserMediaType type ) throws ApplicationException
    {
        List<Object> param = new ArrayList<Object>( 2 );
        param.add( user );
        param.add( type );
        UserMedia userMedia = ( UserMedia )getSingleResult( UserMedia.getType, param );
        return loadMediaObject( userMedia );
    }

    private UserMedia loadMediaObject( UserMedia userMedia )
    {
        /*Lazy Load Media Object*/
        if ( userMedia != null ) {
            Media media = userMedia.getMedia();
            int len;

            if ( media != null && media.getObject() != null )
                len = userMedia.getMedia().getObject().length;
        }
        return userMedia;
    }
}
