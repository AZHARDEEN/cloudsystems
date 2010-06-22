package br.com.mcampos.ejb.cloudsystem.user.media.entity;


import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "UserMedia.findAll", query = "select o from UserMedia o" ) } )
@Table( name = "\"user_media\"" )
@IdClass( UserMediaPK.class )
public class UserMedia implements Serializable
{
    @Id
    @Column( name = "med_id_in", nullable = false, updatable = false, insertable = false )
    private Integer mediaId;

    @Column( name = "umd_insert_dt", nullable = false )
    private Timestamp insertDate;

    @Column( name = "umd_version_in" )
    private Integer version;

    @Id
    @Column( name = "usr_id_in", nullable = false, updatable = false, insertable = false )
    private Integer userId;

    @ManyToOne( optional = false )
    @JoinColumn( name = "umt_id_in" )
    private UserMediaType userMediaType;

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

    public Timestamp getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Timestamp umd_insert_dt )
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

    public UserMediaType getUserMediaType()
    {
        return userMediaType;
    }

    public void setUserMediaType( UserMediaType userMediaType )
    {
        this.userMediaType = userMediaType;
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
