package br.com.mcampos.ejb.inep;

import java.io.Serializable;

import br.com.mcampos.jpa.inep.InepRevisor;

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
		if ( tests == null ) {
			tests = 0;
		}
		return tests;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getTests( ).hashCode( );
		hash = hash * prime + getRevisor( ).getId( ).hashCode( );
		return hash;
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
