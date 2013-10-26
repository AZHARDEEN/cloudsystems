package br.com.mcampos.ejb.cloudsystem;

public interface PagingSessionInterface<T> extends ReadOnlySessionInterface<T>
{
    public int count();

    public int count( String filter );
}
