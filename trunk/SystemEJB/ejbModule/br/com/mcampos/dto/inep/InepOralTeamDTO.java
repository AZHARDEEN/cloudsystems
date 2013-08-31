package br.com.mcampos.dto.inep;

import java.io.Serializable;

import br.com.mcampos.entity.inep.InepRevisor;

public class InepOralTeamDTO implements Serializable, Comparable<InepOralTeamDTO>
{
	private static final long serialVersionUID = -29852702391404973L;

	private InepRevisor revisor;
	private Integer tests;

	public InepOralTeamDTO( )
	{

	}

	public InepOralTeamDTO( InepRevisor item )
	{
		revisor = item;
	}

	public InepRevisor getRevisor( )
	{
		return revisor;
	}

	public void setRevisor( InepRevisor revisor )
	{
		this.revisor = revisor;
	}

	public Integer getTests( )
	{
		if ( tests == null )
			tests = 0;
		return tests;
	}

	public void setTests( Integer tests )
	{
		this.tests = tests;
	}

	@Override
	public int compareTo( InepOralTeamDTO o )
	{
		return getRevisor( ).compareTo( o.getRevisor( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		return getRevisor( ).equals( ( (InepOralTeamDTO) obj ).getRevisor( ) );
	}

}
