package br.com.mcampos.ejb.cloudsystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public abstract class BaseSessionBean<T> extends PagingSessionBean<T> implements BaseSessionInterface<T>
{


    @Override
    public <T> T merge( T newEntity )
    {
        if ( newEntity == null )
            return null;
        T managed = getEntityManager().merge( newEntity );
        return managed;
    }

    @Override
    public Collection<T> merge( Collection<T> entities )
    {
        if ( entities == null || entities.size() == 0 )
            return Collections.emptyList();
        List<T> merged = new ArrayList<T>( entities.size() );
        for ( T item : entities ) {
            merged.add( ( T ) merge( item ) );
        }
        return merged;
    }

    @Override
    public Collection<T> remove( Collection<T> entities )
    {
        if ( entities == null || entities.size() == 0 )
            return Collections.emptyList();
        List<T> merged = new ArrayList<T>( entities.size() );
        for ( T item : entities ) {
            merged.add( ( T ) remove( item ) );
        }
        return merged;
    }

    @Override
    public <T> T remove( T entity )
    {
        if ( entity == null )
            return null;
        T removed = merge( entity );
        getEntityManager().remove( removed );
        return removed;
    }
}
