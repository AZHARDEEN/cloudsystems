package br.com.mcampos.dto.inep;

import java.io.Serializable;

public class InepAnaliticoCorrecao implements Serializable, Comparable<InepAnaliticoCorrecao>
{
	private static final long serialVersionUID = -3795984868146876861L;

	private String subscritpion;
	private TaskGrade[ ] grades;

	@Override
	public int compareTo( InepAnaliticoCorrecao o )
	{
		return getSubscritpion( ).compareTo( o.getSubscritpion( ) );
	}

	public String getSubscritpion( )
	{
		if ( this.subscritpion == null ) {
			this.subscritpion = "";
		}
		return this.subscritpion;
	}

	public void setSubscritpion( String subscritpion )
	{
		this.subscritpion = subscritpion;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof InepAnaliticoCorrecao )
		{
			InepAnaliticoCorrecao other = (InepAnaliticoCorrecao) obj;
			return getSubscritpion( ).equals( other.getSubscritpion( ) );
		}
		else if ( obj instanceof String )
		{
			String other = (String) obj;
			return getSubscritpion( ).equals( other );
		}
		return false;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;

		hash = hash * prime + getSubscritpion( ).hashCode( );
		return hash;
	}

	public TaskGrade[ ] getGrades( )
	{
		if ( this.grades == null ) {
			this.grades = new TaskGrade[ 4 ];
			for ( int nIndex = 0; nIndex < this.grades.length; nIndex++ ) {
				this.grades[ nIndex ] = new TaskGrade( );
			}
		}
		return this.grades;
	}

	public void setGrades( TaskGrade[ ] grades )
	{
		this.grades = grades;
	}

}
