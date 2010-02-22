package br.com.mcampos.ejb.cloudsystem.anode.entity;

import br.com.mcampos.dto.anode.FormDTO;

import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Form.findAll", query = "select o from Form o" ), @NamedQuery( name = "Form.nextId", query = "select MAX(o.id) from Form o" ), @NamedQuery( name = "Form.findAvailableFormsForPen", query = "select o from Form o where ?1 NOT MEMBER OF o.pens" ) } )
@Table( name = "\"form\"" )
public class Form implements Serializable, EntityCopyInterface<FormDTO>
{
    private String description;
    private Integer id;
    private String ip;
    private List<Pen> pens;
    private List<Media> medias;

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

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "form_pen", joinColumns = @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false ), inverseJoinColumns = @JoinColumn( name = "pen_id_ch", referencedColumnName = "pen_id_ch", nullable = false ) )
    public List<Pen> getPens()
    {
        return pens; /*Implementar MANY TO MANY!!!*/
    }

    public void setPens( List<Pen> formPenList )
    {
        this.pens = formPenList;
    }

    public Pen addFormPen( Pen formPen )
    {
        getPens().add( formPen );
        return formPen;
    }

    public Pen removeFormPen( Pen formPen )
    {
        getPens().remove( formPen );
        return formPen;
    }

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "form_media", joinColumns = @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false ), inverseJoinColumns = @JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in", nullable = false ) )
    public List<Media> getMedias()
    {
        return medias;
    }

    public void setMedias( List<Media> formMediaList )
    {
        this.medias = formMediaList;
    }

    public Media addFormMedia( Media formMedia )
    {
        getMedias().add( formMedia );
        return formMedia;
    }

    public Media removeFormMedia( Media formMedia )
    {
        getMedias().remove( formMedia );
        return formMedia;
    }

    public FormDTO toDTO()
    {
        FormDTO dto = new FormDTO( this.getId(), this.getDescription() );

        dto.setIp( this.getIp() );
        return dto;
    }
}
