package br.com.mcampos.dto.core;

public interface SimpleTable
{
    SimpleTable setId( Integer id );

    Integer getId();

    SimpleTable setDescription( String description );

    String getDescription();
}
