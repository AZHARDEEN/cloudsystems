package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.attributes.Gender;


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

@Stateless( name = "GenderSession", mappedName = "CloudSystems-EjbPrj-GenderSession" )
@Remote
@Local
public class GenderSessionBean implements GenderSession, GenderSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public GenderSessionBean()
    {
    }
    
    

    public void add( GenderDTO dto )
    {
        em.persist( DTOFactory.copy ( dto ) );
    }

    public void update( GenderDTO dto )
    {
        em.merge( DTOFactory.copy ( dto ) );
    }

    public void delete( Integer id )
    {
        Gender gender = em.find(Gender.class, id );
        if ( gender != null )
            em.remove(gender);
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<GenderDTO> getAll()
    {
        List<Gender> list;
        List<GenderDTO> dtos = null;
        
        list = ( em.createNamedQuery("Gender.findAll").getResultList() );
        if ( list == null )
            return dtos;
        dtos = new ArrayList<GenderDTO>( list.size () );
        for ( Gender item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public GenderDTO get( Integer id)
    {
        Gender record;
        
        record = em.find ( Gender.class, id );
        if ( record != null )
            return DTOFactory.copy ( record );
        else 
            return null;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getIdMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( gnd_id_in, 0 ) ) + 1 as idMax from gender" );
        
        return (Integer) query.getSingleResult();
    }
}
