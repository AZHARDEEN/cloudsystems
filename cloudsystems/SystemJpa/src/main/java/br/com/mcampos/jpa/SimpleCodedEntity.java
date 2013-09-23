package br.com.mcampos.jpa;


public interface SimpleCodedEntity<ENTITY> extends SimpleEntity<ENTITY>
{
	public String getCode( );

	public void setCode( String code );
}
