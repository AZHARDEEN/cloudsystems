package br.com.mcampos.dto.upload;

import java.io.Serializable;

public class RejectedDTO implements Serializable
{
	private static final long serialVersionUID = -6431850814246098942L;

	private Integer lineNumber;
	private String record;
	private String cause;

	public Integer getLineNumber( )
	{
		return this.lineNumber;
	}

	public void setLineNumber( Integer lineNumber )
	{
		this.lineNumber = lineNumber;
	}

	public String getRecord( )
	{
		return this.record;
	}

	public void setRecord( String record )
	{
		this.record = record;
	}

	public String getCause( )
	{
		return this.cause;
	}

	public void setCause( String cause )
	{
		this.cause = cause;
	}

}
