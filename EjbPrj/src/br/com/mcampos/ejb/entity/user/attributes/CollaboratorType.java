package br.com.mcampos.ejb.entity.user.attributes;

import br.com.mcampos.ejb.entity.user.Collaborator;

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
@NamedQueries({
  @NamedQuery(name = "CollaboratorType.findAll", query = "select o from CollaboratorType o")
})
@Table( name = "\"collaborator_type\"" )
public class CollaboratorType implements Serializable
{
    public static final int typeManager = 1;
    public static final int typeEmployee = 2;
    
    private String description;
    private Integer id;

    public CollaboratorType()
    {
    }

    public CollaboratorType( Integer id, String description )    
    {
        this.description = description;
        this.id = id;
    }

    @Column( name="clt_description_ch", nullable = false )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Id
    @Column( name="clt_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }
}
