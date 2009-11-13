package br.com.mcampos.ejb.session.address;

import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.address.State;

import br.com.mcampos.ejb.entity.address.StatePK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless( name = "StateSession", mappedName = "CloudSystems-EjbPrj-StateSession" )
@Remote
@Local
public class StateSessionBean implements StateSession, StateSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public StateSessionBean()
    {
    }
    

    public void add( StateDTO dto )
    {
        em.persist( DTOFactory.copy ( dto ) );
    }

    public void update( StateDTO dto )
    {
        em.merge(DTOFactory.copy ( ( dto ) ) );
    }

    public void delete( Integer countryID, Integer regionId, Integer id )
    {
        State state;
        
        
        state = em.find(State.class, new StatePK( countryID, regionId, id ) );
        if ( state != null )
            em.remove(state);
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<StateDTO> getAll()
    {
        List<State> list;
        
        list = ( em.createNamedQuery("State.findAll").getResultList() );
        if ( list == null )
            return Collections.emptyList();
        return getDTOList( list );
    }
    
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    protected List<StateDTO> getDTOList ( List<State> list )
    {
        List<StateDTO> dtos;
        
        if ( list == null )
            return Collections.emptyList();
        dtos = new ArrayList<StateDTO>( list.size () );
        for ( State item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<StateDTO> getAll( Integer countryId )
    {
        List<State> list;
        
        list = em.createNamedQuery("State.findByCountry").setParameter("countryId", countryId).getResultList();
        return getDTOList( list );
    }
}
