package br.com.mcampos.ejb.cloudsystem.locality.state.session;


import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.region.entity.Region;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.StatePK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "StateSession", mappedName = "CloudSystems-EjbPrj-StateSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
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
        if ( region == null )
            return Collections.emptyList();
        return ( List<State> )getResultList( State.getAllByRegion, region );
    }

    @Override
    public List<State> getAll( Country country ) throws ApplicationException
    {
        if ( country == null )
            return Collections.emptyList();
        return ( List<State> )getResultList( State.getAllByCountry, country );
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
