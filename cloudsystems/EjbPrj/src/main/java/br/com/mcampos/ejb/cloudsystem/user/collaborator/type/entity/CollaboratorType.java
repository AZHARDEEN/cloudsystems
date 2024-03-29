package br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = CollaboratorType.getAll, query = "select o from CollaboratorType o" ),
                 @NamedQuery( name = CollaboratorType.nextId, query = "select max(o.id) from CollaboratorType o" ) } )
@Table( name = "collaborator_type" )
public class CollaboratorType implements Serializable
{
    public static final int typeManager = 1;
    public static final int typeEmployee = 2;

    public static final String getAll = "CollaboratorType.findAll";
    public static final String nextId = "CollaboratorType.nextId";

    @Column( name = "clt_description_ch", nullable = false, length = 32 )
    private String description;

    @Id
    @Column( name = "clt_id_in", nullable = false )
    private Integer id;

    @Column( name = "clt_inherit_role_bt", nullable = true )
    private Boolean inheritRole;

    public CollaboratorType()
    {
    }

    public CollaboratorType( Integer id )
    {
        setId( id );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public void setInheritRole( Boolean inheritRole )
    {
        this.inheritRole = inheritRole;
    }

    public Boolean getInheritRole()
    {
        return inheritRole;
    }
}
