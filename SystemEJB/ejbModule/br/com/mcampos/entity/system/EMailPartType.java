package br.com.mcampos.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the e_mail_part_type database table.
 * 
 */
@Entity
@Table( name = "e_mail_part_type", schema = "public" )
public class EMailPartType extends SimpleTable<EMailPartType>
{
	private static final long serialVersionUID = 1L;

	public static final int partSubject = 1;
	public static final int partBody = 2;

	@Id
	@Column( name = "emp_id_in" )
	private Integer id;

	@Column( name = "emp_description_ch" )
	private String description;

	public EMailPartType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer empIdIn )
	{
		id = empIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String empDescriptionCh )
	{
		description = empDescriptionCh;
	}

}