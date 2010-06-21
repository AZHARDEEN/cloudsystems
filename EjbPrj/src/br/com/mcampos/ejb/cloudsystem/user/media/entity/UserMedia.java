package br.com.mcampos.ejb.cloudsystem.user.media.entity;

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
@NamedQueries({
  @NamedQuery(name = "UserMedia.findAll", query = "select o from UserMedia o")
})
@Table( name = "\"user_media\"" )
@IdClass( UserMediaPK.class )
public class UserMedia implements Serializable
{
    public UserMedia()
    {
    }

    public UserMedia( Integer med_id_in, Timestamp umd_insert_dt, Integer umd_version_in, UserMediaType userMediaType, Integer usr_id_in )
    {
        this.med_id_in = med_id_in;
        this.umd_insert_dt = umd_insert_dt;
        this.umd_version_in = umd_version_in;
        this.userMediaType = userMediaType;
        this.usr_id_in = usr_id_in;
    }
    @Id
    @Column( name="med_id_in", nullable = false )
    private Integer med_id_in;
    @Column( name="umd_insert_dt", nullable = false )
    private Timestamp umd_insert_dt;
    @Column( name="umd_version_in" )
    private Integer umd_version_in;
    @Id
    @Column( name="usr_id_in", nullable = false )
    private Integer usr_id_in;
    @ManyToOne
    @JoinColumn( name = "umt_id_in" )
    private UserMediaType userMediaType;

    public Integer getMed_id_in()
    {
        return med_id_in;
    }

    public void setMed_id_in( Integer med_id_in )
    {
        this.med_id_in = med_id_in;
    }

    public Timestamp getUmd_insert_dt()
    {
        return umd_insert_dt;
    }

    public void setUmd_insert_dt( Timestamp umd_insert_dt )
    {
        this.umd_insert_dt = umd_insert_dt;
    }

    public Integer getUmd_version_in()
    {
        return umd_version_in;
    }

    public void setUmd_version_in( Integer umd_version_in )
    {
        this.umd_version_in = umd_version_in;
    }


    public Integer getUsr_id_in()
    {
        return usr_id_in;
    }

    public void setUsr_id_in( Integer usr_id_in )
    {
        this.usr_id_in = usr_id_in;
    }

    public UserMediaType getUserMediaType()
    {
        return userMediaType;
    }

    public void setUserMediaType( UserMediaType userMediaType )
    {
        this.userMediaType = userMediaType;
    }
}
