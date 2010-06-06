package br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = DocumentType.getAll, query = "select o from DocumentType o" ),
				 @NamedQuery( name = DocumentType.nextId, query = "select max(o.id) from DocumentType o" ) } )
@Table( name = "document_type" )
public class DocumentType implements Serializable
{
	public static final String getAll = "DocumentType.findAll";
	public static final String nextId = "DocumentType.nextId";

	@Id
	@Column( name = "doc_id_in", nullable = false )
	private Integer id;
	@Column( name = "doc_mask_ch", length = 32 )
	private String mask;
	@Column( name = "doc_name_ch", nullable = false, length = 20 )
	private String description;

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
		setDescription( name );
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String doc_name_ch )
	{
		this.description = doc_name_ch;
	}
}
