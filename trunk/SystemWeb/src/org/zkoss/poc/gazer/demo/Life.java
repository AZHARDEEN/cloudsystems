package org.zkoss.poc.gazer.demo;

public class Life
{
	String country;
	int maleLife;
	int femaleLife;

	public Life( int maleLife, int femaleLife )
	{
		this.maleLife = maleLife;
		this.femaleLife = femaleLife;
	}

	public Life( )
	{
	}

	public String getCountry( )
	{
		return country;
	}

	public void setCountry( String country )
	{
		this.country = country;
	}

	public int getMaleLife( )
	{
		return maleLife;
	}

	public void setMaleLife( int maleLife )
	{
		this.maleLife = maleLife;
	}

	public int getFemaleLife( )
	{
		return femaleLife;
	}

	public void setFemaleLife( int femaleLife )
	{
		this.femaleLife = femaleLife;
	}

}
