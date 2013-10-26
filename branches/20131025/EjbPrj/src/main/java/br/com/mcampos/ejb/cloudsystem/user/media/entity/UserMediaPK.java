package br.com.mcampos.ejb.cloudsystem.user.media.entity;

import java.io.Serializable;

public class UserMediaPK implements Serializable
{
    private Integer mediaId;
    private Integer userId;

    public UserMediaPK()
    {
    }

    public UserMediaPK( Integer med_id_in, Integer usr_id_in )
    {
        this.mediaId = med_id_in;
        this.userId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof UserMediaPK ) {
            final UserMediaPK otherUserMediaPK = ( UserMediaPK )other;
            final boolean areEqual = ( otherUserMediaPK.mediaId.equals( mediaId ) && otherUserMediaPK.userId.equals( userId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getMediaId()
    {
        return mediaId;
    }

    void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    Integer getUserId()
    {
        return userId;
    }

    void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }
}
