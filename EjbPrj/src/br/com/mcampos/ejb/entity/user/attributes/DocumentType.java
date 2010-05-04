package br.com.mcampos.ejb.entity.user.attributes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "DocumentType.findAll", query = "select o from DocumentType o" ),
                 @NamedQuery( name = "DocumentType.find", query = "select o from DocumentType o where o.id = :id" ) } )
@Table( name = "\"document_type\"" )
public class DocumentType implements Serializable
{
    @Id
    @Column( name = "doc_id_in", nullable = false )
    private Integer id;
    @Column( name = "doc_mask_ch", length = 32 )
    private String mask;
    @Column( name = "doc_name_ch", nullable = false, length = 20 )
    private String name;

    public DocumentType()
    {
    }

    public DocumentType( Integer id )
    {
        setId( id );
    }

    public DocumentType( Integer id, String name, String mask )
    {
        super();
        init( id, name, mask );
    }


    protected void init( Integer id, String name, String mask )
    {
        setId( id );
        setMask( mask );
        setName( name );
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer doc_id_in )
    {
        this.id = doc_id_in;
    }

    public String getMask()
    {
        return mask;
    }

    public void setMask( String doc_mask_ch )
    {
        this.mask = doc_mask_ch;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String doc_name_ch )
    {
        this.name = doc_name_ch;
    }
}
