package br.com.mcampos.ejb.session.address;

import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.address.Country;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "CountrySession", mappedName = "CloudSystems-EjbPrj-CountrySession" )
public class CountrySessionBean implements CountrySession, CountrySessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public CountrySessionBean()
    {
    }
    
    

    public void add( CountryDTO dto )
    {
        em.persist( DTOFactory.copy ( dto ) );
    }

    public void update( CountryDTO dto )
    {
        em.merge( DTOFactory.copy ( dto ) );
    }

    public void delete( Integer id )
    {
        Country country;
        
        
        country = em.find(Country.class, id );
        if ( country != null )
            em.remove(country);
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<CountryDTO> getAll()
    {
        List<Country> list;
        List<CountryDTO> dtos = null;
        
        list = ( em.createNamedQuery("Country.findAll").getResultList() );
        if ( list == null )
            return dtos;
        dtos = new ArrayList<CountryDTO>( list.size () );
        for ( Country item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
}

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public CountryDTO get( Integer id )
    {
        Country country;
        CountryDTO dto = null;
        
        country = em.find(Country.class, id );
        if ( country != null )
            dto = DTOFactory.copy ( country );
        return dto;
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( ctr_id_in, 0 ) ) + 1 as maxId from country" );
        
        return (Integer) query.getSingleResult();
    }
}
