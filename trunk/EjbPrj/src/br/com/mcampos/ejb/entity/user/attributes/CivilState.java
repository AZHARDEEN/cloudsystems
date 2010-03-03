package br.com.mcampos.ejb.entity.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "CivilState.findAll", query = "select o from CivilState o")
})
@Table( name = "\"civil_state\"" )
public class CivilState implements Serializable
{
    protected Integer id;
    protected String description;
                     
    public CivilState()
    {
    }
    

    public CivilState ( SimpleTableDTO dto )
    {
        if ( dto != null )
        {
            this.id = dto.getId();
            this.description = dto.getDescription();
        }
    }
    
    public CivilState ( Integer id )
    {
        this.id = id;
    }


    public CivilState( Integer id, String description )
    {
        this.id = id;
        this.description = description;
    }
    

    @Column( name="cst_description_ch", nullable = false, length = 32 )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Id
    @Column( name="cst_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }
    
}

