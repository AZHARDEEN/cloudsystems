package br.com.mcampos.ejb.cloudsystem.anoto.pad;


import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

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
@NamedQueries( { @NamedQuery( name = Pad.formPadsNamedQuery, query = "select o from Pad o" ),
                 @NamedQuery( name = Pad.padFindAllNamedQuery, query = "select o from Pad o where o.form.application = ?1" ) } )
@Table( name = "pad" )
@IdClass( PadPK.class )
public class Pad implements Serializable, EntityCopyInterface<PadDTO>, Comparable<Pad>
{
    public static final String formPadsNamedQuery = "Pad.findFormPads";
    public static final String padFindAllNamedQuery = "Pad.findAll";

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;

    @Id
    @Column( name = "pad_id_in", nullable = false, insertable = false, updatable = false )
    private Integer id;

    @Column( name = "pad_insert_dt", nullable = false )
    @Temporal( TemporalType.DATE )
    private Date insertDate;

    @ManyToOne
    @JoinColumn( name = "frm_id_in" )
    private AnotoForm form;

    @ManyToOne
    @JoinColumn( name = "pad_id_in" )
    private Media media;

    public Pad()
    {
    }

    public Pad( Integer formId, Integer mediaId, Date insertDate )
    {
        this.formId = formId;
        this.id = mediaId;
        this.insertDate = insertDate;
    }

    public Pad( AnotoForm form, Media media )
    {
        setForm( form );
        setMedia( media );
    }

    public Integer getFormId()
    {
        return formId;
    }

    public void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer pad_id_in )
    {
        this.id = pad_id_in;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Date pad_insert_dt )
    {
        this.insertDate = pad_insert_dt;
    }

    public void setForm( AnotoForm form )
    {
        this.form = form;
        if ( form != null )
            setFormId( form.getId() );
    }

    public AnotoForm getForm()
    {
        return form;
    }

    public void setMedia( Media media )
    {
        this.media = media;
        if ( media != null )
            setId( media.getId() );
    }

    public Media getMedia()
    {
        return media;
    }

    public PadDTO toDTO()
    {
        PadDTO dto = new PadDTO( getForm().toDTO(), getMedia().toDTO() );
        return dto;
    }

    public int compareTo( Pad o )
    {
        int nRet;

        nRet = getId().compareTo( o.getId() );
        if ( nRet != 0 )
            return nRet;
        return getForm().compareTo( o.getForm() );
    }
}
