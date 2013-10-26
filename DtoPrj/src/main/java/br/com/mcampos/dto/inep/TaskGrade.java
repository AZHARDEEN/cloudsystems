package br.com.mcampos.dto.inep;

import java.io.Serializable;

public class TaskGrade implements Serializable
{
	private static final long serialVersionUID = 7624430880176912842L;

	private Integer task;

	private Integer firstGrade;
	private Integer secondGrade;
	private Integer thirdGrade;

	public Integer getTask( )
	{
		return this.task;
	}

	public void setTask( Integer task )
	{
		this.task = task;
	}

	public Integer getFirstGrade( )
	{
		return this.firstGrade;
	}

	public void setFirstGrade( Integer firstGrade )
	{
		this.firstGrade = firstGrade;
	}

	public Integer getSecondGrade( )
	{
		return this.secondGrade;
	}

	public void setSecondGrade( Integer secondGrade )
	{
		this.secondGrade = secondGrade;
	}

	public Integer getThirdGrade( )
	{
		return this.thirdGrade;
	}

	public void setThirdGrade( Integer thirdGrade )
	{
		this.thirdGrade = thirdGrade;
	}
}
