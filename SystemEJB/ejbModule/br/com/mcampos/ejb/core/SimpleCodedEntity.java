package br.com.mcampos.ejb.core;

public interface SimpleCodedEntity<ENTITY> extends SimpleEntity<ENTITY>
{
	public String getCode( );

	public void setCode( String code );
}
