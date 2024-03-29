package br.com.mcampos.jpa.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

/**
 * The persistent class for the upload_status database table.
 * 
 */
@Entity
@Table( name = "upload_status", schema = "public" )
public class UploadStatus extends SimpleTable<UploadStatus>
{
	private static final long serialVersionUID = 1L;

	public static final Integer duplicated = 2;
	public static final Integer sucess = 1;

	@Id
	@Column( name = "ups_id_in" )
	private Integer id;

	@Column( name = "ups_description_ch" )
	private String description;

	public UploadStatus( )
	{
	}

	public UploadStatus( Integer id )
	{
		this.setId( id );
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer upsIdIn )
	{
		this.id = upsIdIn;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String upsDescriptionCh )
	{
		this.description = upsDescriptionCh;
	}
}