package br.com.mcampos.jpa.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

/**
 * The persistent class for the document_type database table.
 * 
 */
@Entity
@Table( name = "document_type", schema = "public" )
public class DocumentType extends SimpleTable<DocumentType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "doc_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "doc_mask_ch", length = 64 )
	private String mask;

	@Column( name = "doc_name_ch", nullable = false, length = 32 )
	private String description;

	@Column( name = "doc_uniq_bt", nullable = false )
	private Boolean uniq;

	public DocumentType( )
	{
	}

	public DocumentType( Integer type )
	{
		setId( type );
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer docIdIn )
	{
		id = docIdIn;
	}

	public String getMask( )
	{
		return mask;
	}

	public void setMask( String docMaskCh )
	{
		mask = docMaskCh;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String docNameCh )
	{
		description = docNameCh;
	}

	public Boolean getUniq( )
	{
		return uniq;
	}

	public void setUniq( Boolean uniq )
	{
		this.uniq = uniq;
	}

}