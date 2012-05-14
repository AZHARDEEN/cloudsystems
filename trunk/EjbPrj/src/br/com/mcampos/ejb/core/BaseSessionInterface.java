package br.com.mcampos.ejb.core;

import java.util.Collection;


public interface BaseSessionInterface<T> extends PagingSessionInterface<T>
{
    public <T> T merge( T newEntity );

    public Collection<T> merge( Collection<T> entities );

    public <T> T remove( T entity );

    public Collection<T> remove( Collection<T> entities );

    public Class<T> getPersistentClass();

}
