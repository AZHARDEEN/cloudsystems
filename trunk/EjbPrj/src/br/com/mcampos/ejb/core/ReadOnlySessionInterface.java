package br.com.mcampos.ejb.core;

import java.io.Serializable;

import java.util.Collection;


public interface ReadOnlySessionInterface<T>
{
    public <T> T get( Serializable key );

    public Collection<T> getAll();

    public Collection<T> getAll( String whereClause, DBPaging page );
}
