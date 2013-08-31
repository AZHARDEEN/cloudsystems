package br.com.mcampos.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the document_type database table.
 * 
 */
@Entity
@Table( name = "document_type" )
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
		return this.id;
	}

	@Override
	public void setId( Integer docIdIn )
	{
		this.id = docIdIn;
	}

	public String getMask( )
	{
		return this.mask;
	}

	public void setMask( String docMaskCh )
	{
		this.mask = docMaskCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String docNameCh )
	{
		this.description = docNameCh;
	}

	public Boolean getUniq( )
	{
		return this.uniq;
	}

	public void setUniq( Boolean uniq )
	{
		this.uniq = uniq;
	}

}