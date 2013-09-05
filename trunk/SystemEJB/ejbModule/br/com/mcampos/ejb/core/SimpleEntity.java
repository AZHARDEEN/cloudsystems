package br.com.mcampos.ejb.core;

public interface SimpleEntity<ENTITY> extends BasicEntityRenderer<ENTITY>, Comparable<ENTITY>, BaseEntity
{
	public String getDescription( );

	public void setDescription( String description );

	public void setId( Integer id );

}
