package br.com.mcampos.ejb.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the program_exception_trace database table.
 * 
 */
@Entity
@Table( name = "program_exception_trace" )
public class ProgramExceptionTrace implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProgramExceptionTracePK id;

	@Column( name = "ped_method_ch" )
	private String method;

	@Column( name = "pet_class_ch" )
	private String className;

	@Column( name = "pet_filename_ch" )
	private String fileName;

	@Column( name = "pet_line_in" )
	private Integer line;

	// bi-directional many-to-one association to ProgramException
	@ManyToOne
	@JoinColumn( name = "pex_id_in", insertable = false, updatable = false, nullable = false )
	private ProgramException programException;

	public ProgramExceptionTrace( )
	{
	}

	public ProgramExceptionTrace( ProgramException e )
	{
		setProgramException( e );
	}

	public ProgramExceptionTracePK getId( )
	{
		if ( this.id == null ) {
			this.id = new ProgramExceptionTracePK( );
		}
		return this.id;
	}

	public void setId( ProgramExceptionTracePK id )
	{
		this.id = id;
	}

	public String getMethod( )
	{
		return this.method;
	}

	public void setMethod( String pedMethodCh )
	{
		this.method = pedMethodCh;
	}

	public String getClassName( )
	{
		return this.className;
	}

	public void setClassName( String petClassCh )
	{
		this.className = petClassCh;
	}

	public String getFileName( )
	{
		return this.fileName;
	}

	public void setFileName( String petFilenameCh )
	{
		this.fileName = petFilenameCh;
	}

	public Integer getLine( )
	{
		return this.line;
	}

	public void setLine( Integer petLineIn )
	{
		this.line = petLineIn;
	}

	public ProgramException getProgramException( )
	{
		return this.programException;
	}

	public void setProgramException( ProgramException programException )
	{
		this.programException = programException;
		if ( getProgramException( ) != null ) {
			getId( ).setExceptionId( getProgramException( ).getId( ) );
		}
	}

}