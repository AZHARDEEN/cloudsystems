package br.com.mcampos.ejb.cloudsystem.anode.entity;

import br.com.mcampos.dto.anode.FormDTO;

import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Form.findAll", query = "select o from Form o" ) } )
@Table( name = "\"form\"" )
public class Form implements Serializable, EntityCopyInterface<FormDTO>
{
    private String description;
    private Integer id;
    private String ip;
    private List<FormPen> pens;
    private List<FormMedia> medias;

    public Form()
    {
    }

    public Form( Integer frm_id_in, String frm_description_ch, String frm_ip_ch )
    {
        this.description = frm_description_ch;
        this.id = frm_id_in;
        this.ip = frm_ip_ch;
    }

    @Column( name = "frm_description_ch" )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String frm_description_ch )
    {
        this.description = frm_description_ch;
    }

    @Id
    @Column( name = "frm_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer frm_id_in )
    {
        this.id = frm_id_in;
    }

    @Column( name = "frm_ip_ch", nullable = false )
    public String getIp()
    {
        return ip;
    }

    public void setIp( String frm_ip_ch )
    {
        this.ip = frm_ip_ch;
    }

    @OneToMany( mappedBy = "form" )
    public List<FormPen> getPens()
    {
        return pens;
    }

    public void setPens( List<FormPen> formPenList )
    {
        this.pens = formPenList;
    }

    public FormPen addFormPen( FormPen formPen )
    {
        getPens().add( formPen );
        formPen.setForm( this );
        return formPen;
    }

    public FormPen removeFormPen( FormPen formPen )
    {
        getPens().remove( formPen );
        formPen.setForm( null );
        return formPen;
    }

    @OneToMany( mappedBy = "form" )
    public List<FormMedia> getMedias()
    {
        return medias;
    }

    public void setMedias( List<FormMedia> formMediaList )
    {
        this.medias = formMediaList;
    }

    public FormMedia addFormMedia( FormMedia formMedia )
    {
        getMedias().add( formMedia );
        formMedia.setForm( this );
        return formMedia;
    }

    public FormMedia removeFormMedia( FormMedia formMedia )
    {
        getMedias().remove( formMedia );
        formMedia.setForm( null );
        return formMedia;
    }

    public FormDTO toDTO()
    {
        FormDTO dto = new FormDTO( this.getId(), this.getDescription() );

        dto.setIp( this.getIp() );
        return null;
    }
}
