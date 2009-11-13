package br.com.mcampos.ejb.entity.user.attributes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "ContactType.findAll", query = "select o from ContactType o")
})
@Table( name = "\"contact_type\"" )
public class ContactType implements Serializable
{
    private Boolean allowDuplicate;
    private String description;
    private Integer id;
    private String mask;

    public ContactType()
    {
    }

    public ContactType( Integer id )
    {
        this.id = id;
    }

    public ContactType( Integer id, String description, String mask, Boolean allowDuplicate )
    {
        init ( id, description, mask, allowDuplicate );
    }
    
    protected void init ( Integer id, String description, String mask, Boolean allowDuplicate )
    {
        this.id = id;
        this.allowDuplicate = allowDuplicate;
        this.description = description;
        this.mask = mask;
    }

    @Column( name="cct_allow_duplicate_bt", nullable = false )
    public Boolean getAllowDuplicate()
    {
        return allowDuplicate;
    }

    public void setAllowDuplicate( Boolean allowDuplicate )
    {
        this.allowDuplicate = allowDuplicate;
    }


    @Column( name="cct_description_ch", nullable = false, length = 64 )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Id
    @Column( name="cct_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    @Column( name="cct_mask_ch", length = 64 )
    public String getMask()
    {
        return mask;
    }

    public void setMask( String mask )
    {
        this.mask = mask;
    }
}
