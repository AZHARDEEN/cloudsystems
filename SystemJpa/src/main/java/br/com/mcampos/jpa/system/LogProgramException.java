package br.com.mcampos.jpa.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the program_exception database table.
 * 
 */
@Entity
@Table( name = "program_exception", schema = "audit" )
public class LogProgramException implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "pex_id_in" )
	private Integer id;

	@Column( name = "pex_insert_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date insertDate;

	@Column( name = "pex_stack_ch" )
	private String description;

	public LogProgramException( )
	{
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer pexIdIn )
	{
		id = pexIdIn;
	}

	public Date getInsertDate( )
	{
		return insertDate;
	}

	public void setInsertDate( Date pexInsertDt )
	{
		insertDate = pexInsertDt;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String pexStackCh )
	{
		description = pexStackCh;
	}

}