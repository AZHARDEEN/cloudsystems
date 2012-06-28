package br.com.mcampos.ejb.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the program_exception database table.
 * 
 */
@Entity
@Table( name = "program_exception" )
public class ProgramException implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "pex_id_in" )
	private Integer id;

	@Column( name = "pex_insert_dt", nullable = true )
	private Timestamp pexInsertDt;

	@Column( name = "pex_stack_ch" )
	private String description;

	// bi-directional many-to-one association to ProgramExceptionTrace
	@OneToMany( mappedBy = "programException", fetch = FetchType.EAGER )
	private List<ProgramExceptionTrace> programExceptionTraces;

	public ProgramException( )
	{
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer pexIdIn )
	{
		this.id = pexIdIn;
	}

	public Timestamp getPexInsertDt( )
	{
		return this.pexInsertDt;
	}

	public void setPexInsertDt( Timestamp pexInsertDt )
	{
		this.pexInsertDt = pexInsertDt;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String pexStackCh )
	{
		this.description = pexStackCh;
	}

	public List<ProgramExceptionTrace> getProgramExceptionTraces( )
	{
		if ( this.programExceptionTraces == null ) {
			this.programExceptionTraces = new ArrayList<ProgramExceptionTrace>( );
		}
		return this.programExceptionTraces;
	}

	public void setProgramExceptionTraces( List<ProgramExceptionTrace> programExceptionTraces )
	{
		this.programExceptionTraces = programExceptionTraces;
	}

	public void add( ProgramExceptionTrace item )
	{
		getProgramExceptionTraces( ).add( item );
		item.setProgramException( this );
	}

}