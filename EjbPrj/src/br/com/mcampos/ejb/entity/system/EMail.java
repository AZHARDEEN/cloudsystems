package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "EMail.findAll", query = "select o from EMail o")
})
@Table( name = "\"e_mail\"" )
public class EMail implements Serializable
{
    private String description;
    private Integer id;
    private List<EMailPart> EMailPartList;

    public EMail()
    {
    }

    public EMail( String eml_description_ch, Integer eml_id_in )
    {
        this.description = eml_description_ch;
        this.id = eml_id_in;
    }

    @Column( name="eml_description_ch", nullable = false )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String eml_description_ch )
    {
        this.description = eml_description_ch;
    }

    @Id
    @Column( name="eml_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer eml_id_in )
    {
        this.id = eml_id_in;
    }

    @OneToMany( mappedBy = "email" )
    public List<EMailPart> getEMailPartList()
    {
        if ( EMailPartList == null )
            EMailPartList = new ArrayList<EMailPart> ();
        return EMailPartList;
    }

    public void setEMailPartList( List<EMailPart> EMailPartList )
    {
        this.EMailPartList = EMailPartList;
    }

    public EMailPart addEMailPart( EMailPart EMailPart )
    {
        getEMailPartList().add(EMailPart);
        EMailPart.setEmail(this);
        return EMailPart;
    }

    public EMailPart removeEMailPart( EMailPart EMailPart )
    {
        getEMailPartList().remove(EMailPart);
        EMailPart.setEmail(null);
        return EMailPart;
    }
}
