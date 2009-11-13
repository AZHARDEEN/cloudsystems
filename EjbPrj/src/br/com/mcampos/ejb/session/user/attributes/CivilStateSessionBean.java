package br.com.mcampos.ejb.session.user.attributes;

import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.attributes.CivilState;


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

@Stateless( name = "CivilStateSession", mappedName = "CloudSystems-EjbPrj-CivilStateSession" )
@Remote
@Local
public class CivilStateSessionBean implements CivilStateSession,
                                              CivilStateSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;


    public CivilStateSessionBean()
    {
    }
    

    public void add( CivilStateDTO dto )
    {
        em.persist( DTOFactory.copy ( dto ) );
    }

    public void update( CivilStateDTO dto )
    {
        em.merge( DTOFactory.copy ( dto ) );
    }

    public void delete( Integer id )
    {
        CivilState civilState;
        
        
        civilState = em.find(CivilState.class, id );
        if ( civilState != null )
            em.remove(civilState);
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<CivilStateDTO> getAll()
    {
        List<CivilState> list;
        List<CivilStateDTO> dtos = null;

        list = ( List<CivilState> ) em.createNamedQuery("CivilState.findAll").getResultList();
        if ( list == null  ) 
            return dtos;
        dtos = new ArrayList<CivilStateDTO>( list.size () );
        for ( CivilState item : list )
            dtos.add( DTOFactory.copy ( item ) );
        return dtos;
    }

    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public CivilStateDTO get( Integer id )
    {
        CivilState record;
        CivilStateDTO dto;
        
        
        record =  (CivilState ) em.find( CivilState.class, id );
        dto = DTOFactory.copy ( record );
        return dto;
    }
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getMaxPKValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( cst_id_in, 0 ) ) + 1 as adt_id_in from civil_state" );
        
        return (Integer) query.getSingleResult();
    }
}



