package br.com.mcampos.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the contact_type database table.
 * 
 */
@Entity
@Table( name = "contact_type", schema = "public" )
public class ContactType extends SimpleTable<ContactType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "cct_id_in" )
	private Integer id;

	@Column( name = "cct_allow_duplicate_bt" )
	private Boolean duplicate;

	@Column( name = "cct_description_ch" )
	private String description;

	@Column( name = "cct_mask_ch" )
	private String mask;

	public ContactType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer cctIdIn )
	{
		id = cctIdIn;
	}

	public Boolean getDuplicate( )
	{
		return duplicate;
	}

	public void setDuplicate( Boolean cctAllowDuplicateBt )
	{
		duplicate = cctAllowDuplicateBt;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String cctDescriptionCh )
	{
		description = cctDescriptionCh;
	}

	public String getMask( )
	{
		return mask;
	}

	public void setMask( String cctMaskCh )
	{
		mask = cctMaskCh;
	}

}