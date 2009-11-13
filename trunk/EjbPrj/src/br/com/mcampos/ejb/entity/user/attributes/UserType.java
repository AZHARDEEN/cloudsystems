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
  @NamedQuery(name = "UserType.findAll", query = "select o from UserType o")
})
@Table( name = "\"user_type\"" )
public class UserType implements Serializable
{
    protected String id;
    protected String description;
    
    
    public UserType()
    {
    }

    public UserType( String id )
    {
        this.id = id;
    }

    public UserType( Integer id )
    {
        this.id = id.toString();
    }

    public UserType( Integer id, String description )
    {
        init ( id, description );
    }

    @Column( name="ust_description_ch", nullable = false, length = 32 )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Id
    @Column( name="ust_id_in", nullable = false )
    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    protected void init ( Integer id, String description )
    {
        if ( description != null && description.length() > 0 ) 
            this.description = description.trim();
        this.id = id.toString();
    }

  
    public void copyFrom ( SimpleTableDTO dto )
    {
        setId ( getId ().toString() );
        setDescription( dto.getDescription() );
    }

}
