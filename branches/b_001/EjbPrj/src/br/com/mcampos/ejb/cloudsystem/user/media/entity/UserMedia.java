package br.com.mcampos.ejb.cloudsystem.user.media.entity;


import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = UserMedia.getAll, query = "select o from UserMedia o" ),
                 @NamedQuery( name = UserMedia.getType,
                              query = "select o from UserMedia o where o.user = ?1 and o.type = ?2 and o.version = ( select max ( p.version ) from UserMedia p where p.user = ?1 and p.type = ?2 ) " ) } )
@Table( name = "user_media" )
@IdClass( UserMediaPK.class )
public class UserMedia implements Serializable
{
    public static final String getAll = "UserMedia.findAll";
    public static final String getType = "UserMedia.getType";

    @Id
    @Column( name = "med_id_in", nullable = false, updatable = false, insertable = false )
    private Integer mediaId;

    @Column( name = "umd_insert_dt", nullable = false )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date insertDate;

    @Column( name = "umd_version_in" )
    private Integer version;

    @Id
    @Column( name = "usr_id_in", nullable = false, updatable = false, insertable = false )
    private Integer userId;

    @ManyToOne( optional = false )
    @JoinColumn( name = "umt_id_in" )
    private UserMediaType type;

    @ManyToOne( optional = false )
    @JoinColumn( name = "med_id_in" )
    private Media media;

    @ManyToOne( optional = false )
    @JoinColumn( name = "usr_id_in" )
    private Users user;


    public UserMedia()
    {
    }

    public Integer getMediaId()
    {
        return mediaId;
    }

    public void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Date umd_insert_dt )
    {
        this.insertDate = umd_insert_dt;
    }

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion( Integer umd_version_in )
    {
        this.version = umd_version_in;
    }


    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    public UserMediaType getType()
    {
        return type;
    }

    public void setType( UserMediaType userMediaType )
    {
        this.type = userMediaType;
    }

    public void setMedia( Media media )
    {
        this.media = media;
        setMediaId( media != null ? media.getId() : null );
    }

    public Media getMedia()
    {
        return media;
    }

    public void setUser( Users user )
    {
        this.user = user;
        setUserId( user != null ? user.getId() : null );
    }

    public Users getUser()
    {
        return user;
    }
}
