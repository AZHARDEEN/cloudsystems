package br.com.mcampos.ejb.cloudsystem.locality.state.session;


import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.StatePK;
import br.com.mcampos.ejb.entity.address.Region;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;


@Stateless( name = "StateSession", mappedName = "CloudSystems-EjbPrj-StateSession" )
public class StateSessionBean extends Crud<StatePK, State> implements StateSessionLocal
{

    @Override
    public void delete( StatePK key ) throws ApplicationException
    {
        State state = get( key );

        unlinkToRegion( state );
        if ( state != null ) {
            getEntityManager().remove( state );
        }
    }

    @Override
    public State get( StatePK key ) throws ApplicationException
    {
        return get( State.class, key );
    }

    @Override
    public List<State> getAll( Region region ) throws ApplicationException
    {
        return ( List<State> )getResultList( State.getAllByRegion, region );
    }

    @Override
    public List<State> getAll( Country country ) throws ApplicationException
    {
        if ( country != null )
            return ( List<State> )getResultList( State.getAllByRegion, country );
        else
            return Collections.emptyList();
    }

    private void linkToRegion( State entity )
    {
        if ( entity == null )
            return;
        Region region = entity.getRegion();
        if ( region != null ) {
            region.addState( entity );
        }
    }

    private void unlinkToRegion( State entity )
    {
        if ( entity == null )
            return;
        Region region = entity.getRegion();
        if ( region != null ) {
            region.removeState( entity );
        }
    }

    @Override
    public State add( State entity ) throws ApplicationException
    {
        State state = super.add( entity );
        linkToRegion( state );
        return state;
    }
}
