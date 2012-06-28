package br.com.mcampos.ejb.core;

public interface PagingSessionInterface<T> extends ReadOnlySessionInterface<T>
{
    public int count();

    public int count( String filter );
}
