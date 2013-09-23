package br.com.mcampos.dto.inep;

import java.io.Serializable;

public class InepTaskCounters implements Serializable
{
	private static final long serialVersionUID = 1147291455721448311L;
	private int tasks;
	private int revised;
	private int variance;

	public int getTasks( )
	{
		return this.tasks;
	}

	public void setTasks( int tasks )
	{
		this.tasks = tasks;
	}

	public int getRevised( )
	{
		return this.revised;
	}

	public void setRevised( int revised )
	{
		this.revised = revised;
	}

	public int getVariance( )
	{
		return this.variance;
	}

	public void setVariance( int variance )
	{
		this.variance = variance;
	}
}
