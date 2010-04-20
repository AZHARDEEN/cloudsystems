package br.com.mcampos.ejb.cloudsystem.system.entity;


import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "FieldType.findAll", query = "select o from FieldType o" ) } )
@Table( name = "field_type" )
public class FieldType implements Serializable, Comparable<FieldType>, EntityCopyInterface<FieldTypeDTO>
{
    @Column( name = "flt_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "flt_id_in", nullable = false )
    private Integer id;

    public FieldType()
    {
    }

    public FieldType( String flt_description_ch, Integer flt_id_in )
    {
        this.description = flt_description_ch;
        this.id = flt_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String flt_description_ch )
    {
        this.description = flt_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer flt_id_in )
    {
        this.id = flt_id_in;
    }

    public int compareTo( FieldType o )
    {
        return getId().compareTo( o.getId() );
    }

    public FieldTypeDTO toDTO()
    {
        FieldTypeDTO dto = new FieldTypeDTO( getId(), getDescription() );

        return dto;
    }
}
